package com.example.polarh10activityviewer.session

import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.ble.*
import org.junit.Assert.assertEquals
import org.junit.Test

class SessionStartGuardTest {
    @Test fun newControlsPreserveTheOriginalStartGuardForEveryStateCombination() {
        BluetoothAvailability.entries.forEach { availability ->
            ConnectionStatus.entries.forEach { connection ->
                SessionStatus.entries.forEach { session ->
                    SubscriptionStatus.entries.forEach { subscription ->
                        listOf(false, true).forEach { action ->
                            listOf(false, true).forEach { saving ->
                                listOf(false, true).forEach { ready ->
                                    val subscriptions = listOf(SubscriptionState(subscription), SubscriptionState(), SubscriptionState())
                                    val readiness = listOf(DataReadiness(if (ready) DataReadinessStatus.READY else DataReadinessStatus.CHECKING,
                                        configurationComplete = ready))
                                    val original = action && availability == BluetoothAvailability.READY &&
                                        connection == ConnectionStatus.CONNECTED && !saving &&
                                        session in setOf(SessionStatus.IDLE, SessionStatus.STOPPED) &&
                                        subscriptions.none { it.status in setOf(SubscriptionStatus.STARTING,
                                            SubscriptionStatus.RECEIVING, SubscriptionStatus.STOPPING) } &&
                                        readiness.any { it.status == DataReadinessStatus.READY && it.configurationComplete }
                                    assertEquals(original, startDisabledReason(availability, action, ConnectionState(connection),
                                        SessionState(session), saving, subscriptions, readiness) == null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
