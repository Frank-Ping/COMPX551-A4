package com.example.polarh10activityviewer.storage

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal class SessionStorage private constructor(context: Context) {
    val database = SessionDatabase(context.applicationContext)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    val recording = SignalWriter(scope, database::recoverInterrupted, database::writeSignals, database::delete)
    val saves = SessionSaveController(scope) { snapshot ->
        recording.drain()
        database.save(snapshot)
    }.apply {
        beforeRetry = { recording.retry(); recording.drain() }
        discardPending = { id -> recording.discard(id) }
    }

    companion object {
        @Volatile private var instance: SessionStorage? = null
        fun get(context: Context): SessionStorage = instance ?: synchronized(this) {
            instance ?: SessionStorage(context.applicationContext).also { instance = it }
        }
    }
}
