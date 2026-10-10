package com.example.polarh10activityviewer.ble

import android.os.SystemClock
import androidx.annotation.MainThread
import com.example.polarh10activityviewer.chart.ChartKind
import com.example.polarh10activityviewer.chart.LiveCharts
import com.example.polarh10activityviewer.heartrate.HeartRateZones
import com.example.polarh10activityviewer.history.HrHistory
import com.example.polarh10activityviewer.history.MotionHistory
import com.example.polarh10activityviewer.motion.StepDetector
import com.example.polarh10activityviewer.sensor.AccBuffer
import com.example.polarh10activityviewer.sensor.EcgBuffer
import com.example.polarh10activityviewer.session.SessionController
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SessionStatus
import com.example.polarh10activityviewer.session.SessionSummary
import com.example.polarh10activityviewer.storage.RawEcg
import com.example.polarh10activityviewer.storage.SessionStorage
import com.example.polarh10activityviewer.storage.SignalBuffer
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.EcgSample
import com.polar.sdk.api.model.PolarAccelerometerData
import com.polar.sdk.api.model.PolarHrData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@MainThread
internal class SessionDataCoordinator(
    private val storage: SessionStorage,
    private val scope: CoroutineScope
) {
    private val latestHeartRate = LatestHeartRate()
    private val hrHistory = HrHistory()
    private val motionHistory = MotionHistory()
    internal fun validCadenceStatistics(): com.example.polarh10activityviewer.chart.ChartStatistics {
        val end = session.state.value.elapsedMs.toDouble()
        return com.example.polarh10activityviewer.chart.chartStatistics(
            com.example.polarh10activityviewer.chart.ChartSnapshot(motionHistory.snapshot().map {
                com.example.polarh10activityviewer.chart.ChartPoint(it.elapsedMs.toDouble(), it.cadence, it.breakBefore)
            }, end, end, SubscriptionStatus.STOPPED), ChartKind.CADENCE)
    }
    private val mutableLastSnapshot = MutableStateFlow<SessionSnapshot?>(null)
    internal val lastSnapshot = mutableLastSnapshot.asStateFlow()
    private var previousHrArrival: Long? = null
    val heartRate = latestHeartRate.reading
    val heartRateStatistics = latestHeartRate.statistics
    val heartRateMessage = latestHeartRate.message
    private val heartRateZones = HeartRateZones()
    internal val heartRateZoneState = heartRateZones.state
    private val stepDetector = StepDetector(SystemClock::elapsedRealtime)
    internal val stepState = stepDetector.state
    private val accBuffer = AccBuffer { stepDetector.receive(it) }
    private var signals = SignalBuffer()
    private var checkpointBucket = 0L
    private var checkpointAt = -1000L
    private var discardingRecording = false
    private var pauseContinuations = emptySet<PolarDeviceDataType>()
    private val ecgBuffer = EcgBuffer()
    internal val liveCharts = LiveCharts { ecgBuffer.samples.value }
    val dataSubscriptions: DataSubscriptions = DataSubscriptions(
        CoroutineScope(Dispatchers.Main.immediate)
    ) { type, status ->
        val eventTime = SystemClock.elapsedRealtime()
        if (session.checkTimeLimit(eventTime)) return@DataSubscriptions
        if (type == PolarDeviceDataType.HR) hrHistory.onSubscriptionState(status)
        if (type == PolarDeviceDataType.HR && status == SubscriptionStatus.STARTING) previousHrArrival = null
        if (type == PolarDeviceDataType.HR && status != SubscriptionStatus.RECEIVING && session.state.value.ongoing) {
            session.refresh(session.state.value.generation, eventTime)
            heartRateZones.clearCurrent(session.state.value.elapsedMs)
        }
        latestHeartRate.onSubscriptionState(type, status)
        accBuffer.onSubscriptionState(type, status)
        if (type == PolarDeviceDataType.ACC && session.state.value.ongoing) {
            stepDetector.onSubscriptionState(status)
        }
        if (type == PolarDeviceDataType.ACC) motionHistory.onSubscriptionState(status, stepDetector.segment)
        liveCharts.onSubscriptionState(type, status, session.elapsedAt(eventTime), stepDetector.segment)
        if (status != SubscriptionStatus.RECEIVING) {
            if (type == PolarDeviceDataType.ECG) signals.endEcg(session.elapsedAt(eventTime))
        }
        ecgBuffer.onSubscriptionState(type, status)
        session.onSubscriptionState(type, status, eventTime)
    }
    internal val subscriptionStates = dataSubscriptions.states
    val session: SessionController = SessionController(dataSubscriptions, SystemClock::elapsedRealtime,
        clearAllReadings = {
            pauseContinuations = emptySet()
            signals = SignalBuffer()
            checkpointBucket = 0L; checkpointAt = -1000L
            hrHistory.reset(session.state.value.record?.id)
            motionHistory.reset(session.state.value.record?.id)
            liveCharts.reset()
            if (session.state.value.status == SessionStatus.IDLE) liveCharts.select(ChartKind.HEART_RATE)
            heartRateZones.reset()
            latestHeartRate.reset()
            previousHrArrival = null
            accBuffer.clear()
            stepDetector.reset()
            ecgBuffer.clear()
        },
        clearHr = {
            pauseContinuations = if (session.state.value.status == SessionStatus.PAUSING &&
                !storage.recording.state.value.blocked) {
                setOf(PolarDeviceDataType.HR, PolarDeviceDataType.ACC).filter {
                    dataSubscriptions.states.value.getValue(it).status == SubscriptionStatus.RECEIVING
                }.toSet()
            } else emptySet()
            hrHistory.stop()
            motionHistory.stop()
            liveCharts.stop(session.state.value.elapsedMs)
            heartRateZones.clearCurrent(session.state.value.elapsedMs)
            latestHeartRate.clear()
            stepDetector.updateSessionTime(session.state.value.elapsedMs)
            stepDetector.stop()
        },
        readSummary = { elapsed ->
            heartRateZones.refresh(elapsed)
            stepDetector.updateSessionTime(elapsed)
            SessionSummary.from(latestHeartRate.statistics.value, heartRateZones.state.value, stepState.value)
        },
        onSummaryFrozen = { record ->
            if (!discardingRecording) checkpointSignals(record, true)
            val snapshot = SessionSnapshot(record, hrHistory.snapshot(), motionHistory.snapshot())
            mutableLastSnapshot.value = snapshot
            if (!discardingRecording) storage.saves.submit(snapshot)
        },
        canStart = { !storage.saves.state.value.blocksStart && !storage.recording.state.value.blocked },
        onResume = {
            val hr = PolarDeviceDataType.HR in pauseContinuations
            val motion = PolarDeviceDataType.ACC in pauseContinuations
            hrHistory.resume(hr); motionHistory.resume(motion); liveCharts.resume(hr, motion)
            pauseContinuations = emptySet()
        },
        onPaused = { record -> checkpointSignals(record, true) })
    init { storage.recording.onFailure = { session.pause() } }
    internal val sessionState = session.state

    fun refreshSessionTime(generation: Long) {
        session.refresh(generation)
        if (session.accepts(generation)) {
            stepDetector.refresh()
            if (session.state.value.status == SessionStatus.RUNNING) {
                val elapsed = session.state.value.elapsedMs
                liveCharts.recordMotion(elapsed, stepState.value,
                    warmingUp = stepDetector.isWarmingUp, segment = stepDetector.segment)
                motionHistory.record(elapsed, stepState.value,
                    warmingUp = stepDetector.isWarmingUp, segment = stepDetector.segment)
                liveCharts.advance(elapsed)
                checkpointSignals(session.state.value.record!!)
            }
        }
    }

    private fun acceptSignalInput(bytes: Int): Boolean {
        if (storage.recording.state.value.blocked) return false
        if (storage.recording.canAccept(signals.bytes + bytes + 8192)) return true
        session.markMissing(PolarDeviceDataType.ECG)
        session.markMissing(PolarDeviceDataType.HR)
        storage.recording.fail("Recording queue is full. Unaccepted input was not recorded.")
        return false
    }

    private fun checkpointSignals(record: SessionRecord, force: Boolean = false) {
        if (!record.eligibleForSaving) return
        if (!force && record.durationMs - checkpointAt < 1000) return
        if (!force && !storage.recording.canAccept(signals.bytes + 8192)) {
            if (storage.recording.state.value.error == null) storage.recording.fail("Recording queue is full. Recording paused.")
            return
        }
        if (force) signals.boundary(record.durationMs)
        val checkpoint = record.copy(endedAt = record.endedAt ?: System.currentTimeMillis())
        val batch = signals.take(checkpoint, hrHistory.since(checkpointBucket), motionHistory.since(checkpointBucket), force)
        checkpointAt = record.durationMs
        checkpointBucket = record.durationMs / 1000
        storage.recording.enqueue(batch)
    }

    fun discardRecording() {
        val id = session.state.value.record?.id ?: return
        scope.launch {
            try {
                storage.recording.discard(id)
                discardingRecording = true
                try { session.stop("Session discarded", reset = true) } finally { discardingRecording = false }
            } catch (error: Exception) { storage.recording.fail(error.message ?: "Unable to discard recording") }
        }
    }

    internal fun chartSnapshot(kind: ChartKind): com.example.polarh10activityviewer.chart.ChartSnapshot {
        val elapsed = session.elapsedAt().toDouble()
        val live = liveCharts.snapshot(kind, elapsed.toLong())
        if (kind == ChartKind.ELECTROCARDIOGRAM) return live
        val points = when (kind) {
            ChartKind.HEART_RATE -> hrHistory.snapshot().map {
                com.example.polarh10activityviewer.chart.ChartPoint(it.elapsedMs.toDouble(), it.bpm?.toDouble(), it.breakBefore)
            }
            else -> motionHistory.snapshot().map {
                com.example.polarh10activityviewer.chart.ChartPoint(it.elapsedMs.toDouble(), it.cadence, it.breakBefore)
            }
        }
        return com.example.polarh10activityviewer.chart.recentSessionChart(
            live.copy(points = points, endMs = elapsed, windowMs = elapsed), kind)
    }

    fun receiveHr(data: PolarHrData, receivedTime: Long, receivedDate: Long) {
        if (!acceptSignalInput(0)) return
        val beforeCount = latestHeartRate.statistics.value.count
        val receivedValid = latestHeartRate.receive(data)
        if (latestHeartRate.statistics.value.count - beforeCount < data.samples.size ||
            previousHrArrival?.let { previous -> receivedTime - previous > 3000 } == true) {
            session.markMissing(PolarDeviceDataType.HR)
        }
        previousHrArrival = receivedTime
        if (receivedValid) session.onValidData(receivedTime, receivedDate)
        heartRateZones.receive(latestHeartRate.reading.value, receivedValid, session.elapsedAt(receivedTime))
        session.refresh(session.state.value.generation, receivedTime)
        if (session.state.value.status == SessionStatus.RUNNING) {
            liveCharts.receiveHr(session.state.value.elapsedMs, latestHeartRate.reading.value)
            hrHistory.receive(session.state.value.elapsedMs, latestHeartRate.reading.value)
            checkpointSignals(session.state.value.record!!)
        }
    }

    fun receiveAcc(data: PolarAccelerometerData, receivedAt: Long, receivedDate: Long) {
        accBuffer.receive(data)
        stepDetector.receivedBatch(data.samples.last().timeStamp, receivedAt)
        if (stepState.value.incompleteAcc) session.markMissing(PolarDeviceDataType.ACC)
        session.onValidData(receivedAt, receivedDate)
        session.refresh(session.state.value.generation, receivedAt)
    }

    fun receiveEcg(data: List<EcgSample>, receivedTime: Long, receivedDate: Long, sampleRate: Int) {
        if (!acceptSignalInput(data.size * 100)) return
        var previous = ecgBuffer.samples.value.lastOrNull()?.timeStamp
        data.forEach { sample ->
            if (previous?.let { time -> (sample.timeStamp - time).toDouble() * sampleRate > 3_000_000_000.0 } == true) {
                session.markMissing(PolarDeviceDataType.ECG)
            }
            previous = sample.timeStamp
        }
        ecgBuffer.receive(data)
        session.onValidData(receivedTime, receivedDate)
        session.refresh(session.state.value.generation, receivedTime)
        liveCharts.receiveEcg(data, session.elapsedAt(receivedTime),
            sampleRate)
        signals.receiveEcg(data.map { RawEcg(it.timeStamp, it.voltage) }, session.elapsedAt(receivedTime), sampleRate)
        checkpointSignals(session.state.value.record!!)
    }

    fun clearPauseContinuations() {
        pauseContinuations = emptySet()
    }
}
