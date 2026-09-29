package com.example.polarh10activityviewer

// One owner for the preprocessing, candidate cycle and confirmation segment.
internal class StepDetector {
    val preprocessor = AccPreprocessor()
    private val candidates = StepCandidateDetector()
    private val sequence = StepSequence()
    val totalSteps: Long get() = sequence.totalSteps
    var latestCommitted: List<StepCandidate> = emptyList()
        private set

    fun clearSegment() {
        preprocessor.clear()
        candidates.clear()
        sequence.clearSegment()
        latestCommitted = emptyList()
    }

    fun reset() {
        clearSegment()
        sequence.reset()
    }

    fun receive(sample: AccSample): List<StepCandidate> {
        // Expiry is evaluated on arriving sensor samples, never on UI refreshes.
        if (sample.gapBeforeNs != null || sequence.expire(sample.timeStamp)) clearSegment()
        val prepared = preprocessor.receive(sample)
        val candidate = candidates.receive(prepared) ?: return emptyList()
        val committed = sequence.accept(candidate)
        if (committed.isNotEmpty()) latestCommitted = committed
        return committed
    }
}
