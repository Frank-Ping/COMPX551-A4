package com.example.polarh10activityviewer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class StepState(
    val totalSteps: Long? = null,
    val cadence: Double? = null,
    val message: String = "Not started.",
    val distance: Double? = null,
    val speed: Double? = null,
    val maximumCadence: Double? = null,
    val maximumSpeed: Double? = null,
    val receivedAcc: Boolean = false,
    val incompleteAcc: Boolean = false,
    val durationMs: Long = 0
) {
    val averageSpeed: Double? get() =
        if (durationMs > 0) distance?.let { it * 1000.0 / durationMs } else null
}

// One owner for the preprocessing, candidate cycle and confirmation segment.
internal class StepDetector(private val now: () -> Long = { System.nanoTime() / 1_000_000L }) {
    val preprocessor = AccPreprocessor()
    private val candidates = StepCandidateDetector()
    private val sequence = StepSequence()
    private val strides = StrideLengthEstimator()
    private val cadenceWindow = CadenceWindow()
    private val mutableState = MutableStateFlow(StepState())
    val state = mutableState.asStateFlow()
    private var receivedAcc = false
    private var recovering = false
    private var collecting = false
    private var lastSensorTime: Long? = null
    private var receivedAt = 0L
    private var distance = 0.0
    private var maximumCadence: Double? = null
    private var maximumSpeed: Double? = null
    private var incompleteAcc = false
    val totalSteps: Long get() = sequence.totalSteps
    var latestCommitted: List<StepCandidate> = emptyList()
        private set

    fun clearSegment() {
        preprocessor.clear()
        candidates.clear()
        sequence.clearSegment()
        strides.clear()
        latestCommitted = emptyList()
        cadenceWindow.clear()
        lastSensorTime = null
    }

    fun reset() {
        clearSegment()
        sequence.reset()
        receivedAcc = false
        recovering = false
        collecting = false
        distance = 0.0
        maximumCadence = null
        maximumSpeed = null
        incompleteAcc = false
        mutableState.value = StepState(message = "Waiting for ACC.")
    }

    fun onSubscriptionState(status: SubscriptionStatus, error: String? = null) {
        if (status == SubscriptionStatus.RECEIVING) return
        clearSegment()
        collecting = status == SubscriptionStatus.STARTING
        recovering = receivedAcc
        if (!collecting) incompleteAcc = true
        val current = if (collecting && !recovering) 0.0 else null
        publishState(current, current,
            if (collecting) "Warming up ACC." else error ?: "ACC ${status.name.lowercase()}."
        )
    }

    fun stop() {
        clearSegment()
        collecting = false
        val current = if (receivedAcc) 0.0 else null
        publishState(current, current, if (receivedAcc) "Stopped." else "Stopped. No ACC received.")
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
        if (warmup != null) recovering = false
        publishState(
            if (recovering) null else warmup?.let { cadenceWindow.value(sensorTime, it) } ?: 0.0,
            if (recovering) null else warmup?.let { cadenceWindow.speed(sensorTime, it) } ?: 0.0,
            when {
                recovering -> "Warming up after ACC interruption."
                warmup == null -> "Warming up ACC."
                latestCommitted.isEmpty() -> "Waiting for four consecutive steps."
                else -> "Detecting steps."
            })
    }

    private fun publishState(cadence: Double?, speed: Double?, message: String) {
        mutableState.value = state.value.copy(
            totalSteps = totalSteps.takeIf { receivedAcc }, cadence = cadence, message = message,
            distance = distance.takeIf { receivedAcc }, speed = speed,
            maximumCadence = maximumCadence, maximumSpeed = maximumSpeed,
            receivedAcc = receivedAcc, incompleteAcc = incompleteAcc
        )
    }

    fun receive(sample: AccSample): List<StepCandidate> {
        // Expiry is evaluated on arriving sensor samples, never on UI refreshes.
        if (sample.gapBeforeNs != null) {
            clearSegment()
            recovering = true
            incompleteAcc = true
        } else if (sequence.expire(sample.timeStamp)) {
            clearSegment()
        }
        receivedAcc = true
        val prepared = preprocessor.receive(sample)
        strides.receive(prepared)
        val candidate = candidates.receive(prepared)
        val committed = candidate?.let { sequence.accept(it) { strides.accept(it.timeStamp) } } ?: emptyList()
        distance += committed.sumOf { it.length ?: 0.0 }
        cadenceWindow.receive(committed, sample.timeStamp)
        preprocessor.warmupEndedAt?.let { warmup ->
            if (sample.timeStamp - warmup >= 5_000_000_000L) {
                val cadence = cadenceWindow.rawCadence(sample.timeStamp)
                val speed = cadenceWindow.rawSpeed(sample.timeStamp)
                maximumCadence = maximumCadence?.let { maxOf(it, cadence) } ?: cadence
                maximumSpeed = maximumSpeed?.let { maxOf(it, speed) } ?: speed
            }
        }
        if (committed.isNotEmpty()) {
            latestCommitted = committed
            publish(sample.timeStamp)
        }
        return committed
    }
}
