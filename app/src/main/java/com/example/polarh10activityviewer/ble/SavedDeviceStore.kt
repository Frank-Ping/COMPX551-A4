package com.example.polarh10activityviewer.ble

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONArray
import org.json.JSONObject

data class SavedDevice(val name: String, val deviceId: String, val lastConnectedAt: Long)

data class SavedDevicesState(
    val devices: List<SavedDevice> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

class SavedDeviceStore private constructor(context: Context) {
    private val appContext = context.applicationContext
    private val preferences by lazy { appContext.getSharedPreferences("saved_devices", Context.MODE_PRIVATE) }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = Mutex()
    private var loaded = false
    private val mutableState = MutableStateFlow(SavedDevicesState())
    val state = mutableState.asStateFlow()

    init {
        scope.launch {
            mutex.withLock {
                try {
                    loadIfNeeded()
                } catch (error: Exception) {
                    mutableState.value = SavedDevicesState(
                        loading = false,
                        error = "Unable to read saved devices (${error.javaClass.simpleName}). Existing storage was not overwritten."
                    )
                }
            }
        }
    }

    private fun loadIfNeeded() {
        if (loaded) return
        val json = JSONArray(preferences.getString("devices", "[]") ?: "[]")
        val devices = linkedMapOf<String, SavedDevice>()
        for (index in 0 until json.length()) {
            val item = json.getJSONObject(index)
            val device = SavedDevice(item.getString("name"), item.getString("deviceId"), item.getLong("lastConnectedAt"))
            require(device.name.isNotBlank() && device.deviceId.isNotBlank() && device.lastConnectedAt > 0)
            val previous = devices[device.deviceId]
            if (previous == null || device.lastConnectedAt >= previous.lastConnectedAt) devices[device.deviceId] = device
        }
        mutableState.value = SavedDevicesState(devices.values.sortedByDescending { it.lastConnectedAt }, loading = false)
        loaded = true
    }

    // KTX edit discards commit's Boolean result, which is needed to report write failures.
    @SuppressLint("ApplySharedPref", "UseKtx")
    fun save(device: SavedDevice) {
        // Application-scoped writes finish even when Session closes immediately after connection.
        scope.launch {
            mutex.withLock {
                try {
                    loadIfNeeded()
                    val existing = mutableState.value.devices
                    val previous = existing.firstOrNull { it.deviceId == device.deviceId }
                    if (previous != null && previous.lastConnectedAt > device.lastConnectedAt) return@withLock
                    val updated = (existing.filterNot { it.deviceId == device.deviceId } + device)
                        .sortedByDescending { it.lastConnectedAt }
                    val json = JSONArray()
                    updated.forEach {
                        json.put(JSONObject().put("name", it.name).put("deviceId", it.deviceId)
                            .put("lastConnectedAt", it.lastConnectedAt))
                    }
                    // commit reports failure; disk access is serialized on an IO dispatcher.
                    check(preferences.edit().putString("devices", json.toString()).commit())
                    mutableState.value = SavedDevicesState(updated, loading = false)
                } catch (error: Exception) {
                    mutableState.value = mutableState.value.copy(
                        loading = false,
                        error = "Unable to save device (${error.javaClass.simpleName}). The connection is unaffected; reconnect to retry saving."
                    )
                }
            }
        }
    }

    companion object {
        @Volatile private var instance: SavedDeviceStore? = null

        fun get(context: Context): SavedDeviceStore = instance ?: synchronized(this) {
            instance ?: SavedDeviceStore(context).also { instance = it }
        }
    }
}
