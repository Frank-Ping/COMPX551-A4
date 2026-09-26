package com.example.polarh10activityviewer

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
import java.text.DateFormat
import java.util.Date
import java.util.Locale

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
            val savedDevicesState by bleManager.savedDevicesState.collectAsState()
            PolarH10ActivityViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SessionScreen(
                        availability = availability,
                        actionEnabled = !systemRequestPending,
                        errorMessage = errorMessage,
                        onBluetoothAction = ::handleBluetoothAction,
                        scanState = scanState,
                        onStartScan = ::handleStartScan,
                        onStopScan = { bleManager.stopScan() },
                        connectionState = connectionState,
                        onConnect = ::handleConnect,
                        savedDevicesState = savedDevicesState,
                        onDisconnect = ::handleDisconnect,
                        modifier = Modifier.padding(innerPadding)
                    )
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
fun SessionScreen(
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Session", style = MaterialTheme.typography.headlineLarge)
        Text(availability.message, style = MaterialTheme.typography.bodyLarge)
        Text("Device: ${connectionState.status.message}", style = MaterialTheme.typography.titleMedium)
        connectionState.device?.let { Text("${it.name} (${it.deviceId})") }
        if (connectionState.status == ConnectionStatus.CONNECTED) {
            Text("Data feature readiness has not been checked.")
        }
        connectionState.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        connectionState.message?.let { Text(it) }
        Button(
            onClick = onDisconnect,
            enabled = actionEnabled && availability == BluetoothAvailability.READY &&
                connectionState.status == ConnectionStatus.CONNECTED
        ) {
            Text("Disconnect")
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
            onDisconnect = {}
        )
    }
}
