package com.example.polarh10activityviewer.session

import com.example.polarh10activityviewer.ble.checkedDataTypes
import com.example.polarh10activityviewer.ble.DataSubscriptions

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
    val endReason: String? = null
) {
    val ongoing: Boolean get() = status == SessionStatus.STARTING || status == SessionStatus.RUNNING
}

// Session timing and coordination reuse the existing subscription owner.
@MainThread
internal class SessionController(
    private val subscriptions: DataSubscriptions,
    private val now: () -> Long,
    private val clearAllReadings: () -> Unit,
    private val clearHr: () -> Unit
) {
    private val mutableState = MutableStateFlow(SessionState())
    val state = mutableState.asStateFlow()
    private var startedAt: Long? = null
    private var startingStreams = false

    fun accepts(generation: Long) = state.value.generation == generation && state.value.ongoing

    fun start(eligible: Boolean, startStreams: () -> Unit): Boolean {
        if (!eligible || state.value.ongoing || state.value.status == SessionStatus.STOPPING ||
            checkedDataTypes.any(subscriptions::isActive)) return false
        startingStreams = true
        startedAt = null
        mutableState.value = SessionState(SessionStatus.STARTING, state.value.generation + 1)
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
        if (!available || !state.value.ongoing || type !in checkedDataTypes || subscriptions.isActive(type)) return false
        val accepted = startStream()
        finishIfIdle()
        return accepted
    }

    fun onValidData(at: Long = now()) {
        if (state.value.status == SessionStatus.STARTING) {
            startedAt = at
            mutableState.value = state.value.copy(status = SessionStatus.RUNNING)
        }
    }

    fun onSubscriptionState(at: Long = now()) = finishIfIdle(at)

    fun refresh(generation: Long, at: Long = now()) {
        if (accepts(generation) && state.value.status == SessionStatus.RUNNING) {
            mutableState.value = state.value.copy(elapsedMs = elapsed(at))
        }
    }

    private fun elapsed(at: Long = now()) = startedAt?.let { at - it } ?: 0L

    // Chart reads do not advance session state or resume a stopped viewport.
    fun elapsedAt(at: Long = now()): Long =
        if (state.value.status == SessionStatus.RUNNING) elapsed(at) else state.value.elapsedMs

    fun stop(reason: String) {
        if (!state.value.ongoing) return
        mutableState.value = state.value.copy(
            status = SessionStatus.STOPPING, elapsedMs = elapsed(), endReason = reason
        )
        clearHr()
        subscriptions.stopAll()
        finishIfIdle()
    }

    private fun finishIfIdle(at: Long = now()) {
        if (startingStreams || checkedDataTypes.any(subscriptions::isActive)) return
        if (state.value.ongoing) {
            val reason = if (startedAt == null) "No data received. All stream attempts ended."
                else "All streams ended."
            mutableState.value = state.value.copy(
                status = SessionStatus.STOPPED, elapsedMs = elapsed(at), endReason = reason
            )
            clearHr()
        } else if (state.value.status == SessionStatus.STOPPING) {
            mutableState.value = state.value.copy(status = SessionStatus.STOPPED)
        }
    }
}
