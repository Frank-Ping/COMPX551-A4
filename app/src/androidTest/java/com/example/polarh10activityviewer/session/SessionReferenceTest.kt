package com.example.polarh10activityviewer.session

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.Density
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.SessionScreen
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.chart.*
import com.example.polarh10activityviewer.heartrate.*
import com.example.polarh10activityviewer.history.HistoryPanel
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.storage.SessionDatabase
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting.SettingType
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.util.UUID
import kotlin.math.sin

// Populate the production page composition, never the BLE manager or saved sessions.
class SessionReferenceTest {
    @get:Rule val compose = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val databaseName = "reference-${UUID.randomUUID()}.db"
    private val database = SessionDatabase(context, databaseName)
    private val selected = mutableStateOf(ChartKind.HEART_RATE)
    private val dark = mutableStateOf(false)
    private val phase = mutableStateOf(SessionStatus.RUNNING)
    private var pauses = 0
    private var resumes = 0
    private var stops = 0
    private val empty = mutableStateOf(false)
    private val failed = mutableStateOf(false)
    private val streams get() = checkedDataTypes.associateWith { SubscriptionState(if (failed.value) SubscriptionStatus.FAILED else if (empty.value) SubscriptionStatus.IDLE else SubscriptionStatus.RECEIVING, if (failed.value) "Controlled stream failure with full recovery information. ".repeat(10) else null) }
    private val readiness = checkedDataTypes.associateWith {
        DataReadiness(DataReadinessStatus.READY, selected = mapOf(SettingType.SAMPLE_RATE to 130), configurationComplete = true)
    }

    @After fun closeDatabase() { database.close(); context.deleteDatabase(databaseName) }

    private fun chart(kind: ChartKind): ChartSnapshot {
        if (empty.value) return ChartSnapshot(emptyList(), 0.0, 300000.0, SubscriptionStatus.IDLE)
        val points = when (kind) {
            ChartKind.ELECTROCARDIOGRAM -> List(650) { index ->
                val beat = index % 100
                val value = when (beat) { 40 -> 850.0; 41 -> -180.0; else -> 25.0 * sin(index * 0.18) }
                ChartPoint(745000.0 + index * 5000.0 / 650, value, index == 0)
            }
            ChartKind.CADENCE -> List(301) { index -> ChartPoint(450000.0 + index * 1000,
                if (index in 175..220) 0.0 else 100 + 15 * sin(index * 0.045), index == 0) }
            else -> List(301) { index -> ChartPoint(450000.0 + index * 1000,
                125 + 12 * sin(index * 0.035) + 3 * sin(index * 0.6), index == 0) }
        }
        return ChartSnapshot(points, 750000.0, if (kind.type == PolarDeviceDataType.ECG) 5000.0 else 300000.0,
            SubscriptionStatus.RECEIVING)
    }

