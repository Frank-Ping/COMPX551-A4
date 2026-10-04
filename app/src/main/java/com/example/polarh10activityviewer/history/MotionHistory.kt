package com.example.polarh10activityviewer.history

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.session.MotionHistoryPoint

// Records existing refresh results; it neither calculates motion nor creates missed ticks.
internal class MotionHistory {
    private val points = mutableListOf<MotionHistoryPoint>()
    var sessionId: String? = null
        private set
    var frozen = false
        private set
    private var status = SubscriptionStatus.IDLE
    private var previousSegment: Long? = null
    private var breakBefore = true
    private var continuingAfterPause = false
    private var resumeSegment: Long? = null

    fun reset(sessionId: String? = null) {
        points.clear()
        status = SubscriptionStatus.IDLE
        previousSegment = null
        breakBefore = true
        continuingAfterPause = false
        resumeSegment = null
        this.sessionId = sessionId
        frozen = false
    }

    fun onSubscriptionState(status: SubscriptionStatus, segment: Long? = null) {
        if (frozen) return
        this.status = status
        if (status == SubscriptionStatus.STARTING && continuingAfterPause) resumeSegment = segment
        if (status != SubscriptionStatus.RECEIVING &&
            !(status == SubscriptionStatus.STARTING && continuingAfterPause)) {
            continuingAfterPause = false
            breakBefore = true
        }
    }

    fun record(elapsedMs: Long, motion: StepState, warmingUp: Boolean, segment: Long) {
        val sessionId = sessionId ?: return
        if (frozen || elapsedMs < 0) return
        if (elapsedMs > HrHistory.MAX_ELAPSED_MS) return
        if (continuingAfterPause) {
            if (resumeSegment == null) resumeSegment = segment
            if (resumeSegment != segment) {
                continuingAfterPause = false
                breakBefore = true
            } else if (warmingUp || motion.cadencePending) {
                // Resume warmup has no measurement to add to the activity-time chart.
                return
            }
        }
        val bucket = elapsedMs / 1000
        val replaced = points.lastOrNull()?.takeIf { it.secondBucket == bucket }
        if (replaced == null && points.size == HrHistory.MAX_POINTS) return
        val available = status == SubscriptionStatus.RECEIVING && motion.receivedAcc && !warmingUp
        val cadence = motion.cadence.takeIf { available }
        val missing = cadence == null
        val point = MotionHistoryPoint(sessionId, bucket, elapsedMs, cadence,
            breakBefore || (!continuingAfterPause && previousSegment != segment) || missing || replaced?.breakBefore == true)
        if (replaced == null) points.add(point) else points[points.lastIndex] = point
        previousSegment = segment
        continuingAfterPause = false
        breakBefore = missing
    }

    fun stop() {
        if (sessionId != null) frozen = true
    }

    fun resume(connectPrevious: Boolean = true) {
        val last = points.lastOrNull()
        continuingAfterPause = connectPrevious && !breakBefore && last?.cadence != null
        breakBefore = !continuingAfterPause
        resumeSegment = null
        frozen = false
    }

    fun since(bucket: Long): List<MotionHistoryPoint> = points.takeLastWhile { it.secondBucket >= bucket }

    fun snapshot(): List<MotionHistoryPoint> = points.toList()
}
