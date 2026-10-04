package com.example.polarh10activityviewer.storage

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal data class RecordingState(val ready: Boolean = false, val busy: Boolean = false, val error: String? = null) {
    val blocked get() = !ready || busy || error != null
}

// Main-thread ownership of the queue; the supplied database operations run on IO.
internal class SignalWriter(private val scope: CoroutineScope,
    private val initialize: suspend () -> Unit,
    private val write: suspend (List<SignalBatch>) -> Unit,
    private val delete: suspend (String) -> Unit,
    private val capacity: Int = 1024 * 1024) {
    private val mutableState = MutableStateFlow(RecordingState())
    val state = mutableState.asStateFlow()
    private val queue = ArrayDeque<SignalBatch>()
    private var job: Job? = null
    var onFailure: () -> Unit = {}
    var pendingBytes = 0
        private set

    init { retry() }

    fun canAccept(bytes: Int) = !state.value.blocked && pendingBytes + bytes <= capacity
    fun fail(message: String) {
        if (state.value.error != null) return
        mutableState.value = state.value.copy(error = message)
        onFailure()
    }
    fun enqueue(batch: SignalBatch) {
        check(pendingBytes + batch.size <= capacity) { "Recording queue capacity exceeded" }
        queue.addLast(batch); pendingBytes += batch.size
        pump()
    }
    private fun pump() {
        if (job?.isActive == true || state.value.blocked) return
        job = scope.launch { writeQueue() }
    }
    private suspend fun writeQueue() {
        try {
            while (queue.isNotEmpty() && state.value.error == null) {
                val group = queue.take(5)
                write(group)
                repeat(group.size) { pendingBytes -= queue.removeFirst().size }
            }
        } catch (cancelled: CancellationException) { throw cancelled }
        catch (error: Exception) { fail(error.message ?: "Database write failed") }
    }

    fun retry() {
        if (state.value.busy) return
        mutableState.value = state.value.copy(busy = true)
        val prior = job
        job = scope.launch {
            prior?.join()
            try {
                if (!state.value.ready) initialize()
                mutableState.value = RecordingState(ready = true, busy = true)
                writeQueue()
            } catch (cancelled: CancellationException) { throw cancelled }
            catch (error: Exception) { fail(error.message ?: "Storage initialization failed") }
            finally { mutableState.value = state.value.copy(busy = false) }
        }
    }

    suspend fun drain(retryFailure: Boolean = false) {
        if (retryFailure && state.value.error != null) retry()
        job?.join()
        check(state.value.ready && state.value.error == null) { state.value.error ?: "Storage is not ready" }
        // A callback can append during an in-flight transaction.
        if (queue.isNotEmpty()) { pump(); job?.join() }
        check(queue.isEmpty() && state.value.error == null) { state.value.error ?: "Recording write pending" }
    }

    suspend fun discard(id: String) {
        check(!state.value.busy) { "Storage operation already in progress" }
        mutableState.value = state.value.copy(busy = true)
        try {
            job?.join()
            delete(id)
            queue.clear(); pendingBytes = 0
            mutableState.value = RecordingState(ready = true)
        } finally { mutableState.value = state.value.copy(busy = false) }
    }
}
