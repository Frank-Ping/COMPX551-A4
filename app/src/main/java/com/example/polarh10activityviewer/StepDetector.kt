package com.example.polarh10activityviewer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class StepState(
    val totalSteps: Long? = null,
    val cadence: Double? = null,
    val message: String = "Not started."
)

// One owner for the preprocessing, candidate cycle and confirmation segment.
internal class StepDetector(private val now: () -> Long = { System.nanoTime() / 1_000_000L }) {
    val preprocessor = AccPreprocessor()
    private val candidates = StepCandidateDetector()
    private val sequence = StepSequence()
    private val cadenceWindow = CadenceWindow()
    private val mutableState = MutableStateFlow(StepState())
    val state = mutableState.asStateFlow()
    private var receivedAcc = false
    private var recovering = false
    private var collecting = false
    private var lastSensorTime: Long? = null
    private var receivedAt = 0L
    val totalSteps: Long get() = sequence.totalSteps
    var latestCommitted: List<StepCandidate> = emptyList()
        private set

    fun clearSegment() {
        preprocessor.clear()
        candidates.clear()
        sequence.clearSegment()
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
        mutableState.value = StepState(message = "Waiting for ACC.")
    }

    fun onSubscriptionState(status: SubscriptionStatus, error: String? = null) {
        if (status == SubscriptionStatus.RECEIVING) return
        clearSegment()
        collecting = status == SubscriptionStatus.STARTING
        recovering = receivedAcc
        mutableState.value = StepState(
            totalSteps.takeIf { receivedAcc },
            if (collecting && !recovering) 0.0 else null,
            if (collecting) "Warming up ACC." else error ?: "ACC ${status.name.lowercase()}."
        )
    }

    fun stop() {
        clearSegment()
        collecting = false
        mutableState.value = StepState(totalSteps.takeIf { receivedAcc },
            if (receivedAcc) 0.0 else null, if (receivedAcc) "Stopped." else "Stopped. No ACC received.")
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
        mutableState.value = StepState(totalSteps,
            if (recovering) null else warmup?.let { cadenceWindow.value(sensorTime, it) } ?: 0.0,
            when {
                recovering -> "Warming up after ACC interruption."
                warmup == null -> "Warming up ACC."
                latestCommitted.isEmpty() -> "Waiting for four consecutive steps."
                else -> "Detecting steps."
            })
    }

    fun receive(sample: AccSample): List<StepCandidate> {
        // Expiry is evaluated on arriving sensor samples, never on UI refreshes.
        if (sample.gapBeforeNs != null) {
            clearSegment()
            recovering = true
        } else if (sequence.expire(sample.timeStamp)) {
            clearSegment()
        }
        receivedAcc = true
        val prepared = preprocessor.receive(sample)
        val candidate = candidates.receive(prepared)
        val committed = candidate?.let(sequence::accept) ?: emptyList()
        cadenceWindow.receive(committed, sample.timeStamp)
        if (committed.isNotEmpty()) {
            latestCommitted = committed
            publish(sample.timeStamp)
        }
        return committed
    }
}
