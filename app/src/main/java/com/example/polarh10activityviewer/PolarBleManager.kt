package com.example.polarh10activityviewer

import android.Manifest
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import com.polar.androidcommunications.api.ble.model.DisInfo
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.PolarBleApiCallback
import com.polar.sdk.api.PolarBleApiDefaultImpl
import com.polar.sdk.api.PolarBleDisconnectInfo
import com.polar.sdk.api.model.PolarDeviceInfo
import com.polar.sdk.api.model.PolarHealthThermometerData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

enum class ScanStatus(val message: String) {
    NOT_STARTED("Scan not started."),
    SCANNING("Scanning for Polar H10 (up to 30 seconds)..."),
    STOPPED("Scan stopped."),
    TIMED_OUT("Scan finished after 30 seconds."),
    INTERRUPTED("Scan stopped because Bluetooth is unavailable."),
    ERROR("Scan failed.")
}

data class ScanState(
    val status: ScanStatus = ScanStatus.NOT_STARTED,
    val devices: List<PolarDeviceInfo> = emptyList(),
    val error: String? = null
)

enum class ConnectionStatus(val message: String) {
    NOT_CONNECTED("Not connected"),
    CONNECTING("Connecting"),
    CONNECTED("Connected"),
    DISCONNECTING("Disconnecting")
}

data class ConnectionDevice(val name: String, val deviceId: String)

data class ConnectionState(
    val status: ConnectionStatus = ConnectionStatus.NOT_CONNECTED,
    val device: ConnectionDevice? = null,
    val error: String? = null,
    val message: String? = null,
    val disconnectError: String? = null
)

class PolarBleManager(context: Context) {
    var onBluetoothStateChanged: (() -> Unit)? = null
    private val appContext = context.applicationContext
    private val savedDeviceStore = SavedDeviceStore.get(appContext)
    val savedDevicesState = savedDeviceStore.state
    private val mainHandler = Handler(Looper.getMainLooper())
    private var api: PolarBleApi? = null
    private var pendingCleanup: PolarBleApi? = null
    private val scanScope = CoroutineScope(Dispatchers.Main.immediate)
    private var scanJob: Job? = null
    private var scanGeneration = 0
    private val mutableScanState = MutableStateFlow(ScanState())
    val scanState = mutableScanState.asStateFlow()
    private val mutableConnectionState = MutableStateFlow(ConnectionState())
    val connectionState = mutableConnectionState.asStateFlow()
    private var connectionTimeout: Runnable? = null
    private var connectionConfirmed = false
    private var sdkUsedForConnection = false