    private fun mount(systemFont: Boolean = false) = compose.setContent {
        val density = LocalDensity.current
        CompositionLocalProvider(LocalDensity provides if (systemFont) density else Density(density.density, 1f)) {
            PolarH10ActivityViewerTheme(darkTheme = dark.value) {
                var history by remember { mutableStateOf(false) }
                SessionScaffold(history, { history = it }, controls = {
                    SessionControls(phase.value == SessionStatus.IDLE, phase.value in listOf(SessionStatus.RUNNING, SessionStatus.PAUSED), {}, { stops++; phase.value = SessionStatus.IDLE; empty.value = true; failed.value = false; selected.value = ChartKind.HEART_RATE },
                        phase.value == SessionStatus.RUNNING, phase.value == SessionStatus.PAUSED,
                        phase.value == SessionStatus.PAUSED,
                        { pauses++; phase.value = SessionStatus.PAUSED }, { resumes++; phase.value = SessionStatus.RUNNING })
                }, historyContent = { HistoryPanel(database, null, { history = false }) }, sessionContent = {
                    Column {
                        Text("UI TEST DATA · no H10", fontSize = 10.sp, lineHeight = 12.sp, modifier = Modifier.padding(horizontal = SessionInset))
                        SessionScreen(availability = BluetoothAvailability.READY, actionEnabled = true,
                            errorMessage = null, onBluetoothAction = {}, scanState = ScanState(), onStartScan = {}, onStopScan = {},
                            connectionState = ConnectionState(ConnectionStatus.CONNECTED, ConnectionDevice("Test H10", "TEST1234")),
                            onConnect = {}, savedDevicesState = SavedDevicesState(loading = false), onDisconnect = {},
                            onRetryDisconnect = {}, dataReadiness = readiness, onRecheckData = {},
                            modifier = Modifier.testTag("reference-scroll"), batteryLevel = 86,
                            heartRate = if (empty.value || failed.value) null else HeartRateReading(132, 1_791_019_860_000),
                            heartRateStatistics = if (empty.value) HeartRateStatistics() else HeartRateStatistics(10, 1250, 100, 158),
                            heartRateZones = if (empty.value) HeartRateZoneState() else HeartRateZoneState(listOf(120000, 210000, 330000, 80000, 10000),
                                current = HeartRateZone.MODERATE, receivedValidHr = true),
                            hrSubscription = streams.getValue(PolarDeviceDataType.HR),
                            accSubscription = streams.getValue(PolarDeviceDataType.ACC),
                            ecgSubscription = streams.getValue(PolarDeviceDataType.ECG),
                            steps = if (empty.value) StepState() else StepState(totalSteps = 1200, cadence = 108.0, maximumCadence = 144.0, distance = 875.0,
                                receivedAcc = true, durationMs = 750000, message = "Detecting steps."),
                            session = SessionState(phase.value, elapsedMs = if (empty.value) 0 else 750000),
                            charts = {
                                val kind = selected.value
                                LiveChartCard(kind, chart(kind), if (empty.value) null else if (kind == ChartKind.HEART_RATE) 125.0 else 96.0,
                                    if (empty.value) null else if (kind == ChartKind.HEART_RATE) 158.0 else 144.0,
                                    readiness, { selected.value = it }, phase.value == SessionStatus.PAUSED)
                            })
                    }
                })
            }
        }
    }

    private fun capture(name: String) {
        compose.mainClock.advanceTimeBy(600)
        compose.waitForIdle()
        // Let the platform ripple finish before saving visual evidence.
        android.os.SystemClock.sleep(400)
        val dir = File(context.getExternalFilesDir(null), "step84f-reference").apply { mkdirs() }
        File(dir, "$name.png").outputStream().use {
            (if (name.endsWith("details")) compose.onNode(isDialog()) else compose.onRoot()).captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    private fun noTextOverflow() {
        val nodes = compose.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.GetTextLayoutResult), useUnmergedTree = true)
        repeat(nodes.fetchSemanticsNodes().size) { index ->
            val layouts = mutableListOf<TextLayoutResult>()
            nodes[index].performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
            layouts.forEach { layout ->
                assertFalse("Truncated: ${layout.layoutInput.text}", layout.multiParagraph.didExceedMaxLines)
                assertTrue("Vertical overflow: ${layout.layoutInput.text}", layout.size.height >= layout.multiParagraph.height - 1)
                repeat(layout.lineCount) { line ->
                    assertTrue("Right overflow: ${layout.layoutInput.text}", layout.getLineRight(line) <= layout.size.width + 1)
                    assertTrue("Left overflow: ${layout.layoutInput.text}", layout.getLineLeft(line) >= -1)
                }
            }
        }
    }

    private fun singleScreen() {
        compose.onAllNodes(SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.SemanticsProperties.VerticalScrollAxisRange)).assertCountEquals(0)
        val bottom = compose.onNodeWithContentDescription("Pause").getUnclippedBoundsInRoot().top
        compose.onNodeWithText("Data Streams").assertDoesNotExist()
        compose.onNodeWithContentDescription("Open Devices").assertIsDisplayed()
        for (label in listOf("Cadence", "Duration", "Total Steps", "HR Zone", "Z5")) {
            val node = compose.onNodeWithText(label).assertIsDisplayed()
            val bounds = node.getUnclippedBoundsInRoot()
            assertTrue("$label below footer: $bounds", bounds.bottom <= bottom)
            assertTrue("$label has no height", bounds.bottom > bounds.top)
        }
        compose.onNodeWithTag("live-chart-plot").assertIsDisplayed()
        compose.onAllNodesWithText("Retry", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("· Details", substring = true).assertCountEquals(0)
        noTextOverflow()
    }

