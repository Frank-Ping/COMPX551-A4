package com.example.polarh10activityviewer

import com.example.polarh10activityviewer.ble.checkedDataTypes
import com.example.polarh10activityviewer.ble.ConnectionState
import com.example.polarh10activityviewer.ble.ConnectionStatus
import com.example.polarh10activityviewer.ble.DataReadiness
import com.example.polarh10activityviewer.ble.DataReadinessStatus
import com.example.polarh10activityviewer.ble.HeartRateReading
import com.example.polarh10activityviewer.ble.HeartRateStatistics
import com.example.polarh10activityviewer.ble.PolarBleManager
import com.example.polarh10activityviewer.ble.SavedDevicesState
import com.example.polarh10activityviewer.ble.ScanState
import com.example.polarh10activityviewer.ble.ScanStatus
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.chart.LiveChartPanel
import com.example.polarh10activityviewer.heartrate.HeartRateZonePanel
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.sensor.AccSample
import com.example.polarh10activityviewer.session.SensorViewModel
import com.example.polarh10activityviewer.session.SessionState
import com.example.polarh10activityviewer.session.SessionStatus
import com.example.polarh10activityviewer.session.SessionSummaryPanel
import com.example.polarh10activityviewer.history.HrHistoryState
import com.example.polarh10activityviewer.history.MotionHistoryState
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.history.HistoryPanel
import com.example.polarh10activityviewer.history.SavePanel
import com.example.polarh10activityviewer.storage.SaveStatus
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.TextButton

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.EcgSample
import com.polar.sdk.api.model.PolarSensorSetting.SettingType
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class SensorActivity : ComponentActivity() {
    private val permissions = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    )
    private val permissionHistory by lazy { getSharedPreferences("bluetooth_permissions", MODE_PRIVATE) }
    private lateinit var bleManager: PolarBleManager
    private var availability by mutableStateOf(BluetoothAvailability.PERMISSIONS_NEEDED)
    private var systemRequestPending by mutableStateOf(false)
    private var errorMessage by mutableStateOf<String?>(null)

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        // Record completed requests, not just a click or a cancelled empty result.
        permissionHistory.edit().apply {
            results.keys.forEach { putBoolean(it, true) }
        }.apply()
        systemRequestPending = false
        refreshAvailability()
    }

    private val bluetoothLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        systemRequestPending = false
        refreshAvailability()
    }

    private val settingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        systemRequestPending = false
        refreshAvailability()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        systemRequestPending = savedInstanceState?.getBoolean("systemRequestPending") ?: false
        bleManager = ViewModelProvider(this)[SensorViewModel::class.java].bleManager
        enableEdgeToEdge()
        setContent {
            val scanState by bleManager.scanState.collectAsState()
            val connectionState by bleManager.connectionState.collectAsState()
            val batteryLevel by bleManager.batteryLevel.collectAsState()
            val savedDevicesState by bleManager.savedDevicesState.collectAsState()
            val dataReadiness by bleManager.dataReadiness.collectAsState()
            val heartRate by bleManager.heartRate.collectAsState()
            val heartRateStatistics by bleManager.heartRateStatistics.collectAsState()
            val heartRateMessage by bleManager.heartRateMessage.collectAsState()
            val heartRateZones by bleManager.heartRateZoneState.collectAsState()
            val accSamples by bleManager.accSamples.collectAsState()
            val steps by bleManager.stepState.collectAsState()
            val ecgSamples by bleManager.ecgSamples.collectAsState()
            val subscriptionStates by bleManager.subscriptionStates.collectAsState()
            val session by bleManager.sessionState.collectAsState()
            val hrHistory by bleManager.hrHistoryState.collectAsState()
            val motionHistory by bleManager.motionHistoryState.collectAsState()
            val lastSnapshot by bleManager.lastSnapshot.collectAsState()
            val saveState by bleManager.storage.saves.state.collectAsState()
            var showHistory by rememberSaveable { mutableStateOf(false) }
            LaunchedEffect(session.generation, session.status) {
                val generation = session.generation
                if (session.status == SessionStatus.RUNNING) {
                    while (true) {
                        bleManager.refreshSessionTime(generation)
                        delay(250)
                    }
                }
            }
            PolarH10ActivityViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.fillMaxSize().padding(innerPadding)) {
                        Row {
                            TextButton(onClick = { showHistory = false }) { Text("Session") }
                            TextButton(onClick = { showHistory = true }) { Text("History") }
                        }
                        SavePanel(saveState, bleManager.storage.saves)
                        if (showHistory) HistoryPanel(bleManager.storage.database,
                            saveState.sessionId.takeIf { saveState.status == SaveStatus.SAVED },
                            onBack = { showHistory = false })
                        else SessionScreen(
                        availability = availability,
                        actionEnabled = !systemRequestPending,
                        errorMessage = errorMessage,
                        onBluetoothAction = ::handleBluetoothAction,
                        scanState = scanState,
                        onStartScan = ::handleStartScan,
                        onStopScan = { bleManager.stopScan() },
                        connectionState = connectionState,
                        batteryLevel = batteryLevel,
                        onConnect = ::handleConnect,
                        savedDevicesState = savedDevicesState,
                        onDisconnect = ::handleDisconnect,
                        onRetryDisconnect = ::handleRetryDisconnect,
                        dataReadiness = dataReadiness,
                        onRecheckData = ::handleRecheckData,
                        heartRate = heartRate,
                        heartRateStatistics = heartRateStatistics,
                        heartRateMessage = heartRateMessage,
                        heartRateZones = heartRateZones,
                        hrSubscription = subscriptionStates.getValue(PolarDeviceDataType.HR),
                        accSamples = accSamples,
                        steps = steps,
                        accSubscription = subscriptionStates.getValue(PolarDeviceDataType.ACC),
                        ecgSamples = ecgSamples,
                        ecgSubscription = subscriptionStates.getValue(PolarDeviceDataType.ECG),
                        session = session,
                        hrHistory = hrHistory,
                        motionHistory = motionHistory,
                        lastSnapshot = lastSnapshot,
                        savingBlocksStart = saveState.blocksStart,
                        onStartSession = ::handleStartSession,
                        onStopSession = { bleManager.stopSession() },
                        onRetryStream = ::handleRetryStream,
                        charts = { LiveChartPanel(bleManager) },
                        modifier = Modifier.weight(1f)
                    )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bleManager.onBluetoothStateChanged = { refreshAvailability() }
    }

    override fun onResume() {
        super.onResume()
        refreshAvailability()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("systemRequestPending", systemRequestPending)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        bleManager.onBluetoothStateChanged = null
        if (!isChangingConfigurations) bleManager.leaveSession()
        super.onStop()
    }

    private fun missingPermissions() = permissions.filter {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun refreshAvailability(retryInitialization: Boolean = false) {
        if (systemRequestPending) return
        errorMessage = null
        val adapter = getSystemService(BluetoothManager::class.java)?.adapter
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) || adapter == null) {
            bleManager.bluetoothUnavailable()
            availability = BluetoothAvailability.UNSUPPORTED
            return
        }

        val missing = missingPermissions()
        if (missing.isNotEmpty()) {
            bleManager.release()
            availability = when {
                missing.any { permissionHistory.getBoolean(it, false) && !shouldShowRequestPermissionRationale(it) } ->
                    BluetoothAvailability.SETTINGS_REQUIRED
                missing.any { permissionHistory.getBoolean(it, false) || shouldShowRequestPermissionRationale(it) } ->
                    BluetoothAvailability.PERMISSION_DENIED
                else -> BluetoothAvailability.PERMISSIONS_NEEDED
            }
            return
        }

        // Both runtime permissions have been checked before accessing Bluetooth or the SDK.
        if (!bleManager.initialize(retryInitialization)) {
            availability = BluetoothAvailability.SDK_ERROR
            errorMessage = bleManager.initializationError
            return
        }
        try {
            availability = if (adapter.isEnabled) BluetoothAvailability.READY else BluetoothAvailability.BLUETOOTH_OFF
            if (availability != BluetoothAvailability.READY) bleManager.bluetoothUnavailable()
        } catch (_: SecurityException) {
            bleManager.release()
            availability = BluetoothAvailability.PERMISSIONS_NEEDED
        }
    }

    @SuppressLint("MissingPermission")
    private fun handleStartScan() {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.startScan()
    }

    @SuppressLint("MissingPermission")
    private fun handleConnect(deviceId: String) {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.connect(deviceId)
    }

    private fun handleDisconnect() {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.disconnect()
    }

    private fun handleRetryDisconnect() {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.retryDisconnect()
    }

    private fun handleRecheckData() {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.recheckDataReadiness()
    }

    private fun handleStartSession() {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.startSession()
    }

    private fun handleRetryStream(type: PolarDeviceDataType) {
        if (systemRequestPending) return
        refreshAvailability()
        if (availability == BluetoothAvailability.READY) bleManager.retryStream(type)
    }

    @SuppressLint("MissingPermission")
    private fun handleBluetoothAction() {
        if (systemRequestPending) return
        refreshAvailability(retryInitialization = availability == BluetoothAvailability.SDK_ERROR)
        try {
            when (availability) {
                BluetoothAvailability.PERMISSIONS_NEEDED, BluetoothAvailability.PERMISSION_DENIED -> {
                    systemRequestPending = true
                    permissionLauncher.launch(missingPermissions().toTypedArray())
                }
                BluetoothAvailability.SETTINGS_REQUIRED -> {
                    systemRequestPending = true
                    settingsLauncher.launch(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")))
                }
                BluetoothAvailability.BLUETOOTH_OFF -> {
                    // refreshAvailability has checked both permissions immediately before this action.
                    systemRequestPending = true
                    bluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                }
                else -> Unit
            }
        } catch (error: Exception) {
            systemRequestPending = false
            refreshAvailability()
            errorMessage = "Unable to open the system request (${error.javaClass.simpleName}). Please retry."
        }
    }
}

