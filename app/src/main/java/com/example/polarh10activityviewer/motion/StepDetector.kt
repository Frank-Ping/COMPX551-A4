package com.example.polarh10activityviewer.motion

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.sensor.AccSample

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class StepState(
    val cadencePending: Boolean = false,
    val totalSteps: Long? = null,
    val cadence: Double? = null,
    val maximumCadence: Double? = null,
    val receivedAcc: Boolean = false,
    val incompleteAcc: Boolean = false,
    val durationMs: Long = 0
) {
    // The full Running duration includes stationary and missing-data periods.
    val meanCadence: Double? get() =
        if (receivedAcc && durationMs > 0) totalSteps?.let { it * 60_000.0 / durationMs } else null
}

// One owner for the preprocessing, candidate cycle and confirmation segment.
internal class StepDetector(private val now: () -> Long = { System.nanoTime() / 1_000_000L }) {
    private val preprocessor = AccPreprocessor()
    private val candidates = StepCandidateDetector()
    private val sequence = StepSequence()
    private val motionWindow = MotionWindow()
    private val mutableState = MutableStateFlow(StepState())
    val state = mutableState.asStateFlow()
    private var receivedAcc = false
    private var collecting = false
    private var lastSensorTime: Long? = null
    private var receivedAt = 0L
    private var maximumCadence: Double? = null
    private var incompleteAcc = false
    private var cadenceReady = false
    val totalSteps: Long get() = sequence.totalSteps
    val isWarmingUp: Boolean get() = preprocessor.warmupEndedAt == null
    var segment: Long = 0
        private set

    fun clearSegment() {
        cadenceReady = false
        segment++
        preprocessor.clear()
        candidates.clear()
        sequence.clearSegment()
        motionWindow.clear()
        lastSensorTime = null
    }

    fun reset() {
        clearSegment()
        sequence.reset()
        receivedAcc = false
        collecting = false
        maximumCadence = null
        incompleteAcc = false
        mutableState.value = StepState()
    }

    fun onSubscriptionState(status: SubscriptionStatus) {
        if (status == SubscriptionStatus.RECEIVING) return
        clearSegment()
        collecting = status == SubscriptionStatus.STARTING
        if (!collecting) incompleteAcc = true
        publishState(null)
    }

    fun stop() {
        clearSegment()
        collecting = false
        val current = if (receivedAcc) 0.0 else null
        publishState(current)
    }

    fun updateSessionTime(elapsedMs: Long) {
        mutableState.value = state.value.copy(durationMs = elapsedMs)
    }

    fun receivedBatch(sensorTime: Long, phoneTime: Long) {
        lastSensorTime = sensorTime
        receivedAt = phoneTime
        publish(sensorTime)
    }

    fun refresh() {
        if (!collecting) return
        val sensorTime = lastSensorTime ?: return
        publish(sensorTime + (now() - receivedAt) * 1_000_000L)
    }

    private fun publish(sensorTime: Long) {
        val warmup = preprocessor.warmupEndedAt
        // A quiet zero needs a full window of arriving samples, not just a UI timer.
        if (warmup != null && !sequence.hasPending &&
            (preprocessor.latest?.timeStamp ?: warmup) - warmup >= 5_000_000_000L) cadenceReady = true
        publishState(if (warmup == null || !cadenceReady) null else motionWindow.value(sensorTime, warmup))
    }

    private fun publishState(cadence: Double?) {
        mutableState.value = state.value.copy(
            totalSteps = totalSteps.takeIf { receivedAcc }, cadence = cadence,
            maximumCadence = maximumCadence,
            receivedAcc = receivedAcc, incompleteAcc = incompleteAcc,
            cadencePending = collecting && !isWarmingUp && !cadenceReady
        )
    }

    fun receive(sample: AccSample): List<StepCandidate> {
        // Expiry is evaluated on arriving sensor samples, never on UI refreshes.
        if (sample.gapBeforeNs != null) {
            clearSegment()
            incompleteAcc = true
        } else if (sequence.expire(sample.timeStamp)) {
            // expire clears the confirmation sequence. Keep continuous ACC preparation and
            // chart identity, but discard old candidate cycles and cadence peaks.
            candidates.clear()
            motionWindow.clear()
        }
        receivedAcc = true
        val prepared = preprocessor.receive(sample)
        val candidate = candidates.receive(prepared)
        val committed = candidate?.let { sequence.accept(it) } ?: emptyList()
        motionWindow.receive(committed, sample.timeStamp)
        updateWindowExtrema(sample.timeStamp)
        if (committed.isNotEmpty()) {
            cadenceReady = true
            publish(sample.timeStamp)
        }
        return committed
    }

    private fun updateWindowExtrema(sensorTime: Long) {
        preprocessor.warmupEndedAt?.let { warmup ->
            if (sensorTime - warmup >= 5_000_000_000L) {
                val cadence = motionWindow.rawCadence(sensorTime)
                maximumCadence = maximumCadence?.let { maxOf(it, cadence) } ?: cadence
            }
        }
    }
}
