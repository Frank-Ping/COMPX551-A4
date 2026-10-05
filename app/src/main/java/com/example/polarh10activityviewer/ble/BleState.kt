package com.example.polarh10activityviewer.ble

import com.polar.sdk.api.model.PolarDeviceInfo

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
