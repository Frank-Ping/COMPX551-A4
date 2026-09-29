package com.example.polarh10activityviewer.ble

import org.junit.Assert.*
import org.junit.Test

class DeviceBatteryTest {
    private val sdk = Any()
    private val connected = ConnectionState(
        ConnectionStatus.CONNECTED, ConnectionDevice("Test device", "test-id")
    )
    private val battery = DeviceBattery()

    @Test fun startsUnknownAndAcceptsBothBoundaries() {
        assertNull(battery.level.value)
        for (value in listOf(0, 100, 47)) {
            battery.receive(sdk, sdk, "test-id", connected, value)
            assertEquals(value, battery.level.value)
        }
    }

    @Test fun invalidValuesDoNotReplaceUnknownOrLastValidValue() {
        for (value in listOf(-1, 101, Int.MIN_VALUE, Int.MAX_VALUE)) {
            battery.receive(sdk, sdk, "test-id", connected, value)
            assertNull(battery.level.value)
        }
        battery.receive(sdk, sdk, "test-id", connected, 0)
        for (value in listOf(-1, 101)) {
            battery.receive(sdk, sdk, "test-id", connected, value)
            assertEquals(0, battery.level.value)
        }
    }

    @Test fun clearingIsRepeatableAndReconnectionWaitsForNewValue() {
        battery.receive(sdk, sdk, "test-id", connected, 70)
        battery.clear()
        battery.clear()
        assertNull(battery.level.value)
        val newSdk = Any()
        battery.receive(sdk, newSdk, "test-id", connected, 71)
        assertNull(battery.level.value)
        battery.receive(newSdk, newSdk, "test-id", connected, 65)
        assertEquals(65, battery.level.value)
    }

    @Test fun nonConnectedStatesAndMissingDeviceCannotRestoreClearedValue() {
        for (status in ConnectionStatus.entries.filter { it != ConnectionStatus.CONNECTED }) {
            battery.receive(sdk, sdk, "test-id", connected, 70)
            battery.clear()
            battery.receive(sdk, sdk, "test-id", connected.copy(status = status), 71)
            assertNull(battery.level.value)
        }
        battery.receive(sdk, sdk, "test-id", connected.copy(device = null), 71)
        assertNull(battery.level.value)
    }

    @Test fun wrongDeviceAndReleasedSdkCannotUpdateBattery() {
        battery.receive(sdk, sdk, "test-id", connected, 55)
        battery.receive(sdk, sdk, "other-device", connected, 99)
        assertEquals(55, battery.level.value)
        battery.clear()
        battery.receive(sdk, null, "test-id", connected, 99)
        assertNull(battery.level.value)
    }

    @Test fun sdkMustBeTheSameInstanceEvenIfValuesAreEqual() {
        data class Token(val name: String)
        val previous = Token("sdk")
        val current = Token("sdk")
        battery.receive(current, current, "test-id", connected, 40)
        battery.receive(previous, current, "test-id", connected, 90)
        assertEquals(40, battery.level.value)
    }

    @Test fun queuedCallbackChecksConnectionAtDeliveryTime() {
        var currentSdk: Any? = sdk
        var connection = connected
        val queuedCallback = { battery.receive(sdk, currentSdk, "test-id", connection, 90) }
        battery.receive(sdk, sdk, "test-id", connection, 70)
        battery.clear()
        connection = connected.copy(status = ConnectionStatus.DISCONNECTING)
        queuedCallback()
        assertNull(battery.level.value)
        currentSdk = Any()
        connection = connected
        battery.receive(currentSdk!!, currentSdk, "test-id", connection, 60)
        queuedCallback()
        assertEquals(60, battery.level.value)
    }
}
