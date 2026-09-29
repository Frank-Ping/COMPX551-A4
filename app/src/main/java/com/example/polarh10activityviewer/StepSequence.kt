package com.example.polarh10activityviewer

// Keep only the unconfirmed sequence; returned peaks are committed exactly once.
internal class StepSequence {
    private val pending = mutableListOf<StepCandidate>()
    private var lastAcceptedAt: Long? = null
    private var confirmed = false
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

    fun accept(candidate: StepCandidate): List<StepCandidate> {
        val previous = lastAcceptedAt
        if (previous != null && candidate.timeStamp - previous < 250_000_000L) return emptyList()
        if (expire(candidate.timeStamp)) return emptyList()
        lastAcceptedAt = candidate.timeStamp
        if (confirmed) {
            totalSteps++
            return listOf(candidate)
        }
        pending.add(candidate)
        if (pending.size < 4) return emptyList()
        val committed = pending.toList()
        pending.clear()
        confirmed = true
        totalSteps += committed.size
        return committed
    }
}
