package com.example.polarh10activityviewer

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting.SettingType
import org.junit.Assert.*
import org.junit.Test

class DataReadinessTest {
    @Test fun accSelects100ButDoesNotGuessRange() {
        val result = checkedSettings(PolarDeviceDataType.ACC, mapOf(
            SettingType.SAMPLE_RATE to setOf(25, 100, 200),
            SettingType.RANGE to setOf(2, 4, 8),
            SettingType.RESOLUTION to setOf(16)
        ))
        assertEquals(100, result.selected[SettingType.SAMPLE_RATE])
        assertEquals(16, result.selected[SettingType.RESOLUTION])
        assertFalse(result.selected.containsKey(SettingType.RANGE))
        assertFalse(result.configurationComplete)
        assertNull(result.error)
    }

    @Test fun accWithout100RemainsSupportedButBlocked() {
        val result = checkedSettings(PolarDeviceDataType.ACC, mapOf(SettingType.SAMPLE_RATE to setOf(25, 50)))
        assertEquals(DataReadinessStatus.READY, result.status)
        assertFalse(result.configurationComplete)
        assertFalse(result.selected.containsKey(SettingType.SAMPLE_RATE))
        assertNotNull(result.error)
    }

    @Test fun ecgAcceptsOnlySingleOptions() {
        val result = checkedSettings(PolarDeviceDataType.ECG, mapOf(
            SettingType.SAMPLE_RATE to setOf(130), SettingType.RESOLUTION to setOf(14)
        ))
        assertTrue(result.configurationComplete)
        val multiple = checkedSettings(PolarDeviceDataType.ECG, mapOf(SettingType.SAMPLE_RATE to setOf(100, 130)))
        assertFalse(multiple.configurationComplete)
        assertTrue(multiple.selected.isEmpty())
    }

    @Test fun missingOrInvalidSettingsCannotBeComplete() {
        for (settings in listOf(emptyMap(), mapOf(SettingType.SAMPLE_RATE to emptySet()),
            mapOf(SettingType.SAMPLE_RATE to setOf(0)))) {
            val result = checkedSettings(PolarDeviceDataType.ACC, settings)
            assertEquals(DataReadinessStatus.FAILED, result.status)
            assertFalse(result.configurationComplete)
        }
    }
}
