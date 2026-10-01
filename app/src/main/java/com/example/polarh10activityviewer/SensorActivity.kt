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
import com.example.polarh10activityviewer.session.SensorViewModel
import com.example.polarh10activityviewer.session.SessionState
import com.example.polarh10activityviewer.session.SessionStatus
import com.example.polarh10activityviewer.session.HeartRateCard
import com.example.polarh10activityviewer.session.MotionCard
import com.example.polarh10activityviewer.session.ActivitySummaryCard
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.example.polarh10activityviewer.ui.theme.PagePadding
import com.example.polarh10activityviewer.ble.DevicesDialog
import com.example.polarh10activityviewer.session.SessionHeader
import com.example.polarh10activityviewer.ui.theme.SectionSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import kotlinx.coroutines.delay

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
            val steps by bleManager.stepState.collectAsState()
            val subscriptionStates by bleManager.subscriptionStates.collectAsState()
            val session by bleManager.sessionState.collectAsState()
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
                        SavePanel(saveState, bleManager.storage.saves, showSessionId = showHistory)
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
                        steps = steps,
                        accSubscription = subscriptionStates.getValue(PolarDeviceDataType.ACC),
                        ecgSubscription = subscriptionStates.getValue(PolarDeviceDataType.ECG),
                        session = session,
                        savingBlocksStart = saveState.blocksStart,
                        onStartSession = ::handleStartSession,
                        onStopSession = { bleManager.stopSession() },
                        onRetryStream = ::handleRetryStream,
                        charts = { canRetry, retry -> LiveChartPanel(bleManager, subscriptionStates, dataReadiness, canRetry, retry) },
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
    steps: StepState = StepState(),
    accSubscription: SubscriptionState = SubscriptionState(),
    ecgSubscription: SubscriptionState = SubscriptionState(),
    session: SessionState = SessionState(),
    savingBlocksStart: Boolean = false,
    onStartSession: () -> Unit = {},
    onStopSession: () -> Unit = {},
    onRetryStream: (PolarDeviceDataType) -> Unit = {},
    charts: @Composable (Boolean, () -> Unit) -> Unit = { _, _ -> }
) {
    var showDevices by rememberSaveable { mutableStateOf(false) }
    val validBattery = batteryLevel.takeIf {
        availability == BluetoothAvailability.READY && connectionState.status == ConnectionStatus.CONNECTED
    }
    fun closeDevices() {
        if (scanState.status == ScanStatus.SCANNING) onStopScan()
        showDevices = false
    }
    val accBusy = accSubscription.status in setOf(SubscriptionStatus.STARTING, SubscriptionStatus.RECEIVING, SubscriptionStatus.STOPPING)
    val ecgBusy = ecgSubscription.status in setOf(SubscriptionStatus.STARTING, SubscriptionStatus.RECEIVING, SubscriptionStatus.STOPPING)
    if (showDevices) {
        DevicesDialog(
            availability = availability, actionEnabled = actionEnabled,
            connection = connectionState, batteryLevel = validBattery,
            savedDevices = savedDevicesState, scan = scanState, readiness = dataReadiness,
            canRecheck = actionEnabled && availability == BluetoothAvailability.READY &&
                connectionState.status == ConnectionStatus.CONNECTED && !accBusy && !ecgBusy,
            errorMessage = errorMessage, onBluetoothAction = onBluetoothAction,
            onConnect = onConnect, onDisconnect = onDisconnect, onRetryDisconnect = onRetryDisconnect,
            onStartScan = onStartScan, onStopScan = onStopScan, onRecheck = onRecheckData,
            onClose = ::closeDevices
        )
    }
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(SectionSpacing)
    ) {
        Text("Session", style = MaterialTheme.typography.titleLarge)
        SessionHeader(
            availability = availability, connection = connectionState, batteryLevel = validBattery,
            zones = heartRateZones,
            stopped = session.status == SessionStatus.STOPPING || session.status == SessionStatus.STOPPED,
            hrSubscription = hrSubscription, hrMessage = heartRateMessage,
            onOpenDevices = { showDevices = true }
        )
        val connected = actionEnabled && availability == BluetoothAvailability.READY &&
            connectionState.status == ConnectionStatus.CONNECTED
        val subscriptions = listOf(hrSubscription, accSubscription, ecgSubscription)
        fun busy(subscription: SubscriptionState) = subscription.status in setOf(
            SubscriptionStatus.STARTING, SubscriptionStatus.RECEIVING, SubscriptionStatus.STOPPING
        )
        Text("Session: ${session.status.label}", style = MaterialTheme.typography.titleMedium)
        Text("Timing begins with the first valid HR or actual ACC/ECG sample.")
        Text("Session limit: four hours. The session ends and eligible data is saved automatically.")
        session.endReason?.let { Text(if (it == "TIME_LIMIT") "Session time limit reached." else it) }
        Row(horizontalArrangement = Arrangement.spacedBy(ControlSpacing)) {
            Button(onClick = onStartSession, enabled = connected && !savingBlocksStart &&
                session.status in setOf(SessionStatus.IDLE, SessionStatus.STOPPED) &&
                subscriptions.none(::busy) && dataReadiness.values.any {
                    it.status == DataReadinessStatus.READY && it.configurationComplete
                }) { Text("Start") }
            Button(onClick = onStopSession, enabled = session.ongoing) { Text("Stop") }
        }
        HeartRateCard(
            reading = heartRate,
            statistics = heartRateStatistics,
            message = heartRateMessage,
            subscription = hrSubscription,
            canRetry = connected && session.ongoing,
            onRetry = { onRetryStream(PolarDeviceDataType.HR) }
        )
        MotionCard(
            steps = steps, subscription = accSubscription,
            canRetry = connected && session.ongoing &&
                dataReadiness.values.none { it.status == DataReadinessStatus.CHECKING },
            onRetry = { onRetryStream(PolarDeviceDataType.ACC) }
        )
        HeartRateZonePanel(heartRateZones,
            stopped = session.status == SessionStatus.STOPPING || session.status == SessionStatus.STOPPED)
        ActivitySummaryCard(session, steps)
        session.record?.let { record ->
            if (record.incomplete) Text("Incomplete session data. Some streams have missing or failed observations.")
            if (session.status == SessionStatus.STOPPED && !record.eligibleForSaving) {
                Text("No valid observations to save. Connect a device and Start a new session.")
            }
        }
        charts(connected && session.ongoing &&
            dataReadiness.values.none { it.status == DataReadinessStatus.CHECKING },
            { onRetryStream(PolarDeviceDataType.ECG) })
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
