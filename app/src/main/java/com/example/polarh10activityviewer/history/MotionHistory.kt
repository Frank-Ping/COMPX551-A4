package com.example.polarh10activityviewer.history

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.session.MotionHistoryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class MotionHistoryState(
    val sessionId: String? = null,
    val pointCount: Int = 0,
    val firstElapsedMs: Long? = null,
    val lastElapsedMs: Long? = null,
    val frozen: Boolean = false,
    val limitReached: Boolean = false
)

// Records existing refresh results; it neither calculates motion nor creates missed ticks.
internal class MotionHistory {
    private val points = mutableListOf<MotionHistoryPoint>()
    private val mutableState = MutableStateFlow(MotionHistoryState())
    val state = mutableState.asStateFlow()
    private var status = SubscriptionStatus.IDLE
    private var previousSegment: Long? = null
    private var breakBefore = true

    fun start(sessionId: String) {
        points.clear()
        status = SubscriptionStatus.IDLE
        previousSegment = null
        breakBefore = true
        mutableState.value = MotionHistoryState(sessionId = sessionId)
    }

    fun onSubscriptionState(status: SubscriptionStatus) {
        if (state.value.frozen) return
        this.status = status
        if (status != SubscriptionStatus.RECEIVING) breakBefore = true
    }

    fun record(elapsedMs: Long, motion: StepState, warmingUp: Boolean, segment: Long) {
        val current = state.value
        val sessionId = current.sessionId ?: return
        if (current.frozen || elapsedMs < 0) return
        if (elapsedMs > HrHistory.MAX_ELAPSED_MS) {
            mutableState.value = current.copy(limitReached = true)
            return
        }
        val bucket = elapsedMs / 1000
        val replaced = points.lastOrNull()?.takeIf { it.secondBucket == bucket }
        if (replaced == null && points.size == HrHistory.MAX_POINTS) return
        val available = status == SubscriptionStatus.RECEIVING && motion.receivedAcc && !warmingUp
        val cadence = motion.cadence.takeIf { available }
        val speed = motion.speed.takeIf { available }
        val missing = cadence == null || speed == null
        val point = MotionHistoryPoint(sessionId, bucket, elapsedMs, cadence, speed,
            breakBefore || previousSegment != segment || missing || replaced?.breakBefore == true)
        if (replaced == null) points.add(point) else points[points.lastIndex] = point
        previousSegment = segment
        breakBefore = missing
        mutableState.value = current.copy(pointCount = points.size,
            firstElapsedMs = points.first().elapsedMs, lastElapsedMs = elapsedMs,
            limitReached = elapsedMs == HrHistory.MAX_ELAPSED_MS)
    }

    fun stop() {
        if (state.value.sessionId != null) mutableState.value = state.value.copy(frozen = true)
    }

    fun snapshot(): List<MotionHistoryPoint> = points.toList()
}