enum class BluetoothAvailability(val message: String, val buttonLabel: String) {
    PERMISSIONS_NEEDED("Bluetooth permissions are required.", "Enable Bluetooth"),
    PERMISSION_DENIED("Bluetooth permissions were denied.", "Grant permissions"),
    SETTINGS_REQUIRED("Allow Nearby devices access in app settings.", "Open app settings"),
    BLUETOOTH_OFF("Bluetooth is off.", "Turn on Bluetooth"),
    UNSUPPORTED("This phone does not support Bluetooth Low Energy (BLE).", "Bluetooth unavailable"),
    SDK_ERROR("SDK initialization failed.", "Retry"),
    READY("Bluetooth ready", "Bluetooth ready")
}

@Composable
internal fun SessionScreen(
    availability: BluetoothAvailability,
    actionEnabled: Boolean,
    errorMessage: String?,
    onBluetoothAction: () -> Unit,
    scanState: ScanState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    connectionState: ConnectionState,
    onConnect: (String) -> Unit,
    savedDevicesState: SavedDevicesState,
    onDisconnect: () -> Unit,
    onRetryDisconnect: () -> Unit,
    dataReadiness: Map<PolarDeviceDataType, DataReadiness>,
    onRecheckData: () -> Unit,
    modifier: Modifier = Modifier,
    batteryLevel: Int? = null,
    heartRate: HeartRateReading? = null,
    heartRateStatistics: HeartRateStatistics = HeartRateStatistics(),
    heartRateMessage: String? = null,
    heartRateZones: HeartRateZoneState = HeartRateZoneState(),
    hrSubscription: SubscriptionState = SubscriptionState(),
    accSamples: List<AccSample> = emptyList(),
    steps: StepState = StepState(),
    accSubscription: SubscriptionState = SubscriptionState(),
    ecgSamples: List<EcgSample> = emptyList(),
    ecgSubscription: SubscriptionState = SubscriptionState(),
    session: SessionState = SessionState(),
    hrHistory: HrHistoryState = HrHistoryState(),
    motionHistory: MotionHistoryState = MotionHistoryState(),
    lastSnapshot: SessionSnapshot? = null,
    savingBlocksStart: Boolean = false,
    onStartSession: () -> Unit = {},
    onStopSession: () -> Unit = {},
    onRetryStream: (PolarDeviceDataType) -> Unit = {},
    charts: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Session", style = MaterialTheme.typography.headlineLarge)
        Text(availability.message, style = MaterialTheme.typography.bodyLarge)
        Text("Device: ${connectionState.status.message}", style = MaterialTheme.typography.titleMedium)
        connectionState.device?.let { Text("${it.name} (${it.deviceId})") }
        Text("Battery: ${batteryLevel?.let { "$it%" } ?: "--"}")
        val connected = actionEnabled && availability == BluetoothAvailability.READY &&
            connectionState.status == ConnectionStatus.CONNECTED
        val subscriptions = listOf(hrSubscription, accSubscription, ecgSubscription)
        fun busy(subscription: SubscriptionState) = subscription.status in setOf(
            SubscriptionStatus.STARTING, SubscriptionStatus.RECEIVING, SubscriptionStatus.STOPPING
        )
        Text("Session: ${session.status.label}", style = MaterialTheme.typography.titleMedium)
        val seconds = session.elapsedMs / 1_000
        Text("Elapsed: ${seconds / 60}:${(seconds % 60).toString().padStart(2, '0')}")
        Text("Timing begins with the first valid HR or actual ACC/ECG sample.")
        Text("Session limit: four hours. The session ends and eligible data is saved automatically.")
        session.endReason?.let { Text(if (it == "TIME_LIMIT") "Session time limit reached." else it) }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onStartSession, enabled = connected && !savingBlocksStart &&
                session.status in setOf(SessionStatus.IDLE, SessionStatus.STOPPED) &&
                subscriptions.none(::busy) && dataReadiness.values.any {
                    it.status == DataReadinessStatus.READY && it.configurationComplete
                }) { Text("Start") }
            Button(onClick = onStopSession, enabled = session.ongoing) { Text("Stop") }
        }
        HeartRatePanel(
            reading = heartRate,
            statistics = heartRateStatistics,
            message = heartRateMessage,
            subscription = hrSubscription,
            canRetry = connected && session.ongoing,
            onRetry = { onRetryStream(PolarDeviceDataType.HR) }
        )
        HeartRateZonePanel(heartRateZones,
            stopped = session.status == SessionStatus.STOPPING || session.status == SessionStatus.STOPPED)
        Text("Steps (development check)", style = MaterialTheme.typography.titleMedium)
        Text(steps.message)
        Text("Total steps: ${steps.totalSteps ?: "--"}")
        Text("Cadence: ${steps.cadence?.roundToInt() ?: "--"} steps/min")
        Text("Estimated distance: ${steps.distance?.let { String.format(Locale.ENGLISH, "%.1f m", it) } ?: "--"}")
        Text("Estimated speed: ${formatSpeed(steps.speed)}")
        Text("Average speed: ${formatSpeed(steps.averageSpeed)}")
        Text("Maximum speed: ${formatSpeed(steps.maximumSpeed)}")
        Text("Maximum cadence: ${steps.maximumCadence?.roundToInt() ?: "--"} steps/min")
        if (steps.incompleteAcc) {
            Text("Incomplete ACC data. Missing distance may lower distance and average speed.")
        }
        SessionSummaryPanel(session.record)
        Text("HR history (development check)", style = MaterialTheme.typography.titleMedium)
        Text("HR history points: ${hrHistory.pointCount}")
        Text("First elapsedMs: ${hrHistory.firstElapsedMs ?: "--"}")
        Text("Last elapsedMs: ${hrHistory.lastElapsedMs ?: "--"}")
        Text("HR history: " + when {
            hrHistory.sessionId == null -> "Not started"
            hrHistory.frozen -> "Frozen"
            hrHistory.limitReached -> "Four-hour collection limit reached"
            session.status == SessionStatus.STARTING -> "Waiting for Running"
            else -> "Collecting"
        })
        Text("First four hours; at most 14,401 HR points. Saved with the session summary at ending.")
        Text("Motion history (development check)", style = MaterialTheme.typography.titleMedium)
        Text("Motion history points: ${motionHistory.pointCount}")
        Text("First elapsedMs: ${motionHistory.firstElapsedMs ?: "--"}")
        Text("Last elapsedMs: ${motionHistory.lastElapsedMs ?: "--"}")
        Text("Motion history: " + when {
            motionHistory.sessionId == null -> "Not started"
            motionHistory.frozen -> "Frozen"
            motionHistory.limitReached -> "Four-hour collection limit reached"
            session.status == SessionStatus.STARTING -> "Waiting for Running"
            else -> "Collecting"
        })
        Text("Current session snapshot: " + if (lastSnapshot != null &&
            lastSnapshot.record.id == session.record?.id) "Frozen in memory" else "Not frozen")
        Text("Last frozen snapshot ID: ${lastSnapshot?.record?.id ?: "--"}")
        Text("First four hours; at most 14,401 motion points. Check Save status for database confirmation.")
        charts()
        AccPanel(
            samples = accSamples,
            subscription = accSubscription,
            readiness = dataReadiness[PolarDeviceDataType.ACC] ?: DataReadiness(),
            canRetry = connected && session.ongoing &&
                dataReadiness.values.none { it.status == DataReadinessStatus.CHECKING },
            onRetry = { onRetryStream(PolarDeviceDataType.ACC) }
        )
        EcgPanel(
            samples = ecgSamples,
            subscription = ecgSubscription,
            readiness = dataReadiness[PolarDeviceDataType.ECG] ?: DataReadiness(),
            canRetry = connected && session.ongoing &&
                dataReadiness.values.none { it.status == DataReadinessStatus.CHECKING },
            onRetry = { onRetryStream(PolarDeviceDataType.ECG) }
        )
        DataReadinessPanel(
            states = dataReadiness,
            canRecheck = actionEnabled && availability == BluetoothAvailability.READY &&
                connectionState.status == ConnectionStatus.CONNECTED && !busy(accSubscription) && !busy(ecgSubscription),
            onRecheck = onRecheckData
        )
        connectionState.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        connectionState.disconnectError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        connectionState.message?.let { Text(it) }
        Button(
            onClick = onDisconnect,
            enabled = actionEnabled && availability == BluetoothAvailability.READY &&
                connectionState.status == ConnectionStatus.CONNECTED
        ) {
            Text("Disconnect")
        }
        if (connectionState.status == ConnectionStatus.DISCONNECTING && connectionState.disconnectError != null) {
            Button(
                onClick = onRetryDisconnect,
                enabled = actionEnabled && availability == BluetoothAvailability.READY
            ) {
                Text("Retry disconnect")
            }
        }
        if (availability in setOf(
                BluetoothAvailability.PERMISSIONS_NEEDED,
                BluetoothAvailability.PERMISSION_DENIED,
                BluetoothAvailability.SETTINGS_REQUIRED
            )) {
            Text("Nearby devices access is needed to find and connect to your Polar H10. Location access is not requested.")
        }
        errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = onBluetoothAction,
            enabled = actionEnabled && availability != BluetoothAvailability.UNSUPPORTED && availability != BluetoothAvailability.READY
        ) {
            Text(availability.buttonLabel)
        }
        val canConnect = actionEnabled && availability == BluetoothAvailability.READY &&
            connectionState.status == ConnectionStatus.NOT_CONNECTED
        Text("Saved devices", style = MaterialTheme.typography.titleMedium)
        Text("Previously connected by this app. A saved record does not mean the device is nearby, online or paired in system settings.")
        savedDevicesState.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (savedDevicesState.loading) {
            Text("Loading saved devices...")
        } else if (savedDevicesState.devices.isEmpty() && savedDevicesState.error == null) {
            Text("No saved devices")
        }
        savedDevicesState.devices.forEach { device ->
            OutlinedCard(onClick = { onConnect(device.deviceId) }, enabled = canConnect) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(device.name, style = MaterialTheme.typography.titleSmall)
                    Text("Device ID: ${device.deviceId}")
                    val lastConnected = DateFormat.getDateTimeInstance(
                        DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.ENGLISH
                    ).format(Date(device.lastConnectedAt))
                    Text("Last connected: $lastConnected")
                    Text("Tap to connect")
                }
            }
        }
        Text("Nearby Polar H10 devices", style = MaterialTheme.typography.titleMedium)
        val scanning = scanState.status == ScanStatus.SCANNING
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onStartScan,
                enabled = canConnect && !scanning
            ) {
                Text("Start scan")
            }
            Button(onClick = onStopScan, enabled = scanning) {
                Text("Stop scan")
            }
        }
        Text(scanState.status.message)
        scanState.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (scanState.devices.isEmpty() &&
            scanState.status in setOf(ScanStatus.STOPPED, ScanStatus.TIMED_OUT)) {
            Text("No Polar H10 found")
        }
        scanState.devices.forEach { device ->
            OutlinedCard(onClick = { onConnect(device.deviceId) }, enabled = canConnect) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(device.name, style = MaterialTheme.typography.titleSmall)
                    Text("Device ID: ${device.deviceId}")
                    Text("Signal strength: ${device.rssi} dBm")
                    Text("Tap to connect")
                }
            }
        }
    }
}

