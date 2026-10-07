package com.example.polarh10activityviewer.ble

import com.example.polarh10activityviewer.chart.ChartKind
import com.example.polarh10activityviewer.sensor.h10EcgSamples
import com.example.polarh10activityviewer.storage.SessionStorage

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import com.polar.androidcommunications.api.ble.model.DisInfo
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.PolarBleApi.PolarBleSdkFeature
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.PolarBleApiCallback
import com.polar.sdk.api.PolarBleApiDefaultImpl
import com.polar.sdk.api.PolarBleDisconnectInfo
import com.polar.sdk.api.model.PolarDeviceInfo
import com.polar.sdk.api.model.PolarHealthThermometerData
import com.polar.sdk.api.model.PolarSensorSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter

class PolarBleManager(context: Context) {
    var onBluetoothStateChanged: (() -> Unit)? = null
    private val appContext = context.applicationContext
    internal val storage = SessionStorage.get(appContext)
    private val savedDeviceStore = SavedDeviceStore.get(appContext)
    val savedDevicesState = savedDeviceStore.state
    fun clearSavedDevices() = savedDeviceStore.clear()
    private val mainHandler = Handler(Looper.getMainLooper())
    private var api: PolarBleApi? = null
    private var pendingCleanup: PolarBleApi? = null
    private val managerScope = CoroutineScope(Dispatchers.Main.immediate)
    private val scanner = PolarScanner(managerScope) { error ->
        Log.e("PolarBleManager", "Scan failed", error)
    }
    val scanState = scanner.state
    private val mutableConnectionState = MutableStateFlow(ConnectionState())
    val connectionState = mutableConnectionState.asStateFlow()
    private val deviceBattery = DeviceBattery()
    val batteryLevel = deviceBattery.level
    private var connectionTimeout: Runnable? = null
    private var connectionConfirmed = false
    private var sdkUsedForConnection = false
    private val readiness = PolarDataReadiness(
        CoroutineScope(Dispatchers.Main.immediate),
        ::readinessMatches, ::bluetoothAvailableForData, ::onlineStreamActive
    )
    val dataReadiness = readiness.state
    private val coordinator = SessionDataCoordinator(storage, managerScope)
    private val dataSubscriptions = coordinator.dataSubscriptions
    private val session = coordinator.session
    internal val lastSnapshot = coordinator.lastSnapshot
    val heartRate = coordinator.heartRate
    val heartRateStatistics = coordinator.heartRateStatistics
    val heartRateMessage = coordinator.heartRateMessage
    internal val heartRateZoneState = coordinator.heartRateZoneState
    internal val stepState = coordinator.stepState
    internal val liveCharts = coordinator.liveCharts
    internal val subscriptionStates = coordinator.subscriptionStates
    internal val sessionState = coordinator.sessionState

    @MainThread
    fun startSession(): Boolean = session.start(
        eligible = connectedForData() && hasReadyDataType(),
        device = mutableConnectionState.value.device
    ) {
        startSessionStreams()
    }

    private fun startSessionStreams() {
        checkedDataTypes.forEach { type ->
            val readiness = dataReadiness.value.getValue(type)
            if (readiness.status == DataReadinessStatus.READY && readiness.configurationComplete) {
                startStream(type)
            } else {
                dataSubscriptions.unavailable(type, readiness.error ?: if (readiness.status == DataReadinessStatus.READY)
                    "$type configuration needs confirmation." else "$type: ${readiness.status.message}.")
            }
        }
    }

    @MainThread
    fun stopSession() = session.stop("Stopped by user.", interrupted = false, reset = true)

    @MainThread
    fun stopAccStream() = dataSubscriptions.stop(PolarDeviceDataType.ACC)

    @MainThread
    fun pauseSession() = session.pause()

    @MainThread
    fun resumeSession() = session.resume(!storage.recording.state.value.blocked && connectedForData() &&
        sessionState.value.acceptsDevice(mutableConnectionState.value.device?.deviceId) && hasReadyDataType(),
        ::startSessionStreams)

    private fun hasReadyDataType() = dataReadiness.value.values.any {
        it.status == DataReadinessStatus.READY && it.configurationComplete
    }

    fun refreshSessionTime(generation: Long) = coordinator.refreshSessionTime(generation)

    fun discardRecording() = coordinator.discardRecording()

    internal fun chartSnapshot(kind: ChartKind) = coordinator.chartSnapshot(kind)

    private fun connectedForData() = api != null &&
        mutableConnectionState.value.status == ConnectionStatus.CONNECTED && bluetoothAvailableForData()

