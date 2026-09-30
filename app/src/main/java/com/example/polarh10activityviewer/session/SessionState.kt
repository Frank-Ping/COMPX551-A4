package com.example.polarh10activityviewer.session

import com.example.polarh10activityviewer.ble.checkedDataTypes
import com.example.polarh10activityviewer.ble.DataSubscriptions
import com.example.polarh10activityviewer.ble.ConnectionDevice
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import java.util.UUID

import androidx.annotation.MainThread
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal enum class SessionStatus(val label: String) {
    IDLE("Idle"), STARTING("Starting"), RUNNING("Running"), STOPPING("Stopping"), STOPPED("Stopped")
}

internal data class SessionState(
    val status: SessionStatus = SessionStatus.IDLE,
    val generation: Long = 0,
    val elapsedMs: Long = 0,
    val endReason: String? = null,
    val record: SessionRecord? = null
) {
    val ongoing: Boolean get() = status == SessionStatus.STARTING || status == SessionStatus.RUNNING
}

// Session timing and coordination reuse the existing subscription owner.
@MainThread
internal class SessionController(
    private val subscriptions: DataSubscriptions,
    private val now: () -> Long,
    private val clearAllReadings: () -> Unit,
    private val clearHr: () -> Unit,
    private val readSummary: (Long) -> SessionSummary,
    private val wallNow: () -> Long = System::currentTimeMillis,
    private val onSummaryFrozen: (SessionRecord) -> Unit = {},
    private val canStart: () -> Boolean = { true }
) {
    private val mutableState = MutableStateFlow(SessionState())
    val state = mutableState.asStateFlow()
    private var startedAt: Long? = null
    private var startingStreams = false

    fun accepts(generation: Long) = state.value.generation == generation && state.value.ongoing

    fun start(eligible: Boolean, device: ConnectionDevice? = null, startStreams: () -> Unit): Boolean {
        if (!eligible || !canStart() || state.value.ongoing || state.value.status == SessionStatus.STOPPING ||
            checkedDataTypes.any(subscriptions::isActive)) return false
        startingStreams = true
        startedAt = null
        mutableState.value = SessionState(SessionStatus.STARTING, state.value.generation + 1,
            record = SessionRecord(UUID.randomUUID().toString(), wallNow(), device?.copy()))
        subscriptions.reset()
        clearAllReadings()
        try {
            startStreams()
        } finally {
            startingStreams = false
            finishIfIdle()
        }
        return true
    }

    fun retry(type: PolarDeviceDataType, available: Boolean, startStream: () -> Boolean): Boolean {
        if (checkTimeLimit()) return false
        if (!available || !state.value.ongoing || type !in checkedDataTypes || subscriptions.isActive(type)) return false
        val accepted = startStream()
        finishIfIdle()
        return accepted
    }

    fun onValidData(at: Long = now(), receivedAt: Long = wallNow()) {
        if (state.value.status == SessionStatus.STARTING) {
            startedAt = at
            mutableState.value = state.value.copy(status = SessionStatus.RUNNING,
                record = state.value.record?.copy(startedAt = receivedAt))
        }
    }

    fun onSubscriptionState(type: PolarDeviceDataType, status: SubscriptionStatus, at: Long = now()) {
        if (checkTimeLimit(at)) return
        if (state.value.ongoing) {
            val record = state.value.record!!
            val previous = record.streams.getValue(type)
            val ended = status in setOf(SubscriptionStatus.IDLE, SubscriptionStatus.STOPPING,
                SubscriptionStatus.STOPPED, SubscriptionStatus.FAILED)
            mutableState.value = state.value.copy(record = record.copy(streams = record.streams +
                (type to previous.copy(received = previous.received || status == SubscriptionStatus.RECEIVING,
                    missing = previous.missing || ended,
                    failed = previous.failed || status == SubscriptionStatus.FAILED))))
        }
        finishIfIdle(at)
    }

    fun markMissing(type: PolarDeviceDataType) {
        if (!state.value.ongoing) return
        val record = state.value.record!!
        mutableState.value = state.value.copy(record = record.copy(streams = record.streams +
            (type to record.streams.getValue(type).copy(missing = true))))
    }

    fun refresh(generation: Long, at: Long = now()) {
        if (accepts(generation)) {
            if (checkTimeLimit(at)) return
            val duration = elapsed(at)
            mutableState.value = state.value.copy(elapsedMs = duration,
                record = state.value.record!!.copy(durationMs = duration, summary = readSummary(duration)))
        }
    }

    private fun elapsed(at: Long = now()) = startedAt?.let { at - it } ?: 0L

    // Chart reads do not advance session state or resume a stopped viewport.
    fun elapsedAt(at: Long = now()): Long =
        if (state.value.status == SessionStatus.RUNNING) elapsed(at).coerceAtMost(TIME_LIMIT_MS) else state.value.elapsedMs

    // Call before accepting a stream event so late batches cannot change any statistics.
    fun checkTimeLimit(at: Long = now()): Boolean {
        if (state.value.status != SessionStatus.RUNNING || elapsed(at) < TIME_LIMIT_MS) return false
        finish("TIME_LIMIT", interrupted = false, duration = TIME_LIMIT_MS,
            endedAt = wallNow() - (elapsed(at) - TIME_LIMIT_MS))
        return true
    }

    fun stop(reason: String, interrupted: Boolean = true) {
        if (!state.value.ongoing || checkTimeLimit()) return
        finish(reason, interrupted, elapsed(), wallNow())
    }

    private fun finish(reason: String, interrupted: Boolean, duration: Long, endedAt: Long) {
        mutableState.value = state.value.copy(
            status = SessionStatus.STOPPING, elapsedMs = duration, endReason = reason,
            record = state.value.record!!.copy(endedAt = endedAt, endReason = reason, interrupted = interrupted)
        )
        clearHr()
        freezeSummary()
        subscriptions.stopAll()
        finishIfIdle()
    }

    private fun freezeSummary() {
        val duration = state.value.elapsedMs
        mutableState.value = state.value.copy(record = state.value.record!!.copy(
            durationMs = duration, summary = readSummary(duration)))
        onSummaryFrozen(state.value.record!!)
    }

    private fun finishIfIdle(at: Long = now()) {
        if (startingStreams || checkedDataTypes.any(subscriptions::isActive)) return
        if (checkTimeLimit(at)) return
        if (state.value.ongoing) {
            val reason = if (startedAt == null) "No data received. All stream attempts ended."
                else "All streams ended."
            mutableState.value = state.value.copy(
                status = SessionStatus.STOPPED, elapsedMs = elapsed(at), endReason = reason,
                record = state.value.record!!.copy(endedAt = wallNow(), endReason = reason)
            )
            clearHr()
            freezeSummary()
        } else if (state.value.status == SessionStatus.STOPPING) {
            mutableState.value = state.value.copy(status = SessionStatus.STOPPED)
        }
    }

    companion object { const val TIME_LIMIT_MS = 14_400_000L }
}