private fun formatSpeed(value: Double?): String =
    value?.let { String.format(Locale.ENGLISH, "%.1f km/h", it * 3.6) } ?: "--"

// Detailed stream information remains temporary until the final layout.
@Composable
private fun HeartRatePanel(
    reading: HeartRateReading?,
    statistics: HeartRateStatistics,
    message: String?,
    subscription: SubscriptionState,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Heart rate (development check)", style = MaterialTheme.typography.titleMedium)
        val status = when (subscription.status) {
            SubscriptionStatus.IDLE -> "Idle"
            SubscriptionStatus.STARTING -> "Starting"
            SubscriptionStatus.RECEIVING -> "Receiving"
            SubscriptionStatus.STOPPING -> "Stopping"
            SubscriptionStatus.STOPPED -> "Stopped"
            SubscriptionStatus.FAILED -> "Failed"
        }
        Text("HR stream: $status")
        Text("HR: ${reading?.let { "${it.bpm} bpm" } ?: "--"}")
        Text("Minimum HR: ${statistics.min?.let { "$it bpm" } ?: "--"}")
        Text("Maximum HR: ${statistics.max?.let { "$it bpm" } ?: "--"}")
        val average = statistics.average?.let { String.format(Locale.ENGLISH, "%.1f bpm", it) } ?: "--"
        Text("Mean HR: $average")
        Text("Mean of valid HR samples")
        val receivedAt = reading?.let {
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.ENGLISH)
                .format(Date(it.receivedAt))
        } ?: "--"
        Text("Last received on phone: $receivedAt")
        Text("Shows the latest sample when valid; the time is not a sensor sampling timestamp.")
        message?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.HR, subscription, canRetry, onRetry)
    }
}