    @Test fun normalPhoneShowsAllRegionsWithoutScrollingInAllThreeViews() {
        mount()
        compose.onNodeWithText("Motion").performClick()
        val cardBounds = compose.onNodeWithTag("live-chart-card").getUnclippedBoundsInRoot()
        val plotHeight = compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot().height
        val lowerLabels = listOf("Duration", "Total Steps", "HR Zone", "Z5")
        val lowerBounds = lowerLabels.map { compose.onNodeWithText(it).getUnclippedBoundsInRoot() }
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            val prefix = if (night) "dark" else "light"
            for (label in listOf("HR", "Motion", "ECG")) {
                compose.onNodeWithText(label).performClick().assertIsSelected()
                capture("$prefix-$label")
                singleScreen()
                assertEquals(cardBounds, compose.onNodeWithTag("live-chart-card").getUnclippedBoundsInRoot())
                assertEquals(plotHeight, compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot().height)
                assertEquals(lowerBounds, lowerLabels.map { compose.onNodeWithText(it).getUnclippedBoundsInRoot() })
            }
            val summary = listOf("Duration", "Total Steps").map {
                compose.onNodeWithText(it).fetchSemanticsNode().positionInRoot }
            assertEquals(summary[0].y, summary[1].y, 1f)
            compose.onNodeWithText("Estimated Distance").assertDoesNotExist()
        }
        compose.onNodeWithText("History").performClick().assertIsSelected()
        compose.onNodeWithText("Session").performClick().assertIsSelected()
        compose.onNodeWithText("ECG").assertIsSelected()
        singleScreen()
    }

    @Test fun emptyPausedAndFailedStatesKeepWholePageAndFullErrorDetails() {
        mount()
        compose.onNodeWithContentDescription("Pause").performClick()
        capture("paused"); singleScreen()
        for (label in listOf("Motion", "ECG")) {
            compose.onNodeWithText(label).performClick()
            compose.onNodeWithText("Paused · chart frozen").assertDoesNotExist()
            capture("paused-$label"); singleScreen()
        }
        compose.onNodeWithText("HR").performClick()
        compose.onNodeWithContentDescription("Continue").assertIsEnabled().performClick()
        assertEquals(1, pauses); assertEquals(1, resumes)
        compose.runOnIdle { empty.value = true; phase.value = SessionStatus.IDLE }
        compose.onNodeWithContentDescription("Session details").assertDoesNotExist()
        compose.onNodeWithText("Not started").assertDoesNotExist()
        capture("empty"); singleScreen()
        compose.runOnIdle { failed.value = true; empty.value = false; phase.value = SessionStatus.RUNNING }
        capture("error"); singleScreen()
        compose.onNodeWithContentDescription("Open Devices").performClick()
        compose.onNodeWithText("HR: " + "Controlled stream failure with full recovery information. ".repeat(10), substring = true).performScrollTo().assertIsDisplayed()
        compose.onAllNodesWithText("Retry", substring = true).assertCountEquals(0)
        capture("error-details")
        compose.onNodeWithText("Close").performClick()
        singleScreen()
        compose.onNodeWithContentDescription("Stop").performClick()
        assertEquals(1, stops)
        compose.onNodeWithContentDescription("Start").assertIsEnabled()
        compose.onNodeWithText("HR").assertIsSelected()
        compose.onNodeWithText("Mean HR: -- bpm").assertIsDisplayed()
        compose.onNodeWithText("Mean: -- steps/min").assertIsDisplayed()
        compose.onNodeWithText("Activity Summary").assertDoesNotExist()
        capture("after-stop"); singleScreen()
    }

    @Test fun captureActualSystemFontLimits() {
        mount(systemFont = true)
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            capture("system-${if (night) "dark" else "light"}")
        }
        // Diagnostic captures do not assert that oversized configurations fit.
        compose.onAllNodes(SemanticsMatcher.keyIsDefined(androidx.compose.ui.semantics.SemanticsProperties.VerticalScrollAxisRange)).assertCountEquals(0)
        compose.onNodeWithContentDescription("Pause").assertIsDisplayed()
    }
}
