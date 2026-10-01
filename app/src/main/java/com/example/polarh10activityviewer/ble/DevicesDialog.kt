package com.example.polarh10activityviewer.ble

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.ui.theme.CardCornerRadius
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.PagePadding
import com.example.polarh10activityviewer.ui.theme.SectionSpacing
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting.SettingType
import java.text.DateFormat
import java.util.Date
import java.util.Locale

internal fun connectionStatusText(availability: BluetoothAvailability, connection: ConnectionState): String =
    when (availability) {
        BluetoothAvailability.READY -> connection.status.message
        BluetoothAvailability.PERMISSIONS_NEEDED, BluetoothAvailability.PERMISSION_DENIED,
        BluetoothAvailability.SETTINGS_REQUIRED -> "Permissions needed"
        BluetoothAvailability.BLUETOOTH_OFF -> "Bluetooth off"
        BluetoothAvailability.UNSUPPORTED -> "BLE unavailable"
        BluetoothAvailability.SDK_ERROR -> "SDK error"
    }

@Composable
internal fun DevicesDialog(
    availability: BluetoothAvailability,
    actionEnabled: Boolean,
    connection: ConnectionState,
    batteryLevel: Int?,
    savedDevices: SavedDevicesState,
    scan: ScanState,
    readiness: Map<PolarDeviceDataType, DataReadiness>,
    canRecheck: Boolean,
    errorMessage: String?,
    onBluetoothAction: () -> Unit,
    onConnect: (String) -> Unit,
    onDisconnect: () -> Unit,
    onRetryDisconnect: () -> Unit,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onRecheck: () -> Unit,
    onClose: () -> Unit
) {
    val connected = actionEnabled && availability == BluetoothAvailability.READY &&
        connection.status == ConnectionStatus.CONNECTED
    val canConnect = actionEnabled && availability == BluetoothAvailability.READY &&
        connection.status == ConnectionStatus.NOT_CONNECTED
    val scanning = scan.status == ScanStatus.SCANNING
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Devices") },
        confirmButton = { TextButton(onClick = onClose) { Text("Close") } },
        text = {
            Column(
                Modifier.fillMaxWidth().heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.55f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(SectionSpacing)
            ) {
                Text(connectionStatusText(availability, connection), style = MaterialTheme.typography.titleMedium)
                Text("Current device", style = MaterialTheme.typography.titleMedium)
                val device = connection.device
                if (device == null) Text("No device connected")
                else {
                    Text(device.name)
                    Text("Device ID: ${device.deviceId}")
                }
                Text("Battery: ${batteryLevel?.let { "$it%" } ?: "--"}")
                connection.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                connection.disconnectError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                connection.message?.let { Text(it) }
                if (connection.status == ConnectionStatus.CONNECTED) {
                    Button(onClick = onDisconnect, enabled = connected) { Text("Disconnect") }
                }
                if (connection.status == ConnectionStatus.DISCONNECTING && connection.disconnectError != null) {
                    Button(onClick = onRetryDisconnect,
                        enabled = actionEnabled && availability == BluetoothAvailability.READY) { Text("Retry disconnect") }
                }
                if (availability != BluetoothAvailability.READY) {
                    Text(availability.message)
                    if (availability in setOf(BluetoothAvailability.PERMISSIONS_NEEDED,
                            BluetoothAvailability.PERMISSION_DENIED, BluetoothAvailability.SETTINGS_REQUIRED)) {
                        Text("Nearby devices access is needed to find and connect to H10. Location access is not requested.")
                    }
                    Button(onClick = onBluetoothAction,
                        enabled = actionEnabled && availability != BluetoothAvailability.UNSUPPORTED) {
                        Text(availability.buttonLabel)
                    }
                }
                errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                Text("Data readiness", style = MaterialTheme.typography.titleMedium)
                checkedDataTypes.forEach { type ->
                    val state = readiness[type] ?: DataReadiness()
                    Text("${type.name}: ${state.status.message}")
                    state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    if (state.status == DataReadinessStatus.READY && !state.configurationComplete) {
                        Text(if (state.error == null) "Confirm available options before starting." else "Configuration blocked.")
                    }
                    if (!state.configurationComplete) {
                        state.available.filterKeys { it !in state.selected }.forEach { (setting, values) ->
                            val label = when (setting) {
                                SettingType.SAMPLE_RATE -> "Sample rate (Hz)"
                                SettingType.RESOLUTION -> "Resolution (bits)"
                                SettingType.RANGE -> if (type == PolarDeviceDataType.ACC) "Range (g)" else "Range (SDK units)"
                                SettingType.CHANNELS -> "Channels (count)"
                            }
                            Text("$label: ${values.sorted().joinToString()}")
                        }
                    }
                }
                Button(onClick = onRecheck,
                    enabled = canRecheck && readiness.values.none { it.status == DataReadinessStatus.CHECKING }) {
                    Text("Recheck data readiness")
                }
                Text("Saved devices", style = MaterialTheme.typography.titleMedium)
                Text("Previously connected by this app. A saved device is not necessarily nearby or online.")
                savedDevices.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                if (savedDevices.loading) Text("Loading saved devices...")
                else if (savedDevices.devices.isEmpty() && savedDevices.error == null) Text("No saved devices")
                savedDevices.devices.forEach { saved ->
                    OutlinedCard(onClick = { onConnect(saved.deviceId) }, enabled = canConnect,
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
                        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                            Text(saved.name)
                            Text("Device ID: ${saved.deviceId}")
                            val date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.ENGLISH)
                                .format(Date(saved.lastConnectedAt))
                            Text("Last connected: $date", style = MaterialTheme.typography.bodySmall)
                            Text("Tap to connect")
                        }
                    }
                }
                Text("Nearby Polar H10 devices", style = MaterialTheme.typography.titleMedium)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
                    verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Button(onClick = onStartScan, enabled = canConnect && !scanning) { Text("Start scan") }
                    Button(onClick = onStopScan, enabled = scanning) { Text("Stop scan") }
                }
                Text(scan.status.message)
                scan.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                if (scan.devices.isEmpty() && scan.status in setOf(ScanStatus.STOPPED, ScanStatus.TIMED_OUT)) {
                    Text("No Polar H10 found")
                }
                scan.devices.forEach { nearby ->
                    OutlinedCard(onClick = { onConnect(nearby.deviceId) }, enabled = canConnect,
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
                        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                            Text(nearby.name)
                            Text("Device ID: ${nearby.deviceId}")
                            Text("Signal strength: ${nearby.rssi} dBm")
                            Text("Tap to connect")
                        }
                    }
                }
            }
        }
    )
}