// Temporary ACC verification UI; retained samples are not a live reading after stopping.
@Composable
private fun AccPanel(
    samples: List<AccSample>,
    subscription: SubscriptionState,
    readiness: DataReadiness,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Acceleration (development check)", style = MaterialTheme.typography.titleMedium)
        Text("ACC stream: ${subscription.status.name.lowercase().replaceFirstChar { it.uppercase() }}")
        val receiving = subscription.status == SubscriptionStatus.RECEIVING
        Text(if (receiving) "Latest received samples" else "Inactive snapshot")
        Text("Selected rate: ${readiness.selected[SettingType.SAMPLE_RATE] ?: "--"} Hz")
        Text("Selected range: +/-${readiness.selected[SettingType.RANGE] ?: "--"} g")
        Text("Selected resolution: ${readiness.selected[SettingType.RESOLUTION] ?: "--"} bits")
        val latest = samples.lastOrNull()
        Text("X: ${latest?.x ?: "--"} mG; Y: ${latest?.y ?: "--"} mG; Z: ${latest?.z ?: "--"} mG")
        Text("Sensor timestamp: ${latest?.timeStamp ?: "--"} ns (epoch 2000-01-01)")
        Text("Buffer: ${samples.size} samples (up to 10 s / 1,000 samples)")
        val lastGap = samples.lastOrNull { it.gapBeforeNs != null }
        Text(if (lastGap == null) "Gap > 30 ms in buffer: none"
            else "Last gap in buffer: ${lastGap.gapBeforeNs!! / 1_000_000.0} ms at ${lastGap.timeStamp} ns")
        readiness.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (readiness.status == DataReadinessStatus.READY && !readiness.configurationComplete && readiness.error == null) {
            Text("Confirm the available options in Data readiness before starting ACC.")
        }
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.ACC, subscription, canRetry, onRetry)
    }
}

