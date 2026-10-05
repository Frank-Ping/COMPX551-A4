package com.example.polarh10activityviewer.ble

import com.polar.sdk.api.model.PolarDeviceInfo
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PolarScannerTest {
    private fun device(id: String, name: String = "Polar H10 $id", rssi: Int = -55) =
        PolarDeviceInfo(id, "00:00:00:00:00:01", rssi, name, true, true, true, false)

    @Test fun filtersNamesAndBlankIdsAndUpdatesDuplicatesInOrder() = runTest {
        val scanner = PolarScanner(this)
        val first = device("A", "Polar H10")
        val second = device("B")
        val updated = device("A", rssi = -40)
        scanner.start {
            flow {
                emit(first)
                emit(device("OTHER", "Polar H9 OTHER"))
                emit(device("PREFIX", "Polar H100 PREFIX"))
                emit(device(" "))
                emit(second)
                emit(updated)
            }
        }
        runCurrent()
        assertEquals(ScanStatus.STOPPED, scanner.state.value.status)
        assertEquals(listOf(updated, second), scanner.state.value.devices)
        assertNull(scanner.state.value.error)
    }

    @Test fun timesOutAtThirtySecondsAndKeepsFoundDevices() = runTest {
        val scanner = PolarScanner(this)
        val found = device("A")
        scanner.start { flow { emit(found); awaitCancellation() } }
        runCurrent()
        advanceTimeBy(29_999)
        runCurrent()
        assertEquals(ScanStatus.SCANNING, scanner.state.value.status)
        advanceTimeBy(1)
        runCurrent()
        assertEquals(ScanStatus.TIMED_OUT, scanner.state.value.status)
        assertEquals(listOf(found), scanner.state.value.devices)
        assertNull(scanner.state.value.error)
    }

    @Test fun duplicateStartDoesNotReplaceCurrentScanAndStopRejectsLaterInput() = runTest {
        val scanner = PolarScanner(this)
        val source = MutableSharedFlow<PolarDeviceInfo>()
        var starts = 0
        scanner.start { starts++; source }
        runCurrent()
        source.emit(device("A"))
        runCurrent()
        scanner.start { starts++; emptyFlow() }
        runCurrent()
        assertEquals(1, starts)
        scanner.stop()
        scanner.stop()
        source.emit(device("B"))
        runCurrent()
        assertEquals(ScanStatus.STOPPED, scanner.state.value.status)
        assertEquals(listOf("A"), scanner.state.value.devices.map { it.deviceId })
    }

    @Test fun restartWaitsForOldCleanupAndIgnoresItsLateFailure() = runTest {
        val cleanup = CompletableDeferred<Unit>()
        val logged = mutableListOf<Exception>()
        val scanner = PolarScanner(this, logged::add)
        scanner.start {
            flow {
                try {
                    emit(device("OLD"))
                    awaitCancellation()
                } finally {
                    withContext(NonCancellable) { cleanup.await() }
                    throw IllegalStateException("old scan cleanup failed")
                }
            }
        }
        runCurrent()
        scanner.stop(ScanStatus.INTERRUPTED)
        var restarted = false
        scanner.start {
            restarted = true
            flow { emit(device("NEW")); awaitCancellation() }
        }
        runCurrent()
        assertFalse(restarted)
        assertEquals(ScanStatus.SCANNING, scanner.state.value.status)
        assertTrue(scanner.state.value.devices.isEmpty())
        cleanup.complete(Unit)
        runCurrent()
        assertTrue(restarted)
        assertEquals(ScanStatus.SCANNING, scanner.state.value.status)
        assertEquals(listOf("NEW"), scanner.state.value.devices.map { it.deviceId })
        assertTrue(logged.isEmpty())
        assertNull(scanner.state.value.error)
        scanner.release()
        runCurrent()
        assertEquals(ScanStatus.INTERRUPTED, scanner.state.value.status)
    }

    @Test fun factoryFailureIsReportedAndManualRetryClearsIt() = runTest {
        val failure = IllegalStateException("controlled scan failure")
        val logged = mutableListOf<Exception>()
        val scanner = PolarScanner(this, logged::add)
        scanner.start { throw failure }
        runCurrent()
        assertEquals(ScanStatus.ERROR, scanner.state.value.status)
        assertTrue(scanner.state.value.error!!.contains("IllegalStateException"))
        assertEquals(1, logged.size)
        assertEquals(failure.javaClass, logged.single().javaClass)
        assertEquals(failure.message, logged.single().message)
        scanner.start { emptyFlow() }
        assertNull(scanner.state.value.error)
        runCurrent()
        assertEquals(ScanStatus.STOPPED, scanner.state.value.status)
    }

    @Test fun releaseBeforeQueuedStartNeverOpensTheSearch() = runTest {
        val scanner = PolarScanner(this)
        var starts = 0
        scanner.start { starts++; emptyFlow() }
        scanner.release()
        runCurrent()
        assertEquals(0, starts)
        assertEquals(ScanStatus.INTERRUPTED, scanner.state.value.status)
        scanner.start { starts++; emptyFlow() }
        runCurrent()
        assertEquals(1, starts)
        assertEquals(ScanStatus.STOPPED, scanner.state.value.status)
    }
}