    private fun startStream(type: PolarDeviceDataType): Boolean {
        if (type != PolarDeviceDataType.HR && readiness.checking) {
            dataSubscriptions.unavailable(type, "Settings check in progress. Retry when it finishes.")
            return false
        }
        return when (type) {
            PolarDeviceDataType.HR -> startHr()
            PolarDeviceDataType.ACC -> startAcc()
            PolarDeviceDataType.ECG -> startEcg()
            else -> false
        }
    }

    @MainThread
    private fun startHr(): Boolean = startDataSubscription(
        PolarDeviceDataType.HR,
        stream = { source, identifier ->
            readiness.checkFeature(source, identifier, PolarDeviceDataType.HR)
            source.startHrStreaming(identifier).filter { it.samples.isNotEmpty() }
        },
        onData = coordinator::receiveHr
    )

    @MainThread
    private fun startAcc(): Boolean = startDataSubscription(
        PolarDeviceDataType.ACC,
        stream = { source, identifier ->
            val settings = readiness.currentStreamSettings(source, identifier, PolarDeviceDataType.ACC)
            source.startAccStreaming(identifier, settings).filter { it.samples.isNotEmpty() }
        },
        onData = coordinator::receiveAcc
    )

    @MainThread
    private fun startEcg(): Boolean = startDataSubscription(
        PolarDeviceDataType.ECG,
        stream = { source, identifier ->
            val settings = readiness.currentStreamSettings(source, identifier, PolarDeviceDataType.ECG)
            source.startEcgStreaming(identifier, settings).h10EcgSamples()
        },
        onData = { data, receivedTime, receivedDate ->
            val sampleRate = dataReadiness.value.getValue(PolarDeviceDataType.ECG)
                .selected.getValue(PolarSensorSetting.SettingType.SAMPLE_RATE)
            coordinator.receiveEcg(data, receivedTime, receivedDate, sampleRate)
        }
    )

    @MainThread
    private fun <T> startDataSubscription(
        type: PolarDeviceDataType,
        stream: suspend (PolarBleApi, String) -> Flow<T>,
        onData: (T, Long, Long) -> Unit
    ): Boolean {
        val source = api ?: return false
        val identifier = mutableConnectionState.value.device?.deviceId ?: return false
        val generation = sessionState.value.generation
        return dataSubscriptions.start(
            type,
            canStart = { connectedForData() && session.accepts(generation) },
            isCurrent = { session.accepts(generation) && readinessMatches(source, identifier) && bluetoothAvailableForData() },
            stream = { stream(source, identifier) },
            onData = { data ->
                val receivedAt = SystemClock.elapsedRealtime()
                val receivedDate = System.currentTimeMillis()
                if (!session.checkTimeLimit(receivedAt) && session.accepts(generation)) {
                    onData(data, receivedAt, receivedDate)
                }
            }
        )
    }

    @MainThread
    private fun cleanupDataSubscriptions() = dataSubscriptions.stopAll()