// Temporary ECG verification display within the session.
@Composable
private fun EcgPanel(
    samples: List<EcgSample>,
    subscription: SubscriptionState,
    readiness: DataReadiness,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("ECG (development check)", style = MaterialTheme.typography.titleMedium)
        Text("ECG stream: ${subscription.status.name.lowercase().replaceFirstChar { it.uppercase() }}")
        val receiving = subscription.status == SubscriptionStatus.RECEIVING
        Text(if (receiving) "Latest received samples" else "Inactive snapshot")
        Text("Selected rate: ${readiness.selected[SettingType.SAMPLE_RATE] ?: "--"} Hz")
        Text("Selected resolution: ${readiness.selected[SettingType.RESOLUTION] ?: "--"} bits")
        Text("Other available settings and selections are shown in Data readiness.")
        val latest = samples.lastOrNull()
        Text("Voltage: ${latest?.voltage ?: "--"} µV")
        Text("Sensor timestamp: ${latest?.timeStamp ?: "--"} ns (epoch 2000-01-01)")
        Text("Buffer: ${samples.size} samples (up to 10 s / 1,300 samples)")
        readiness.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (readiness.status == DataReadinessStatus.READY && !readiness.configurationComplete && readiness.error == null) {
            Text("Confirm the available options in Data readiness before starting ECG.")
        }
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.ECG, subscription, canRetry, onRetry)
    }
}

