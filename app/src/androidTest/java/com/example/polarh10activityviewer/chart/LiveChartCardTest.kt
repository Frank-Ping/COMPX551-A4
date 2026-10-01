package com.example.polarh10activityviewer.chart

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.*
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.session.*
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File

// Explicit controlled fixtures; these tests do not acquire H10 signals.
class LiveChartCardTest {
    @get:Rule val compose = createComposeRule()
    private val charts = LiveCharts { emptyList() }
    private val points = mutableStateOf(listOf(ChartPoint(10_000.0, 80.0, true),
        ChartPoint(20_000.0, 100.0, false), ChartPoint(40_000.0, 110.0, true)))
    private val end = mutableStateOf(60_000.0)
    private val status = mutableStateOf(SubscriptionStatus.RECEIVING)
    private val ecg = mutableStateOf(SubscriptionState(SubscriptionStatus.RECEIVING))
    private val readiness = mutableStateOf(checkedDataTypes.associateWith { DataReadiness() })
    private val retryAllowed = mutableStateOf(true)
    private val dark = mutableStateOf(false)
    private val font = mutableStateOf<Float?>(1f)
    private var retries = 0

    @Composable private fun Card(canRetry: Boolean = retryAllowed.value, retry: () -> Unit = { retries++ }) {
        val kind by charts.selection.collectAsState()
        LiveChartCard(kind, charts.motionSelection,
            ChartSnapshot(points.value, end.value, if (kind == ChartKind.ELECTROCARDIOGRAM) 5000.0 else 60_000.0, status.value),
            checkedDataTypes.associateWith { if (it == PolarDeviceDataType.ECG) ecg.value else SubscriptionState() },
            readiness.value, canRetry, charts::select, retry)
    }
    private fun mount(session: Boolean = false) = compose.setContent {
        val density = LocalDensity.current
        CompositionLocalProvider(LocalDensity provides (font.value?.let { Density(density.density, it) } ?: density)) {
            PolarH10ActivityViewerTheme(darkTheme = dark.value) {
                Scaffold { padding ->
                    if (session) Column(Modifier.padding(padding)) {
                        Text("Controlled UI fixture — no H10 data")
                        SessionScreen(availability = BluetoothAvailability.READY, actionEnabled = true,
                            errorMessage = null, onBluetoothAction = {}, scanState = ScanState(),
                            onStartScan = {}, onStopScan = {}, connectionState = ConnectionState(),
                            onConnect = {}, savedDevicesState = SavedDevicesState(loading = false),
                            onDisconnect = {}, onRetryDisconnect = {}, dataReadiness = readiness.value,
                            onRecheckData = {}, charts = { allowed, retry -> Card(allowed, retry) })
                    } else Column(Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp)) {
                        Text("Controlled UI fixture — no H10 data")
                        Card()
                    }
                }
            }
        }
    }
    private fun visible(text: String) = compose.onNodeWithText(text).performScrollTo().assertIsDisplayed()
    private fun click(text: String) = compose.onNodeWithText(text).performScrollTo().performClick()
    private fun noOverflow(text: String) {
        val results = mutableListOf<TextLayoutResult>()
        compose.onNodeWithText(text).performScrollTo().performSemanticsAction(SemanticsActions.GetTextLayoutResult) {
            assertTrue(it(results))
        }
        val result = results.single()
        assertFalse(result.multiParagraph.didExceedMaxLines)
        for (line in 0 until result.lineCount) {
            assertTrue(result.getLineRight(line) <= result.size.width + 1)
            assertTrue(result.getLineLeft(line) >= -1)
            assertTrue(result.getLineBottom(line) <= result.size.height + 1)
        }
    }
    private fun capture(name: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.getExternalFilesDir(null), "step83-controlled-visual").apply { mkdirs() }
        File(directory, "$name.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    @Test fun selectionRemembersMotionAndShowsOneUnitAtATime() {
        mount(); compose.onNodeWithText("HR").assertIsSelected()
        click("Motion"); click("Speed")
        visible("Estimated speed (km/h)"); compose.onNodeWithText("Speed").assertIsSelected()
        click("HR"); click("Motion"); compose.onNodeWithText("Speed").assertIsSelected()
        click("ECG"); visible("ECG (µV)"); compose.onAllNodesWithText("Speed").assertCountEquals(0)
        assertEquals(ChartKind.SPEED, charts.motionSelection)
    }
    @Test fun sessionChartIsLastAndHasOnlyFormalDisplays() {
        mount(session = true); visible("Activity summary"); visible("Live charts")
        visible("Scale (bpm): 0 to 121"); visible("01:00")
        compose.onAllNodesWithText("development check", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Buffer:", substring = true).assertCountEquals(0)
    }
    @Test fun plotHeightIs220DpAndAxisIsNotLabeledAsStatistics() {
        mount(); compose.onNodeWithTag("live-chart-plot").performScrollTo().assertHeightIsEqualTo(220.dp)
        visible("00:00"); visible("00:30"); visible("01:00")
        compose.onAllNodesWithText("Min ", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Max ", substring = true).assertCountEquals(0)
    }
    @Test fun emptyAndGenuineZeroAreDistinct() {
        points.value = emptyList(); mount(); visible("Scale (bpm): -- to --")
        visible("Waiting for valid data"); visible("No valid chart data in this window")
        compose.runOnIdle { points.value = listOf(ChartPoint(30_000.0, 0.0, true)) }
        visible("Live"); visible("Scale (bpm): 0 to 1")
        compose.onAllNodesWithText("No valid chart data in this window").assertCountEquals(0)
    }
    @Test fun shortWindowCollapsesDuplicateSecondLabels() {
        end.value = 400.0; points.value = emptyList(); mount()
        compose.onAllNodesWithText("00:00").assertCountEquals(1)
        compose.runOnIdle { end.value = 1100.0 }
        visible("00:00"); visible("00:01")
    }
    @Test fun ecgNegativeScaleAndFrozenStatusKeepRecoveryAccessibleFromHr() {
        points.value = listOf(ChartPoint(58_000.0, -100.0, true), ChartPoint(59_000.0, 200.0, false))
        ecg.value = SubscriptionState(SubscriptionStatus.FAILED, "Controlled ECG failure. Open Devices and retry.")
        mount(); visible("ECG: ${ecg.value.error}"); click("Retry ECG"); assertEquals(1, retries)
        click("ECG"); compose.runOnIdle { status.value = SubscriptionStatus.FAILED }
        visible("Failed · chart frozen"); visible("Scale (µV): -130 to 230")
        compose.runOnIdle { retryAllowed.value = false }
        compose.onNodeWithText("Retry ECG").performScrollTo().assertIsNotEnabled()
    }
    @Test fun configurationErrorsRemainAfterRawPanelsAreRemoved() {
        readiness.value = mapOf(PolarDeviceDataType.ACC to DataReadiness(error = "Controlled configuration error."),
            PolarDeviceDataType.ECG to DataReadiness(DataReadinessStatus.READY, configurationComplete = false))
        mount(); visible("ACC: Controlled configuration error.")
        visible("Open Devices to confirm ECG configuration before starting.")
    }
    @Test fun idleWaitingAndStoppedStatusesAreNotMisreportedAsLive() {
        points.value = emptyList(); status.value = SubscriptionStatus.IDLE; mount(); visible("Not started")
        compose.runOnIdle { status.value = SubscriptionStatus.STARTING }; visible("Waiting for data")
        compose.runOnIdle { status.value = SubscriptionStatus.RECEIVING }; visible("Waiting for valid data")
        compose.runOnIdle { status.value = SubscriptionStatus.STOPPING }; visible("Stopping · chart frozen")
        compose.runOnIdle { status.value = SubscriptionStatus.STOPPED }; visible("Stopped · chart frozen")
    }
    @Test fun isolatedNegativeEcgSampleRemainsVisibleWithoutAConnectingLine() {
        points.value = listOf(ChartPoint(58_000.0, -100.0, true)); charts.select(ChartKind.ELECTROCARDIOGRAM)
        mount(); visible("Scale (µV): -110 to 10")
        val plot = compose.onNodeWithTag("live-chart-plot").performScrollTo().captureToImage().asAndroidBitmap()
        var inkCount = 0
        for (x in 0 until plot.width) for (y in 0 until plot.height) {
            if (plot.getPixel(x, y) == android.graphics.Color.rgb(37, 99, 235)) inkCount++
        }
        assertTrue(inkCount > 0)
        assertTrue("A single sample must not form a line", inkCount < 200)
    }
    @Test fun renderedSegmentsDoNotBridgeNullsOrExplicitBreaks() {
        mount()
        for (hasNull in listOf(false, true)) {
            compose.runOnIdle {
                points.value = listOf(ChartPoint(6000.0, 80.0, true), ChartPoint(15_000.0, 80.0, false)) +
                    (if (hasNull) listOf(ChartPoint(30_000.0, null, false)) else emptyList()) +
                    listOf(ChartPoint(45_000.0, 80.0, true), ChartPoint(54_000.0, 80.0, false))
            }
            val plot = compose.onNodeWithTag("live-chart-plot").performScrollTo().captureToImage().asAndroidBitmap()
            fun inkAt(x: Int): Int = (0 until plot.height).count { y -> plot.getPixel(x, y) == android.graphics.Color.rgb(37, 99, 235) }
            assertTrue(inkAt(plot.width / 5) > 0)
            assertEquals(0, inkAt(plot.width / 2))
            assertTrue(inkAt(plot.width * 4 / 5) > 0)
        }
    }
    @Test fun enlargedFontsAndBothThemesKeepLabelsErrorsAndButtonsReadable() {
        font.value = 2f; mount(); checkVisuals("controlled-font2")
    }
    @Test fun actualSystemFontsKeepAxesAndAllChoicesScrollable() {
        font.value = null; mount(); checkVisuals("system-font")
    }
    private fun checkVisuals(prefix: String) {
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            visible("Live charts"); noOverflow("Window: 60 s · elapsed since Running (mm:ss)")
            noOverflow("Scale (bpm): 0 to 121")
            compose.onNodeWithTag("live-chart-plot").performScrollTo(); capture("$prefix-${if(night) "dark" else "light"}-hr")
            click("Motion"); click("Speed"); noOverflow("Estimated speed (km/h)")
            click("ECG")
            compose.runOnIdle {
                points.value = (0 until 650).map { i -> ChartPoint(55_001.0 + i * 7.69, kotlin.math.sin(i / 10.0) * 200, i == 0) }
                ecg.value = SubscriptionState(SubscriptionStatus.FAILED,
                    "Controlled long ECG recovery message: check the chest strap and Devices, reconnect manually if required, then retry ECG.")
                status.value = SubscriptionStatus.FAILED
            }
            noOverflow("ECG: ${ecg.value.error}"); visible("Retry ECG")
            noOverflow("Approximate time alignment includes transmission delay. All visible ECG samples are drawn.")
            compose.onNodeWithTag("live-chart-plot").performScrollTo(); capture("$prefix-${if(night) "dark" else "light"}-ecg")
            click("HR")
            compose.runOnIdle {
                points.value = listOf(ChartPoint(10_000.0, 80.0, true), ChartPoint(20_000.0, 100.0, false), ChartPoint(40_000.0, 110.0, true))
                ecg.value = SubscriptionState(SubscriptionStatus.RECEIVING); status.value = SubscriptionStatus.RECEIVING
            }
        }
    }
}
