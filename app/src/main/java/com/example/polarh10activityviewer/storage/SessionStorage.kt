package com.example.polarh10activityviewer.storage

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal class SessionStorage private constructor(context: Context) {
    val database = SessionDatabase(context.applicationContext)
    val saves = SessionSaveController(CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate), database::save)

    companion object {
        @Volatile private var instance: SessionStorage? = null
        fun get(context: Context): SessionStorage = instance ?: synchronized(this) {
            instance ?: SessionStorage(context.applicationContext).also { instance = it }
        }
    }
}
