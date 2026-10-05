package com.example.polarh10activityviewer.ble

import androidx.annotation.MainThread
import com.polar.sdk.api.model.PolarDeviceInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

@MainThread
internal class PolarScanner(
    private val scope: CoroutineScope,
    private val onError: (Exception) -> Unit = {}
) {
    private var scanJob: Job? = null
    private var scanGeneration = 0
    private val mutableState = MutableStateFlow(ScanState())
    val state = mutableState.asStateFlow()

    fun start(search: () -> Flow<PolarDeviceInfo>) {
        if (mutableState.value.status == ScanStatus.SCANNING) return
        val previousJob = scanJob
        val generation = ++scanGeneration
        mutableState.value = ScanState(status = ScanStatus.SCANNING)

        // Assign the job before starting it, even if the SDK fails synchronously.
        scanJob = scope.launch(start = CoroutineStart.LAZY) {
            val devicesById = linkedMapOf<String, PolarDeviceInfo>()
            try {
                val completed = withTimeoutOrNull(30_000L) {
                    // Let the previous scan release its subscription before restarting.
                    previousJob?.join()
                    search().collect { device ->
                        ensureActive()
                        if (generation != scanGeneration) return@collect
                        // PolarDeviceInfo has no model field; match the complete H10 name token.
                        if ((device.name == "Polar H10" || device.name.startsWith("Polar H10 ")) &&
                            device.deviceId.isNotBlank()) {
                            devicesById[device.deviceId] = device
                            mutableState.value = mutableState.value.copy(devices = devicesById.values.toList())
                        }
                    }
                    true
                }
                if (generation == scanGeneration) {
                    mutableState.value = mutableState.value.copy(
                        status = if (completed == null) ScanStatus.TIMED_OUT else ScanStatus.STOPPED
                    )
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                if (generation == scanGeneration) {
                    mutableState.value = mutableState.value.copy(
                        status = ScanStatus.ERROR,
                        error = "Scan failed (${error.javaClass.simpleName}). Check Bluetooth and permissions, then retry."
                    )
                    onError(error)
                }
            } finally {
                if (generation == scanGeneration) scanJob = null
            }
        }
        scanJob?.start()
    }

    fun stop(status: ScanStatus = ScanStatus.STOPPED) {
        if (mutableState.value.status != ScanStatus.SCANNING) return
        // Invalidate queued results so a cancelled scan cannot change retained or new results.
        scanGeneration++
        scanJob?.cancel()
        mutableState.value = mutableState.value.copy(status = status)
    }

    fun release() {
        stop(ScanStatus.INTERRUPTED)
        scanJob?.cancel()
    }
}
