package com.example.polarh10activityviewer.session

import android.graphics.Bitmap
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.sp
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.polar.sdk.api.model.PolarDeviceInfo
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File

// Synthetic, labeled device states exercise the production dialog without BLE operations.
class DevicesDialogTest {
    @get:Rule val compose = createComposeRule()
    private val dark = mutableStateOf(false)
    private val availability = mutableStateOf(BluetoothAvailability.READY)
    private val connection = mutableStateOf(ConnectionState())
    private val readiness = mutableStateOf(checkedDataTypes.associateWith {
        DataReadiness(DataReadinessStatus.READY, configurationComplete = true)
    })
    private val saved = mutableStateOf(SavedDevicesState(listOf(
        SavedDevice("Polar H10 A1B2C3D4", "A1B2C3D4", 2000),
        SavedDevice("Polar H10 E5F6A7B8", "E5F6A7B8", 1000)), loading = false))
    private val scan = mutableStateOf(ScanState(ScanStatus.STOPPED, listOf(device("C9D0E1F2"))))
    private var starts = 0
    private var stops = 0
    private var closes = 0
    private var disconnects = 0
    private var retryDisconnects = 0
    private var bluetoothActions = 0
    private var selected: String? = null
    private var clears = 0

    private fun device(id: String) = PolarDeviceInfo(id, "00:00:00:00:00:01", -55, "Polar H10 $id", true, true, true, false)
    private fun mount() = compose.setContent {
        PolarH10ActivityViewerTheme(darkTheme = dark.value) {
            DevicesDialog(availability.value, true, connection.value, 86, saved.value, scan.value,
                readiness.value,
                true, null, { bluetoothActions++ }, { selected = it }, { disconnects++ }, { retryDisconnects++ },
                { starts++; scan.value = ScanState(ScanStatus.SCANNING) },
                { stops++; scan.value = scan.value.copy(status = ScanStatus.STOPPED) }, {}, { closes++ },
                onClearSavedDevices = { clears++; saved.value = SavedDevicesState(loading = false) },
                alerts = { Text("UI TEST DATA · no H10", fontSize = 10.sp) })
        }
    }
    private fun capture(name: String) {
        compose.waitForIdle()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dir = File(context.getExternalFilesDir(null), "step86-devices").apply { mkdirs() }
        File(dir, "$name.png").outputStream().use {
            compose.onNode(isDialog()).captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
    private fun oneLine(text: String, allowEllipsis: Boolean = false) {
        val layouts = mutableListOf<TextLayoutResult>()
        compose.onNodeWithText(text).performScrollTo().assertIsDisplayed()
            .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
        val layout = layouts.single()
        assertEquals(1, layout.lineCount)
        if (!allowEllipsis) assertFalse("Ellipsis: $text", layout.isLineEllipsized(0))
        assertTrue("Vertical overflow: $text", layout.multiParagraph.height <= layout.size.height + 1)
        repeat(layout.lineCount) { line ->
            assertTrue(layout.getLineLeft(line) >= -1)
            assertTrue(layout.getLineRight(line) <= layout.size.width + 1)
        }
    }
    @Test fun referenceCardsPreserveThemesAndConnectOnlyThroughTheirButton() {
        mount()
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            oneLine("Saved devices")
            oneLine("Polar H10 A1B2C3D4")
            compose.onNodeWithText("Polar H10 C9D0E1F2").performScrollTo().assertIsDisplayed()
            compose.onNodeWithText("Scan").assertIsDisplayed()
            compose.onNodeWithText("Close").assertIsDisplayed()
            compose.onNodeWithTag("devices-content").performScrollToNode(hasText("UI TEST DATA · no H10"))
            capture(if (night) "reference-dark" else "reference-light")
        }
        compose.onNodeWithText("Polar H10 A1B2C3D4").performScrollTo().assertHasNoClickAction()
        compose.onNodeWithTag("device-A1B2C3D4").assertHasNoClickAction()
        compose.onNode(hasText("Connect") and hasAnyAncestor(hasTestTag("device-A1B2C3D4"))).performScrollTo().performClick()
        assertEquals("A1B2C3D4", selected)
        assertEquals(0, closes)
        listOf("Last connected:", "Signal strength:", "Data readiness", "Session:", "Tap to connect").forEach {
            compose.onAllNodesWithText(it, substring = true).assertCountEquals(0)
        }
    }
    @Test fun savedAndNearbyDeduplicateWhileScanTogglesWithoutDroppingStoppedResults() {
        scan.value = ScanState(ScanStatus.STOPPED, listOf(device("A1B2C3D4"), device("C9D0E1F2")))
        mount()
        compose.onAllNodesWithText("Polar H10 A1B2C3D4").assertCountEquals(1)
        compose.onNodeWithText("Scan").performClick()
        assertEquals(1, starts)
        compose.onNodeWithText("Polar H10 C9D0E1F2").assertDoesNotExist()
        compose.runOnIdle { scan.value = scan.value.copy(devices = listOf(device("A1B2C3D4"))) }
        compose.onNodeWithText("Stop scan").assertIsDisplayed().performClick()
        assertEquals(1, stops)
        assertEquals(1, scan.value.devices.size)
        compose.onNodeWithText("No new devices found.").performScrollTo().assertIsDisplayed()
        compose.onAllNodesWithText("Polar H10 A1B2C3D4").assertCountEquals(1)
    }
    @Test fun currentDeviceIsUniqueAndBusyActionsStayDisabled() {
        connection.value = ConnectionState(ConnectionStatus.CONNECTING, ConnectionDevice("Polar H10 A1B2C3D4", "A1B2C3D4"))
        mount()
        compose.onAllNodesWithText("Polar H10 A1B2C3D4").assertCountEquals(1)
        compose.onAllNodesWithText("Connect").fetchSemanticsNodes().indices.forEach {
            compose.onAllNodesWithText("Connect")[it].assertIsNotEnabled()
        }
        compose.onNodeWithText("Scan").assertIsNotEnabled()
        compose.runOnIdle { connection.value = connection.value.copy(status = ConnectionStatus.CONNECTED) }
        compose.onNodeWithText("Battery: 86%").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Disconnect").performScrollTo().performClick()
        assertEquals(1, disconnects); assertEquals(0, closes)
        compose.runOnIdle { connection.value = connection.value.copy(status = ConnectionStatus.DISCONNECTING, disconnectError = "Controlled disconnect failure") }
        compose.onNodeWithText("Retry disconnect").performScrollTo().performClick()
        assertEquals(1, retryDisconnects)
        compose.onNodeWithText("Battery: 86%").assertDoesNotExist()
    }
    @Test fun unavailableBluetoothAndEmptyListsKeepActionsAndDismissalAccessible() {
        saved.value = SavedDevicesState(loading = false)
        scan.value = ScanState()
        availability.value = BluetoothAvailability.BLUETOOTH_OFF
        mount()
        compose.onNodeWithText(BluetoothAvailability.BLUETOOTH_OFF.buttonLabel).performScrollTo().performClick()
        assertEquals(1, bluetoothActions)
        compose.onNodeWithText("Scan").assertIsNotEnabled()
        compose.onNodeWithText("No saved devices").performScrollTo().assertIsDisplayed()
        compose.onNodeWithContentDescription("Close devices").assertIsDisplayed().performClick()
        compose.onNodeWithText("Close").assertIsDisplayed().performClick()
        assertEquals(2, closes); assertEquals(0, starts)
    }
    @Test fun longNamesAndManyDevicesRemainReadableWithActualSystemFont() {
        val longName = "Polar H10 controlled long device name for readability checking"
        saved.value = SavedDevicesState(List(12) { SavedDevice("$longName $it", "TEST$it", 1200L - it) }, false)
        mount()
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            oneLine("$longName 0", allowEllipsis = true)
            capture(if (night) "long-dark-top" else "long-light-top")
            oneLine("$longName 11", allowEllipsis = true)
            compose.onNodeWithText("Scan").assertIsDisplayed()
            compose.onNodeWithText("Close").assertIsDisplayed()
            compose.onNodeWithContentDescription("Close devices").assertIsDisplayed()
            capture(if (night) "long-dark-bottom" else "long-light-bottom")
        }
    }