@Composable
private fun StreamRetryButton(
    type: PolarDeviceDataType,
    subscription: SubscriptionState,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    if (subscription.status in setOf(SubscriptionStatus.IDLE, SubscriptionStatus.FAILED, SubscriptionStatus.STOPPED)) {
        Button(onClick = onRetry, enabled = canRetry) { Text("Retry $type") }
    }
}

// Temporary verification UI: remove during stage 8, keeping the underlying readiness checks.
@Composable
private fun DataReadinessPanel(
    states: Map<PolarDeviceDataType, DataReadiness>,
    canRecheck: Boolean,
    onRecheck: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Data readiness", style = MaterialTheme.typography.titleMedium)
        Text("Feature readiness and configuration are separate from data reception.")
        checkedDataTypes.forEach { type ->
            val state = states[type] ?: DataReadiness()
            Text("${type.name}: ${state.status.message}")
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            if (state.status == DataReadinessStatus.READY) {
                Text(if (type == PolarDeviceDataType.HR) "Configuration: no sampling settings required."
                    else if (state.configurationComplete) "Configuration: complete."
                    else if (state.error != null) "Configuration: blocked."
                    else "Configuration: awaiting your confirmation of multiple options.")
            }
            state.available.forEach { (setting, values) ->
                val label = when (setting) {
                    SettingType.SAMPLE_RATE -> "Sample rate (Hz)"
                    SettingType.RESOLUTION -> "Resolution (bits)"
                    SettingType.RANGE -> if (type == PolarDeviceDataType.ACC) "Range (g)" else "Range (SDK units not specified)"
                    SettingType.CHANNELS -> "Channels (count)"
                }
                Text("$label: available ${values.sorted().joinToString()}; selected ${state.selected[setting] ?: "--"}")
            }
        }
        Button(onClick = onRecheck, enabled = canRecheck && states.values.none { it.status == DataReadinessStatus.CHECKING }) {
            Text("Recheck data readiness")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionPreview() {
    PolarH10ActivityViewerTheme {
        SessionScreen(
            availability = BluetoothAvailability.PERMISSIONS_NEEDED,
            actionEnabled = true,
            errorMessage = null,
            onBluetoothAction = {},
            scanState = ScanState(),
            onStartScan = {},
            onStopScan = {},
            connectionState = ConnectionState(),
            onConnect = {},
            savedDevicesState = SavedDevicesState(loading = false),
            onDisconnect = {},
            onRetryDisconnect = {},
            dataReadiness = checkedDataTypes.associateWith { DataReadiness() },
            onRecheckData = {}
        )
    }
}
