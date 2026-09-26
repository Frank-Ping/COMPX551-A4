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

class PolarBleManager(context: Context, private val onBluetoothStateChanged: () -> Unit) {
    private val appContext = context.applicationContext
    private val mainHandler = Handler(Looper.getMainLooper())
    private var api: PolarBleApi? = null
    private val scanScope = CoroutineScope(Dispatchers.Main.immediate)
    private var scanJob: Job? = null
    private var scanGeneration = 0
    private val mutableScanState = MutableStateFlow(ScanState())
    val scanState = mutableScanState.asStateFlow()

    var initializationError: String? = null
        private set

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun initialize(retry: Boolean = false): Boolean {
        if (api != null) return true
        if (initializationError != null && !retry) return false
        initializationError = null

        return try {
            val created = PolarBleApiDefaultImpl.defaultImplementation(
                appContext,
                setOf(PolarBleApi.PolarBleSdkFeature.FEATURE_HR)
            )
            api = created
            created.setApiCallback(object : PolarBleApiCallback() {
                override fun blePowerStateChanged(powered: Boolean) {
                    // Re-read current system state on the main thread; ignore old SDK instances.
                    mainHandler.post {
                        if (api === created) {
                            if (!powered) stopScan(ScanStatus.INTERRUPTED)
                            onBluetoothStateChanged()
                        }
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
        if (mutableScanState.value.status == ScanStatus.SCANNING) return
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

    fun release() {
        stopScan(ScanStatus.INTERRUPTED)
        scanJob?.cancel()
        val previous = api
        api = null
        mainHandler.removeCallbacksAndMessages(null)
        try {
            previous?.shutDown()
        } catch (error: Exception) {
            Log.e("PolarBleManager", "SDK cleanup failed", error)
        }
        initializationError = null
    }
}