    @Test fun clearHistoryRemovesSavedRowsWithoutChangingTheConnectionAndRevealsNearbyDevices() {
        connection.value = ConnectionState(ConnectionStatus.CONNECTED, ConnectionDevice("Polar H10 A1B2C3D4", "A1B2C3D4"))
        scan.value = ScanState(ScanStatus.STOPPED, listOf(device("A1B2C3D4"), device("E5F6A7B8")))
        mount()
        val before = connection.value
        compose.onNodeWithText("Clear History").performScrollTo().performClick()
        assertEquals(1, clears)
        assertEquals(before, connection.value)
        assertEquals(0, disconnects)
        compose.onNodeWithText("No saved devices").performScrollTo().assertIsDisplayed()
        compose.onAllNodesWithText("Polar H10 E5F6A7B8").assertCountEquals(1)
        assertTrue(compose.onNodeWithText("Polar H10 E5F6A7B8").getUnclippedBoundsInRoot().top >
            compose.onNodeWithText("Nearby devices").getUnclippedBoundsInRoot().top)
        compose.onNodeWithText("Clear History").assertIsNotEnabled()
        capture("cleared-connected")
    }

    @Test fun errorAndRecheckShareOneLineAndDialogSizeStaysFixedAcrossConnectionChanges() {
        mount()
        val bounds = compose.onNodeWithTag("devices-dialog").getUnclippedBoundsInRoot()
        val name = "Polar H10 A1B2C3D4"
        for (status in listOf(ConnectionStatus.CONNECTING, ConnectionStatus.CONNECTED, ConnectionStatus.DISCONNECTING)) {
            compose.runOnIdle { connection.value = ConnectionState(status, ConnectionDevice(name, "A1B2C3D4")) }
            assertEquals(bounds, compose.onNodeWithTag("devices-dialog").getUnclippedBoundsInRoot())
        }
        compose.runOnIdle {
            connection.value = ConnectionState(ConnectionStatus.CONNECTED, ConnectionDevice(name, "A1B2C3D4"))
            readiness.value = readiness.value + (com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.ACC to
                DataReadiness(DataReadinessStatus.FAILED, error = "Controlled long configuration failure. ".repeat(15)))
        }
        val layouts = mutableListOf<TextLayoutResult>()
        compose.onNodeWithTag("device-error").performScrollTo().performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
        assertEquals(1, layouts.single().lineCount)
        assertTrue(layouts.single().isLineEllipsized(0))
        val errorBounds = compose.onNodeWithTag("device-error").getUnclippedBoundsInRoot()
        val recheckBounds = compose.onNodeWithText("Recheck").getUnclippedBoundsInRoot()
        assertTrue(errorBounds.right <= recheckBounds.left)
        assertEquals((errorBounds.top.value + errorBounds.bottom.value) / 2,
            (recheckBounds.top.value + recheckBounds.bottom.value) / 2, 1f)
        assertEquals(bounds, compose.onNodeWithTag("devices-dialog").getUnclippedBoundsInRoot())
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            capture(if (night) "connected-error-dark" else "connected-error-light")
        }
    }
}