    @SuppressLint("MissingPermission")
    private fun bluetoothAvailableForData(): Boolean {
        if (listOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT).any {
                appContext.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
            }) return false
        return try {
            appContext.getSystemService(BluetoothManager::class.java)?.adapter?.isEnabled == true
        } catch (_: SecurityException) {
            false
        }
    }

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
                setOf(
                    PolarBleSdkFeature.FEATURE_HR,
                    PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING,
                    PolarBleSdkFeature.FEATURE_BATTERY_INFO
                )
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
                                clearDataReadiness(DataReadinessStatus.WAITING)
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
                        session.connectionUnavailable("Connection ended: ${info.reason.name.replace('_', ' ')}.")
                        clearDataReadiness()
                        cancelConnectionTimeout()
                        connectionConfirmed = false
                        val reason = if (state.status == ConnectionStatus.DISCONNECTING) state.error else {
                            "Connection ended: ${info.reason.name.replace('_', ' ')}" +
                                (info.gattStatus?.let { " (GATT $it)" } ?: "") + "."
                        }
                        mutableConnectionState.value = ConnectionState(
                            error = reason,
                            message = if (state.status == ConnectionStatus.DISCONNECTING && reason == null) {
                                "Disconnected."
                            } else null
                        )
                    }
                }

                override fun batteryLevelReceived(identifier: String, level: Int) {
                    mainHandler.post {
                        deviceBattery.receive(created, api, identifier, mutableConnectionState.value, level)
                    }
                }

                override fun bleSdkFeatureReady(identifier: String, feature: PolarBleSdkFeature) {
                    mainHandler.post { readiness.acceptReadiness(created, identifier, listOf(feature), emptyList()) }
                }

                override fun bleSdkFeaturesReadiness(
                    identifier: String,
                    ready: List<PolarBleSdkFeature>,
                    unavailable: List<PolarBleSdkFeature>
                ) {
                    mainHandler.post { readiness.acceptReadiness(created, identifier, ready, unavailable) }
                }

                // Required by SDK 8.3.0; these features are not enabled in this step.
                override fun disInformationReceived(identifier: String, disInfo: DisInfo) = Unit
                override fun htsNotificationReceived(identifier: String, data: PolarHealthThermometerData) = Unit
            })
            true
        } catch (error: Exception) {
            releaseBluetooth()
            initializationError = "SDK initialization failed (${error.javaClass.simpleName}). Please retry."
            Log.e("PolarBleManager", "SDK initialization failed", error)
            false
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun startScan() {
        if (mutableConnectionState.value.status != ConnectionStatus.NOT_CONNECTED) return
        val currentApi = api ?: return
        scanner.start { currentApi.searchForDevice() }
    }

    fun stopScan(status: ScanStatus = ScanStatus.STOPPED) = scanner.stop(status)

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun connect(deviceId: String) {
        if (mutableConnectionState.value.status != ConnectionStatus.NOT_CONNECTED) return
        if (!sessionState.value.acceptsDevice(deviceId)) {
            mutableConnectionState.value = ConnectionState(
                error = "Reconnect the original H10 to continue, or Stop this session before changing devices.")
            return
        }
        val device = scanState.value.devices.firstOrNull { it.deviceId == deviceId }
            ?.let { ConnectionDevice(it.name, it.deviceId) }
            ?: savedDevicesState.value.devices.firstOrNull { it.deviceId == deviceId }
                ?.let { ConnectionDevice(it.name, it.deviceId) }
            ?: return
        stopScan()
        deviceBattery.clear()
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
                interruptConnection("Connection timed out after 10 seconds.")
            }
        }.also { mainHandler.postDelayed(it, 10_000L) }
        try {
            currentApi.connectToDevice(deviceId)
        } catch (error: Exception) {
            Log.e("PolarBleManager", "Connection request failed", error)
            interruptConnection("Connection failed (${error.javaClass.simpleName}).")
        }
    }

    private fun matches(source: PolarBleApi, device: PolarDeviceInfo): Boolean =
        api === source && mutableConnectionState.value.device?.deviceId == device.deviceId

    private fun readinessMatches(source: PolarBleApi, identifier: String): Boolean =
        api === source && mutableConnectionState.value.status == ConnectionStatus.CONNECTED &&
            mutableConnectionState.value.device?.deviceId == identifier

    private fun onlineStreamActive() = dataSubscriptions.isActive(PolarDeviceDataType.ACC) ||
        dataSubscriptions.isActive(PolarDeviceDataType.ECG)

    fun recheckDataReadiness() {
        val source = api ?: return
        val identifier = mutableConnectionState.value.device?.deviceId ?: return
        readiness.recheckDataReadiness(source, identifier)
    }

    private fun clearDataReadiness(status: DataReadinessStatus = DataReadinessStatus.DISCONNECTED) {
        coordinator.clearPauseContinuations()
        deviceBattery.clear()
        cleanupDataSubscriptions()
        readiness.clear(status)
    }

    fun bluetoothUnavailable() {
        stopScan(ScanStatus.INTERRUPTED)
        interruptConnection("Bluetooth is unavailable. Enable Bluetooth and permissions, then retry.")
    }

    fun pauseForNavigation() {
        session.pause()
        stopScan()
        if (mutableConnectionState.value.status == ConnectionStatus.CONNECTING) {
            interruptConnection(reason = null, message = "Connection attempt cancelled when leaving Session.")
        }
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
        session.connectionUnavailable(reason ?: message ?: "Connection ended.")
        clearDataReadiness()
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
                    message = message
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
        clearDataReadiness()
        cancelConnectionTimeout()
        scanner.release()
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

    fun releaseBluetooth() {
        session.connectionUnavailable("Bluetooth access unavailable.")
        val disposed = disposeSdk()
        mainHandler.removeCallbacksAndMessages(null)
        if (disposed) {
            initializationError = null
            connectionConfirmed = false
            // Local SDK ownership ended; a new connection must be confirmed by a fresh SDK callback.
            mutableConnectionState.value = ConnectionState(message = "Bluetooth access released. Reconnect when available.")
        }
    }
}
