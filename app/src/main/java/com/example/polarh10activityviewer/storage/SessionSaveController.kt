package com.example.polarh10activityviewer.storage

import androidx.annotation.MainThread
import com.example.polarh10activityviewer.session.SessionSnapshot
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal enum class SaveStatus(val label: String) {
    IDLE("No session to save"), SAVING("Saving…"), SAVED("Saved"), FAILED("Save failed"),
    INELIGIBLE("Not eligible for saving"), DISCARDED("Session discarded")
}

internal data class SaveState(val status: SaveStatus = SaveStatus.IDLE, val sessionId: String? = null, val error: String? = null) {
    val blocksStart get() = status == SaveStatus.SAVING || status == SaveStatus.FAILED
}

// The application owns the scope; UI disposal cannot cancel an accepted write.
@MainThread
internal class SessionSaveController(private val scope: CoroutineScope, private val write: suspend (SessionSnapshot) -> Unit) {
    private val mutableState = MutableStateFlow(SaveState())
    val state = mutableState.asStateFlow()
    private var pending: SessionSnapshot? = null

    fun submit(snapshot: SessionSnapshot): Boolean {
        if (state.value.blocksStart || state.value.sessionId == snapshot.record.id) return false
        if (!snapshot.record.eligibleForSaving) {
            mutableState.value = SaveState(SaveStatus.INELIGIBLE, snapshot.record.id)
            return false
        }
        pending = snapshot
        savePending()
        return true
    }

    fun retry(): Boolean {
        if (state.value.status != SaveStatus.FAILED) return false
        savePending()
        return true
    }

    fun discard(): Boolean {
        if (state.value.status != SaveStatus.FAILED) return false
        pending = null
        mutableState.value = state.value.copy(status = SaveStatus.DISCARDED, error = null)
        return true
    }

    private fun savePending() {
        val snapshot = checkNotNull(pending)
        mutableState.value = SaveState(SaveStatus.SAVING, snapshot.record.id)
        scope.launch {
            try {
                write(snapshot)
                pending = null
                mutableState.value = SaveState(SaveStatus.SAVED, snapshot.record.id)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                mutableState.value = SaveState(SaveStatus.FAILED, snapshot.record.id,
                    error.message ?: error.javaClass.simpleName)
            }
        }
    }
}
