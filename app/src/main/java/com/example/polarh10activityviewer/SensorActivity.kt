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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme

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
        bleManager = PolarBleManager(applicationContext) {
            if (!isDestroyed) refreshAvailability()
        }
        enableEdgeToEdge()
        setContent {
            PolarH10ActivityViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SessionScreen(
                        availability = availability,
                        actionEnabled = !systemRequestPending,
                        errorMessage = errorMessage,
                        onBluetoothAction = ::handleBluetoothAction,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshAvailability()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("systemRequestPending", systemRequestPending)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        bleManager.release()
        super.onDestroy()
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
        } catch (_: SecurityException) {
            bleManager.release()
            availability = BluetoothAvailability.PERMISSIONS_NEEDED
        }
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
    READY("Bluetooth ready — no device connected", "Bluetooth ready")
}

@Composable
fun SessionScreen(
    availability: BluetoothAvailability,
    actionEnabled: Boolean,
    errorMessage: String?,
    onBluetoothAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Session", style = MaterialTheme.typography.headlineLarge)
        Text(availability.message, style = MaterialTheme.typography.bodyLarge)
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
            onBluetoothAction = {}
        )
    }
}