    var initializationError: String? = null
        private set

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun initialize(retry: Boolean = false): Boolean {
        if (pendingCleanup != null && (!retry || !disposeSdk())) return false
        if (api != null) return true
        if (initializationError != null && !retry) return false
        initializationError = null

        return try {
            val created = PolarBleApiDefaultImpl.defaultImplementation(
                appContext,
                setOf(PolarBleApi.PolarBleSdkFeature.FEATURE_HR)
            )
            api = created
            sdkUsedForConnection = false
            created.setAutomaticReconnection(false)
            created.setApiCallback(object : PolarBleApiCallback() {
                override fun blePowerStateChanged(powered: Boolean) {
                    // Re-read current system state on the main thread; ignore old SDK instances.
                    mainHandler.post {
                        if (api === created) {
                            if (!powered) bluetoothUnavailable()
                            onBluetoothStateChanged?.invoke()
                        }
                    }
                }

                override fun deviceConnecting(polarDeviceInfo: PolarDeviceInfo) {
                    mainHandler.post {
                        if (matches(created, polarDeviceInfo) &&
                            mutableConnectionState.value.status == ConnectionStatus.DISCONNECTING) {
                            requestDisconnect(created, polarDeviceInfo.deviceId)
                        }
                    }
                }

                override fun deviceConnected(polarDeviceInfo: PolarDeviceInfo) {
                    mainHandler.post {
                        if (!matches(created, polarDeviceInfo)) return@post
                        connectionConfirmed = true
                        when (mutableConnectionState.value.status) {
                            ConnectionStatus.CONNECTING -> {
                                cancelConnectionTimeout()
                                mutableConnectionState.value = ConnectionState(
                                    ConnectionStatus.CONNECTED,
                                    ConnectionDevice(polarDeviceInfo.name, polarDeviceInfo.deviceId)
                                )
                                // Only this accepted success branch can create or update a record.
                                savedDeviceStore.save(SavedDevice(
                                    polarDeviceInfo.name, polarDeviceInfo.deviceId, System.currentTimeMillis()
                                ))
                            }
                            // A connection that arrives during cancellation must never become Connected.
                            ConnectionStatus.DISCONNECTING -> requestDisconnect(created, polarDeviceInfo.deviceId)
                            else -> Unit
                        }
                    }
                }

                override fun deviceDisconnected(polarDeviceInfo: PolarDeviceInfo, info: PolarBleDisconnectInfo) {
                    mainHandler.post {
                        if (!matches(created, polarDeviceInfo)) return@post
                        val state = mutableConnectionState.value
                        cancelConnectionTimeout()
                        connectionConfirmed = false
                        val reason = if (state.status == ConnectionStatus.DISCONNECTING) state.error else {
                            "Connection ended: ${info.reason.name.replace('_', ' ')}" +
                                (info.gattStatus?.let { " (GATT $it)" } ?: "") + ". Tap the device to retry."
                        }
                        mutableConnectionState.value = ConnectionState(
                            error = reason,
                            message = if (state.status == ConnectionStatus.DISCONNECTING && reason == null) {
                                "Disconnected. Tap a device to reconnect."
                            } else null
                        )
                    }
                }

                // Required by SDK 8.3.0; these features are not enabled in this step.
                override fun disInformationReceived(identifier: String, disInfo: DisInfo) = Unit
                override fun htsNotificationReceived(identifier: String, data: PolarHealthThermometerData) = Unit
            })
            true
        } catch (error: Exception) {
            release()
            initializationError = "SDK initialization failed (${error.javaClass.simpleName}). Please retry."
            Log.e("PolarBleManager", "SDK initialization failed", error)
            false
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun startScan() {
        if (mutableScanState.value.status == ScanStatus.SCANNING ||
            mutableConnectionState.value.status != ConnectionStatus.NOT_CONNECTED) return
        val currentApi = api ?: return
        val previousJob = scanJob
        val generation = ++scanGeneration
        mutableScanState.value = ScanState(status = ScanStatus.SCANNING)

        // Assign the job before starting it, even if the SDK fails synchronously.
        scanJob = scanScope.launch(start = CoroutineStart.LAZY) {
            val devicesById = linkedMapOf<String, PolarDeviceInfo>()
            try {
                val completed = withTimeoutOrNull(30_000L) {
                    // Let the previous scan release its subscription before restarting.
                    previousJob?.join()
                    currentApi.searchForDevice().collect { device ->
                        ensureActive()
                        if (generation != scanGeneration) return@collect
                        // PolarDeviceInfo has no model field; match the complete H10 name token.
                        if ((device.name == "Polar H10" || device.name.startsWith("Polar H10 ")) &&
                            device.deviceId.isNotBlank()) {
                            devicesById[device.deviceId] = device
                            mutableScanState.value = mutableScanState.value.copy(devices = devicesById.values.toList())
                        }
                    }
                    true
                }
                if (generation == scanGeneration) {
                    mutableScanState.value = mutableScanState.value.copy(
                        status = if (completed == null) ScanStatus.TIMED_OUT else ScanStatus.STOPPED
                    )
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                if (generation == scanGeneration) {
                    mutableScanState.value = mutableScanState.value.copy(
                        status = ScanStatus.ERROR,
                        error = "Scan failed (${error.javaClass.simpleName}). Check Bluetooth and permissions, then retry."
                    )
                    Log.e("PolarBleManager", "Scan failed", error)
                }
            } finally {
                if (generation == scanGeneration) scanJob = null
            }
        }
        scanJob?.start()
    }

    fun stopScan(status: ScanStatus = ScanStatus.STOPPED) {
        if (mutableScanState.value.status != ScanStatus.SCANNING) return
        // Invalidate queued results so a cancelled scan cannot change retained or new results.
        scanGeneration++
        scanJob?.cancel()
        mutableScanState.value = mutableScanState.value.copy(status = status)
    }

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun connect(deviceId: String) {
        if (mutableConnectionState.value.status != ConnectionStatus.NOT_CONNECTED) return
        val device = mutableScanState.value.devices.firstOrNull { it.deviceId == deviceId }
            ?.let { ConnectionDevice(it.name, it.deviceId) }
            ?: savedDevicesState.value.devices.firstOrNull { it.deviceId == deviceId }
                ?.let { ConnectionDevice(it.name, it.deviceId) }
            ?: return
        stopScan()
        // The SDK does not tag callbacks with an attempt ID. Never reuse an attempted SDK for retry.
        if (sdkUsedForConnection && !disposeSdk()) return
        if (!initialize()) {
            mutableConnectionState.value = ConnectionState(error = initializationError)
            onBluetoothStateChanged?.invoke()
            return
        }
        val currentApi = api ?: return
        sdkUsedForConnection = true
        connectionConfirmed = false
        mutableConnectionState.value = ConnectionState(ConnectionStatus.CONNECTING, device)
        connectionTimeout = Runnable {
            if (api === currentApi && mutableConnectionState.value.status == ConnectionStatus.CONNECTING) {
                interruptConnection("Connection timed out after 10 seconds. Tap the device to retry.")
            }
        }.also { mainHandler.postDelayed(it, 10_000L) }
        try {
            currentApi.connectToDevice(deviceId)
        } catch (error: Exception) {
            Log.e("PolarBleManager", "Connection request failed", error)
            interruptConnection("Connection failed (${error.javaClass.simpleName}). Tap the device to retry.")
        }
    }

    private fun matches(source: PolarBleApi, device: PolarDeviceInfo): Boolean =
        api === source && mutableConnectionState.value.device?.deviceId == device.deviceId

    fun bluetoothUnavailable() {
        stopScan(ScanStatus.INTERRUPTED)
        interruptConnection("Bluetooth is unavailable. Enable Bluetooth and permissions, then retry.")
    }

    fun leaveSession() {
        stopScan()
        interruptConnection(reason = null, message = "Session left the foreground. Reconnect manually when available.")
    }

    fun disconnect() {
        if (mutableConnectionState.value.status != ConnectionStatus.CONNECTED) return
        interruptConnection(reason = null, message = "Disconnect requested.")
    }

    fun retryDisconnect() {
        val state = mutableConnectionState.value
        if (state.status != ConnectionStatus.DISCONNECTING || state.disconnectError == null) return
        val currentApi = api ?: return
        val device = state.device ?: return
        requestDisconnect(currentApi, device.deviceId)
    }

    private fun interruptConnection(reason: String?, message: String? = null) {
        val state = mutableConnectionState.value
        if (state.status == ConnectionStatus.NOT_CONNECTED || state.status == ConnectionStatus.DISCONNECTING) return
        val currentApi = api ?: return
        val device = state.device ?: return
        cancelConnectionTimeout()
        mutableConnectionState.value = state.copy(status = ConnectionStatus.DISCONNECTING, error = reason, message = message)
        requestDisconnect(currentApi, device.deviceId)
        // Cancelling an SDK search can produce no disconnect callback because no session opened.
        // Dispose that unconfirmed attempt before allowing retry; this is not a confirmed disconnect.
        mainHandler.post {
            if (api === currentApi && !connectionConfirmed &&
                mutableConnectionState.value.status == ConnectionStatus.DISCONNECTING && disposeSdk()) {
                mutableConnectionState.value = ConnectionState(
                    error = reason,
                    message = "No connection was confirmed; the request was cancelled."
                )
                onBluetoothStateChanged?.invoke()
            }
        }
    }

    private fun requestDisconnect(source: PolarBleApi, deviceId: String) {
        val state = mutableConnectionState.value
        if (api !== source || state.device?.deviceId != deviceId ||
            state.status != ConnectionStatus.DISCONNECTING) return
        // A disconnect error is the retry flag. Clear it before sending to block duplicate taps.
        mutableConnectionState.value = state.copy(disconnectError = null)
        try {
            source.disconnectFromDevice(deviceId)
        } catch (error: Exception) {
            Log.e("PolarBleManager", "Cancellation/disconnection failed", error)
            mutableConnectionState.value = mutableConnectionState.value.copy(
                disconnectError = "Unable to disconnect (${error.javaClass.simpleName}). Please retry."
            )
        }
    }

    private fun cancelConnectionTimeout() {
        connectionTimeout?.let { mainHandler.removeCallbacks(it) }
        connectionTimeout = null
    }

    private fun disposeSdk(): Boolean {
        cancelConnectionTimeout()
        stopScan(ScanStatus.INTERRUPTED)
        scanJob?.cancel()
        val previous = api ?: pendingCleanup
        // Invalidate queued callbacks before shutdown, including callbacks for the same device ID.
        api = null
        mutableConnectionState.value = mutableConnectionState.value.copy(disconnectError = null)
        return try {
            previous?.shutDown()
            pendingCleanup = null
            true
        } catch (error: Exception) {
            // Retain only for cleanup. Its callbacks stay invalid and initialization is blocked.
            pendingCleanup = previous
            initializationError = "SDK cleanup failed (${error.javaClass.simpleName}). Restart Session."
            mutableConnectionState.value = mutableConnectionState.value.copy(error = initializationError)
            Log.e("PolarBleManager", "SDK cleanup failed", error)
            false
        }
    }

    fun release() {
        val state = mutableConnectionState.value
        val disposed = disposeSdk()
        mainHandler.removeCallbacksAndMessages(null)
        if (disposed) {
            initializationError = null
            if (state.status != ConnectionStatus.NOT_CONNECTED) {
                // Shutdown ends local ownership; do not claim a callback-confirmed disconnection.
                mutableConnectionState.value = if (connectionConfirmed) {
                    state.copy(
                        status = ConnectionStatus.DISCONNECTING,
                        error = "SDK released. Disconnection was not confirmed; reopen Session before reconnecting.",
                        disconnectError = null
                    )
                } else {
                    ConnectionState(error = "Connection request cancelled when the SDK was released.")
                }
            }
        }
    }
}
