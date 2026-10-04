package com.example.polarh10activityviewer.history

import com.example.polarh10activityviewer.ble.HeartRateReading
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.session.HrHistoryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal data class HrHistoryState(
    val sessionId: String? = null,
    val pointCount: Int = 0,
    val firstElapsedMs: Long? = null,
    val lastElapsedMs: Long? = null,
    val frozen: Boolean = false,
    val limitReached: Boolean = false
)

// Owned by the retained manager; only accepted nonempty HR events add records.
internal class HrHistory {
    private val points = mutableListOf<HrHistoryPoint>()
    private val mutableState = MutableStateFlow(HrHistoryState())
    val state = mutableState.asStateFlow()
    private var previousElapsedMs: Long? = null
    private var breakBefore = true

    fun reset(sessionId: String? = null) {
        points.clear()
        previousElapsedMs = null
        breakBefore = true
        mutableState.value = HrHistoryState(sessionId = sessionId)
    }

    fun onSubscriptionState(status: SubscriptionStatus) {
        if (!state.value.frozen && status != SubscriptionStatus.RECEIVING) breakBefore = true
    }

    fun receive(elapsedMs: Long, reading: HeartRateReading?) {
        val current = state.value
        val sessionId = current.sessionId ?: return
        if (current.frozen || elapsedMs < 0) return
        if (elapsedMs > MAX_ELAPSED_MS) {
            mutableState.value = current.copy(limitReached = true)
            return
        }
        val bucket = elapsedMs / 1000
        val replaced = points.lastOrNull()?.takeIf { it.secondBucket == bucket }
        if (replaced == null && points.size == MAX_POINTS) return
        val gap = previousElapsedMs?.let { elapsedMs - it > 3000 } ?: false
        val point = HrHistoryPoint(sessionId, bucket, elapsedMs, reading?.bpm,
            breakBefore || gap || reading == null || replaced?.breakBefore == true)
        if (replaced == null) points.add(point) else points[points.lastIndex] = point
        previousElapsedMs = elapsedMs
        breakBefore = reading == null
        // Publish metadata only; the UI never copies the growing history on every event.
        mutableState.value = current.copy(pointCount = points.size,
            firstElapsedMs = points.first().elapsedMs, lastElapsedMs = point.elapsedMs,
            limitReached = elapsedMs == MAX_ELAPSED_MS)
    }

    fun stop() {
        if (state.value.sessionId != null) mutableState.value = state.value.copy(frozen = true)
    }

    fun resume() {
        breakBefore = true
        previousElapsedMs = null
        mutableState.value = state.value.copy(frozen = false)
    }

    fun since(bucket: Long): List<HrHistoryPoint> = points.takeLastWhile { it.secondBucket >= bucket }

    fun snapshot(): List<HrHistoryPoint> = points.toList()

    companion object {
        const val MAX_ELAPSED_MS = 14_400_000L
        const val MAX_POINTS = 14_401
    }
}
