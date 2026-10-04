package com.example.polarh10activityviewer.motion

// Keep only the unconfirmed sequence; returned peaks are committed exactly once.
internal class StepSequence {
    private val pending = mutableListOf<StepCandidate>()
    private var lastAcceptedAt: Long? = null
    private var confirmed = false
    val hasPending: Boolean get() = pending.isNotEmpty()
    var totalSteps = 0L
        private set

    fun clearSegment() {
        pending.clear()
        lastAcceptedAt = null
        confirmed = false
    }

    fun reset() {
        clearSegment()
        totalSteps = 0
    }

    fun expire(sensorTime: Long): Boolean {
        val previous = lastAcceptedAt ?: return false
        if (sensorTime - previous <= 2_000_000_000L) return false
        clearSegment()
        return true
    }

    fun accept(candidate: StepCandidate, strideLength: () -> Double? = { null }): List<StepCandidate> {
        val previous = lastAcceptedAt
        if (previous != null && candidate.timeStamp - previous < 250_000_000L) return emptyList()
        if (expire(candidate.timeStamp)) return emptyList()
        lastAcceptedAt = candidate.timeStamp
        // Calculate only after admission: rejected peaks never move the stride reference.
        val accepted = candidate.copy(length = strideLength())
        if (confirmed) {
            totalSteps++
            return listOf(accepted)
        }
        pending.add(accepted)
        if (pending.size < 4) return emptyList()
        val committed = pending.toList()
        pending.clear()
        confirmed = true
        totalSteps += committed.size
        return committed
    }
}
