package com.example.polarh10activityviewer

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting.SettingType

enum class DataReadinessStatus(val message: String) {
    DISCONNECTED("Not connected"), WAITING("Waiting for readiness"), CHECKING("Checking"),
    READY("Ready"), UNSUPPORTED("Unsupported"), FAILED("Check failed")
}

data class DataReadiness(
    val status: DataReadinessStatus = DataReadinessStatus.DISCONNECTED,
    val available: Map<SettingType, Set<Int>> = emptyMap(),
    val selected: Map<SettingType, Int> = emptyMap(),
    val configurationComplete: Boolean = false,
    val error: String? = null
)

val checkedDataTypes = listOf(PolarDeviceDataType.HR, PolarDeviceDataType.ACC, PolarDeviceDataType.ECG)

// Keep selection policy separate from the temporary development UI.
internal fun checkedSettings(type: PolarDeviceDataType, settings: Map<SettingType, Set<Int>>): DataReadiness {
    val available = settings.mapValues { it.value.toSet() }
    if (available[SettingType.SAMPLE_RATE].isNullOrEmpty() ||
        available.values.any { values -> values.isEmpty() || values.any { it <= 0 } }) {
        return DataReadiness(DataReadinessStatus.FAILED, available,
            error = "Missing or invalid settings. Recheck this device.")
    }
    val selected = available.mapNotNull { (key, values) ->
        val value = if (type == PolarDeviceDataType.ACC && key == SettingType.SAMPLE_RATE) {
            100.takeIf { it in values }
        } else values.singleOrNull()
        value?.let { key to it }
    }.toMap()
    val accRateMissing = type == PolarDeviceDataType.ACC && 100 !in available.getValue(SettingType.SAMPLE_RATE)
    return DataReadiness(
        status = DataReadinessStatus.READY,
        available = available,
        selected = selected,
        configurationComplete = !accRateMissing && selected.keys == available.keys,
        error = if (accRateMissing) "ACC disabled: 100 Hz is unavailable. No alternative rate selected." else null
    )
}
