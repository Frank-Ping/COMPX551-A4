package com.example.polarh10activityviewer.ble

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.R
import com.example.polarh10activityviewer.session.sessionBlue
import com.example.polarh10activityviewer.session.sessionBorder
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType

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
    onClose: () -> Unit,
    onClearSavedDevices: () -> Unit = {},
    streamErrors: List<String> = emptyList(),
    alerts: @Composable () -> Unit = {}
) {
    val bluetoothReady = actionEnabled && availability == BluetoothAvailability.READY
    val canConnect = bluetoothReady && connection.status == ConnectionStatus.NOT_CONNECTED
    val scanning = scan.status == ScanStatus.SCANNING
    val currentDevice = connection.device.takeIf { connection.status != ConnectionStatus.NOT_CONNECTED }
    val savedIds = savedDevices.devices.map { it.deviceId }.toSet()
    val nearby = scan.devices.filter { it.deviceId !in savedIds && it.deviceId != currentDevice?.deviceId }
    val maxHeight = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.height.toDp() * 0.8f }
    val statusColor = when {
        availability != BluetoothAvailability.READY || connection.error != null || connection.disconnectError != null -> MaterialTheme.colorScheme.error
        connection.status == ConnectionStatus.CONNECTED -> Color(0xFF07852D)
        connection.status in listOf(ConnectionStatus.CONNECTING, ConnectionStatus.DISCONNECTING) -> Color(0xFFE4A900)
        else -> MaterialTheme.colorScheme.outline
    }
    Dialog(onDismissRequest = onClose, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier.widthIn(max = 560.dp).fillMaxWidth(0.94f).height(maxHeight).testTag("devices-dialog"),
            shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, sessionBorder()),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DeviceBluetoothIcon(large = true)
                    Text("Devices", Modifier.weight(1f), fontSize = 26.sp, lineHeight = 32.sp,
                        fontWeight = FontWeight.SemiBold)
                    IconButton(onClick = onClose) {
                        Icon(painterResource(R.drawable.ic_close), "Close devices", tint = sessionBlue())
                    }
                }
                Surface(shape = CircleShape, color = statusColor.copy(alpha = 0.10f)) {
                    Row(Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(10.dp).background(statusColor, CircleShape))
                        Text(connectionStatusText(availability, connection), fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())
                    .testTag("devices-content"), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    currentDevice?.let { device ->
                        Text("Current device", style = MaterialTheme.typography.titleMedium)
                        DeviceCard(device.name, device.deviceId,
                            battery = if (connection.status == ConnectionStatus.CONNECTED && availability == BluetoothAvailability.READY)
                                "Battery: ${batteryLevel?.let { "$it%" } ?: "--"}" else null,
                            action = when (connection.status) {
                                ConnectionStatus.CONNECTED -> "Disconnect"
                                ConnectionStatus.DISCONNECTING -> if (connection.disconnectError != null) "Retry disconnect" else "Disconnecting"
                                else -> "Connecting"
                            }, enabled = bluetoothReady && (connection.status == ConnectionStatus.CONNECTED || connection.disconnectError != null),
                            onAction = if (connection.status == ConnectionStatus.CONNECTED) onDisconnect else onRetryDisconnect)
                    }
                    if (availability != BluetoothAvailability.READY) {
                        Text(availability.message)
                        if (availability in listOf(BluetoothAvailability.PERMISSIONS_NEEDED,
                                BluetoothAvailability.PERMISSION_DENIED, BluetoothAvailability.SETTINGS_REQUIRED)) {
                            Text("Nearby devices access is needed to find and connect to H10. Location access is not requested.",
                                style = MaterialTheme.typography.bodySmall)
                        }
                        Button(onClick = onBluetoothAction,
                            enabled = actionEnabled && availability != BluetoothAvailability.UNSUPPORTED) {
                            Text(availability.buttonLabel)
                        }
                    }
                    // Show errors and recovery only, without per-stream readiness status text.
                    val problems = checkedDataTypes.filter { type ->
                        val state = readiness[type] ?: DataReadiness()
                        state.status != DataReadinessStatus.READY || !state.configurationComplete
                    }
                    val showRecheck = connection.status == ConnectionStatus.CONNECTED && problems.isNotEmpty()
                    val errors = (listOfNotNull(connection.error, connection.disconnectError, connection.message, errorMessage) +
                        (if (connection.status == ConnectionStatus.CONNECTED) problems.mapNotNull { readiness[it]?.error } else emptyList()) +
                        streamErrors).distinct()
                    if (errors.isNotEmpty() || showRecheck) {
                        Row(Modifier.fillMaxWidth().testTag("device-error-row"), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(errors.joinToString(" · ").replace('\n', ' '), Modifier.weight(1f).testTag("device-error"),
                                fontSize = 11.sp, lineHeight = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.error)
                            if (showRecheck) Button(onClick = onRecheck,
                                modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 32.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                enabled = canRecheck && readiness.values.none { it.status == DataReadinessStatus.CHECKING }) {
                                Text("Recheck", fontSize = 11.sp, lineHeight = 15.sp, maxLines = 1)
                            }
                        }
                    }
                    alerts()
                    Row(Modifier.fillMaxWidth().padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("Saved devices", Modifier.weight(1f), fontSize = 18.sp,
                            lineHeight = 24.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis,
                            autoSize = TextAutoSize.StepBased(minFontSize = 11.sp, maxFontSize = 18.sp))
                        TextButton(onClick = onClearSavedDevices,
                            enabled = !savedDevices.loading && (savedDevices.devices.isNotEmpty() || savedDevices.error != null),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp)) {
                            Text("Clear History", fontSize = 11.sp, lineHeight = 15.sp, maxLines = 1)
                        }
                    }
                    savedDevices.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    val saved = savedDevices.devices.filter { it.deviceId != currentDevice?.deviceId }
                    when {
                        savedDevices.loading -> Text("Loading saved devices…")
                        saved.isEmpty() && savedDevices.error == null -> Text(if (currentDevice?.deviceId in savedIds) "No other saved devices" else "No saved devices",
                            style = MaterialTheme.typography.bodyMedium)
                    }
                    saved.forEach { device ->
                        DeviceCard(device.name, device.deviceId, action = "Connect", enabled = canConnect,
                            onAction = { onConnect(device.deviceId) })
                    }
                    Text("Nearby devices", Modifier.padding(top = 8.dp), fontSize = 20.sp,
                        lineHeight = 26.sp, fontWeight = FontWeight.SemiBold)
                    if (scan.status != ScanStatus.NOT_STARTED) Text(scan.status.message, style = MaterialTheme.typography.bodySmall)
                    scan.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    if (nearby.isEmpty()) Text(when {
                        scanning -> "Searching for devices…"
                        scan.status == ScanStatus.NOT_STARTED -> "Tap Scan to find Polar H10 devices."
                        scan.devices.isNotEmpty() -> "No new devices found."
                        scan.status in listOf(ScanStatus.STOPPED, ScanStatus.TIMED_OUT) -> "No Polar H10 found"
                        else -> "No nearby devices"
                    }, style = MaterialTheme.typography.bodyMedium)
                    nearby.forEach { device ->
                        DeviceCard(device.name, device.deviceId, action = "Connect", enabled = canConnect,
                            onAction = { onConnect(device.deviceId) })
                    }
                }
                Button(onClick = if (scanning) onStopScan else onStartScan,
                    enabled = scanning || canConnect, modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = sessionBlue(), contentColor = Color.White)) {
                    Icon(painterResource(if (scanning) R.drawable.ic_stop else R.drawable.ic_search), null, Modifier.size(22.dp))
                    Spacer(Modifier.width(10.dp))
                    Text(if (scanning) "Stop scan" else "Scan", fontWeight = FontWeight.SemiBold)
                }
                FilledTonalButton(onClick = onClose, modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = sessionBlue().copy(alpha = 0.08f),
                        contentColor = MaterialTheme.colorScheme.onSurface)) {
                    Text("Close", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun DeviceBluetoothIcon(large: Boolean = false) {
    Box(Modifier.size(if (large) 44.dp else 28.dp).background(sessionBlue().copy(alpha = 0.08f), CircleShape),
        contentAlignment = Alignment.Center) {
        Icon(painterResource(R.drawable.ic_bluetooth), null, Modifier.size(if (large) 30.dp else 20.dp), tint = sessionBlue())
    }
}

@Composable
private fun DeviceCard(name: String, id: String, action: String, enabled: Boolean, onAction: () -> Unit,
    battery: String? = null) {
    Surface(Modifier.fillMaxWidth().testTag("device-$id"), shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, sessionBorder())) {
        BoxWithConstraints(Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            val stack = maxWidth < 280.dp * LocalDensity.current.fontScale
            val identity: @Composable (Modifier) -> Unit = { modifier ->
                Row(modifier, verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    DeviceBluetoothIcon()
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(name, fontSize = 13.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium,
                            maxLines = 1, overflow = TextOverflow.Ellipsis)
                        if (!name.contains(id, ignoreCase = true)) Text(id, style = MaterialTheme.typography.bodySmall)
                        battery?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
                    }
                }
            }
            val control: @Composable (Modifier) -> Unit = { modifier ->
                Button(onClick = onAction, enabled = enabled,
                    modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 32.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = sessionBlue(), contentColor = Color.White)) {
                    Text(action, fontSize = 11.sp, lineHeight = 15.sp, fontWeight = FontWeight.Medium, maxLines = 1)
                }
            }
            if (stack) Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                identity(Modifier.fillMaxWidth())
                control(Modifier.align(Alignment.End))
            } else Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                identity(Modifier.weight(1f))
                control(Modifier)
            }
        }
    }
}
