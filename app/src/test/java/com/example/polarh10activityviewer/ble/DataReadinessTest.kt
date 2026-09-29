package com.example.polarh10activityviewer.ble

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.PolarBleApi.PolarBleSdkFeature
import com.polar.sdk.api.model.PolarSensorSetting.SettingType
import org.junit.Assert.*
import org.junit.Test

class DataReadinessTest {
    @Test fun stoppedHrNotificationsDoNotInvalidateConfirmedHrOrOnlineFeatures() {
        val features = mutableSetOf(
            PolarBleSdkFeature.FEATURE_HR, PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING
        )
        for (feature in features.toList()) {
            assertTrue(features.confirmReadiness(feature) { false })
        }
        // Disconnect/SDK release clears the manager's same set, so new connections must recheck.
        features.clear()
        assertFalse(features.confirmReadiness(PolarBleSdkFeature.FEATURE_HR) { false })
        assertFalse(features.confirmReadiness(PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING) { false })
        assertTrue(features.isEmpty())
    }

    @Test fun unresolvedFeatureCanBeConfirmedLaterWithoutRepeatedNotificationChecks() {
        val features = mutableSetOf<PolarBleSdkFeature>()
        val hr = PolarBleSdkFeature.FEATURE_HR
        assertFalse(features.confirmReadiness(hr) { false })
        assertTrue(features.confirmReadiness(hr) { true })
        assertTrue(features.confirmReadiness(hr) { error("Already confirmed for this connection") })
        assertFalse(features.confirmReadiness(PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING) { false })
    }

    @Test fun accSelectsConfirmed100HzAnd4g() {
        val result = checkedSettings(PolarDeviceDataType.ACC, mapOf(
            SettingType.SAMPLE_RATE to setOf(25, 100, 200),
            SettingType.RANGE to setOf(2, 4, 8),
            SettingType.RESOLUTION to setOf(16)
        ))
        assertEquals(100, result.selected[SettingType.SAMPLE_RATE])
        assertEquals(16, result.selected[SettingType.RESOLUTION])
        assertEquals(4, result.selected[SettingType.RANGE])
        assertTrue(result.configurationComplete)
        assertNull(result.error)
    }

    @Test fun accWithout100RemainsSupportedButBlocked() {
        val result = checkedSettings(PolarDeviceDataType.ACC, mapOf(SettingType.SAMPLE_RATE to setOf(25, 50)))
        assertEquals(DataReadinessStatus.READY, result.status)
        assertFalse(result.configurationComplete)
        assertFalse(result.selected.containsKey(SettingType.SAMPLE_RATE))
        assertNotNull(result.error)
    }

    @Test fun accWithout4gIsBlockedEvenWhenAnotherRangeIsTheOnlyOption() {
        for (ranges in listOf(setOf(2), setOf(8), setOf(2, 8))) {
            val result = checkedSettings(PolarDeviceDataType.ACC, mapOf(
                SettingType.SAMPLE_RATE to setOf(100), SettingType.RANGE to ranges
            ))
            assertFalse(result.configurationComplete)
            assertFalse(result.selected.containsKey(SettingType.RANGE))
            assertTrue(result.error!!.contains("4 g"))
        }
        val missing = checkedSettings(PolarDeviceDataType.ACC, mapOf(SettingType.SAMPLE_RATE to setOf(100)))
        assertFalse(missing.configurationComplete)
        assertNotNull(missing.error)
    }

    @Test fun accOtherMultipleOptionsStillRequireConfirmation() {
        val result = checkedSettings(PolarDeviceDataType.ACC, mapOf(
            SettingType.SAMPLE_RATE to setOf(100), SettingType.RANGE to setOf(4),
            SettingType.RESOLUTION to setOf(8, 16)
        ))
        assertFalse(result.configurationComplete)
        assertFalse(result.selected.containsKey(SettingType.RESOLUTION))
        assertEquals(4, result.selected[SettingType.RANGE])
        assertNull(result.error)
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
