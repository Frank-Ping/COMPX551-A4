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
import androidx.compose.ui.unit.height
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
    private val mean = mutableStateOf<Double?>(null)
    private val maximum = mutableStateOf<Double?>(null)
    private var retries = 0
    private val paused = mutableStateOf(false)

    @Composable private fun Card() {
        val kind by charts.selection.collectAsState()
        LiveChartCard(kind,
            ChartSnapshot(points.value, end.value, if (kind == ChartKind.ELECTROCARDIOGRAM) 5000.0 else 300_000.0, status.value),
            mean.value, maximum.value,
            readiness.value, charts::select, paused.value)
    }
    private fun mount(session: Boolean = false, connected: Boolean = false) = compose.setContent {
        val density = LocalDensity.current
        CompositionLocalProvider(LocalDensity provides (font.value?.let { Density(density.density, it) } ?: density)) {
            PolarH10ActivityViewerTheme(darkTheme = dark.value) {
                Scaffold { padding ->
                    if (session) Column(Modifier.padding(padding)) {
                        Text("Controlled UI fixture — no H10 data")
                        SessionScreen(availability = BluetoothAvailability.READY, actionEnabled = true,
                            errorMessage = null, onBluetoothAction = {}, scanState = ScanState(),
                            onStartScan = {}, onStopScan = {}, connectionState = if (connected)
                                ConnectionState(ConnectionStatus.CONNECTED, ConnectionDevice("Test H10", "test")) else ConnectionState(),
                            onConnect = {}, savedDevicesState = SavedDevicesState(loading = false),
                            onDisconnect = {}, onRetryDisconnect = {}, dataReadiness = readiness.value,
                            onRecheckData = {}, ecgSubscription = ecg.value, charts = { Card() })
                    } else Column(Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(16.dp)) {
                        Text("Controlled UI fixture — no H10 data")
                        Card()
                    }
                }
            }
        }
    }
    private fun visible(text: String) = compose.onNodeWithText(text).reveal().assertIsDisplayed()
    private fun click(text: String) = compose.onNodeWithText(text).reveal().performClick()
    private fun noOverflow(text: String) {
        val results = mutableListOf<TextLayoutResult>()
        compose.onNodeWithText(text).reveal().performSemanticsAction(SemanticsActions.GetTextLayoutResult) {
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
        compose.mainClock.advanceTimeBy(500)
        compose.waitForIdle()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.getExternalFilesDir(null), "step84c-controlled-visual").apply { mkdirs() }
        File(directory, "$name.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    @Test fun denseCadenceDisplayKeepsStatisticsAndRenamedTabReadable() {
        points.value = (0..1200).map { i -> ChartPoint(i * 250.0,
            110.0 + 25.0 * kotlin.math.sin(i / 80.0) + if (i % 2 == 0) -12.0 else 12.0, i == 0) }
        end.value = 300_000.0
        mean.value = 110.0; maximum.value = 147.0
        mount(); click("Cadence")
        compose.onNodeWithText("Cadence").assertIsSelected()
        compose.onNodeWithText("Motion").assertDoesNotExist()
        for (scale in listOf(1f, 2f)) for (night in listOf(false, true)) {
            compose.runOnIdle { font.value = scale; dark.value = night }
            noOverflow("Cadence")
            noOverflow("Mean: 110 steps/min"); noOverflow("Max: 147 steps/min")
            compose.onNodeWithTag("live-chart-plot").reveal()
            capture("dense-cadence-$scale-${if (night) "dark" else "light"}")
        }
    }

    @Test fun cadenceTabSelectsCadenceWithoutSpeedSubchoices() {
        mount(); compose.onNodeWithText("HR").assertIsSelected()
        click("Cadence"); compose.onNodeWithText("Cadence").assertIsSelected()
        assertEquals(ChartKind.CADENCE, charts.selection.value)
        compose.onAllNodesWithText("Speed", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("km/h", substring = true).assertCountEquals(0)
        click("HR"); click("Cadence")
        assertEquals(ChartKind.CADENCE, charts.selection.value)
        click("ECG"); visible("µV")
    }
    @Test fun sessionChartKeepsFormalDisplaysInTheMetricsLayout() {
        mount(session = true); visible("Duration"); visible("HR")
        compose.onNodeWithTag("live-chart-plot").reveal(); visible("01:00")
        compose.onAllNodesWithText("development check", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Buffer:", substring = true).assertCountEquals(0)
    }
    @Test fun plotHasWideReferenceAspectAndAxisIsNotLabeledAsStatistics() {
        mount(); val bounds = compose.onNodeWithTag("live-chart-plot").reveal().fetchSemanticsNode().boundsInRoot
        assertTrue("The normal reference plot should be wider than tall", bounds.width > bounds.height * 2.5f)
        visible("00:00"); visible("00:30"); visible("01:00")
        compose.onAllNodesWithText("Min ", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Max 121", substring = true).assertCountEquals(0)
    }
    @Test fun emptyAndGenuineZeroAreDistinct() {
        points.value = emptyList(); mount(); compose.onNodeWithTag("live-chart-plot").reveal()
        visible("Waiting for valid data"); compose.onNodeWithContentDescription("Heart rate line chart: no valid data").assertExists()
        compose.runOnIdle { points.value = listOf(ChartPoint(30_000.0, 0.0, true)) }
        compose.onNodeWithText("Live").assertDoesNotExist(); compose.onNodeWithTag("live-chart-plot").reveal()
        compose.onAllNodesWithText("No valid chart data in this window").assertCountEquals(0)
    }
    @Test fun shortWindowCollapsesDuplicateSecondLabels() {
        end.value = 400.0; points.value = emptyList(); mount()
        compose.onAllNodesWithText("00:00").assertCountEquals(1)
        compose.runOnIdle { end.value = 1100.0 }
        visible("00:00"); visible("00:01")
    }
    @Test fun ecgFailureDetailsRemainReachableWithoutCardRetry() {
        points.value = listOf(ChartPoint(58_000.0, -100.0, true), ChartPoint(59_000.0, 200.0, false))
        ecg.value = SubscriptionState(SubscriptionStatus.FAILED, "Controlled ECG failure.")
        mount(session = true)
        compose.onAllNodesWithText("Retry ECG").assertCountEquals(0)
        compose.onNodeWithContentDescription("Open Devices").performClick()
        visible("ECG: Controlled ECG failure.")
        compose.onNodeWithText("Close").performClick()
        click("ECG"); compose.runOnIdle { status.value = SubscriptionStatus.FAILED }
        compose.onNodeWithText("Failed · chart frozen").assertDoesNotExist()
        compose.onNodeWithTag("live-chart-plot").assertIsDisplayed()
    }

    @Test fun configurationErrorsRemainAfterRawPanelsAreRemoved() {
        readiness.value = mapOf(PolarDeviceDataType.ACC to DataReadiness(error = "Controlled configuration error."),
            PolarDeviceDataType.ECG to DataReadiness(DataReadinessStatus.READY, configurationComplete = false))
        mount(session = true, connected = true); compose.onNodeWithContentDescription("Open Devices").performClick()
        visible("Controlled configuration error.")
        visible("Recheck")
    }
    @Test fun wholeSessionStatisticsAndDashedMeanDoNotExpandWindowScale() {
        mean.value = 85.0; maximum.value = 300.0; mount()
        visible("Average HR: 85 bpm"); visible("Max HR: 300 bpm")
        compose.onNodeWithTag("live-chart-plot").reveal();
        fun meanPixels(): Int {
            val plot = compose.onNodeWithTag("live-chart-plot").reveal().captureToImage().asAndroidBitmap()
            return (0 until plot.width).sumOf { x -> (0 until plot.height).count { y ->
                plot.getPixel(x, y) == android.graphics.Color.rgb(245, 158, 11)
            } }
        }
        assertTrue(meanPixels() > 100)
        compose.runOnIdle { mean.value = 300.0 }
        visible("Average HR: 300 bpm"); compose.onNodeWithTag("live-chart-plot").reveal()
        assertEquals(0, meanPixels())
        compose.runOnIdle { mean.value = null; maximum.value = null }
        visible("Average HR: -- bpm"); visible("Max HR: -- bpm")
        assertEquals(0, meanPixels())
    }
    @Test fun ecgMetadataUsesSelectedRateAndActualWindowCountIncludingEmpty() {
        charts.select(ChartKind.ELECTROCARDIOGRAM)
        points.value = listOf(ChartPoint(58_000.0, -10.0, true), ChartPoint(59_000.0, 20.0, false))
        readiness.value = mapOf(PolarDeviceDataType.ECG to DataReadiness(
            selected = mapOf(com.polar.sdk.api.model.PolarSensorSetting.SettingType.SAMPLE_RATE to 200)))
        mount(); visible("Sampling Rate: 200 Hz"); visible("Samples: 2")
        compose.runOnIdle { points.value = emptyList(); readiness.value = emptyMap() }
        visible("Sampling Rate: --"); visible("Samples: 0")
        compose.runOnIdle { status.value = SubscriptionStatus.STARTING }
        visible("Samples: --"); compose.onNodeWithText("Waiting for data").assertDoesNotExist()
    }
    @Test fun longElapsedLabelsKeepTheirFontAndReduceTickCount() {
        end.value = 14_400_000.0; font.value = 2f; mount()
        noOverflow("HR")
        noOverflow("235:00"); noOverflow("240:00")
        val nodes = compose.onAllNodes(hasText("235:00") or hasText("237:30") or hasText("240:00")).fetchSemanticsNodes()
        val boxes = nodes.map { it.boundsInRoot }.sortedBy { it.left }
        boxes.zipWithNext().forEach { (a, b) -> assertTrue(a.right <= b.left) }
        capture("long-running-font2")
    }
    @Test fun idleWaitingAndStoppedStatusesAreNotMisreportedAsLive() {
        points.value = emptyList(); status.value = SubscriptionStatus.IDLE; mount()
        val cardHeight = compose.onNodeWithTag("live-chart-card").getUnclippedBoundsInRoot().height
        val plotHeight = compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot().height
        fun sameSize() {
            assertEquals(cardHeight, compose.onNodeWithTag("live-chart-card").getUnclippedBoundsInRoot().height)
            assertEquals(plotHeight, compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot().height)
        }
        compose.onNodeWithText("Not started").assertDoesNotExist()
        compose.onNodeWithText("Live").assertDoesNotExist()
        compose.runOnIdle { status.value = SubscriptionStatus.STARTING }; visible("Waiting for data"); sameSize()
        compose.runOnIdle { status.value = SubscriptionStatus.RECEIVING }; visible("Waiting for valid data"); sameSize()
        compose.runOnIdle { status.value = SubscriptionStatus.STOPPING }; visible("Stopping · chart frozen"); sameSize()
        compose.runOnIdle { status.value = SubscriptionStatus.STOPPED }; visible("Stopped · chart frozen"); sameSize()
        compose.runOnIdle { status.value = SubscriptionStatus.FAILED }; visible("Failed · chart frozen"); sameSize()
        compose.runOnIdle { paused.value = true }; visible("Paused · chart frozen"); sameSize()
        compose.onNodeWithText("Stopped · chart frozen").assertDoesNotExist()
        for (label in listOf("Cadence", "ECG")) {
            click(label)
            SubscriptionStatus.entries.forEach { state ->
                compose.runOnIdle { status.value = state; paused.value = false }
                sameSize()
                listOf("Not started", "Waiting for data", "Waiting for valid data", "Stopping · chart frozen",
                    "Stopped · chart frozen", "Failed · chart frozen").forEach {
                    compose.onNodeWithText(it).assertDoesNotExist()
                }
            }
            compose.runOnIdle { paused.value = true }
            sameSize()
            compose.onNodeWithText("Paused · chart frozen").assertDoesNotExist()
        }
    }
    @Test fun isolatedNegativeEcgSampleRemainsVisibleWithoutAConnectingLine() {
        mean.value = 0.0 // ECG must never show a session reference line, even if one is supplied.
        points.value = listOf(ChartPoint(58_000.0, -100.0, true)); charts.select(ChartKind.ELECTROCARDIOGRAM)
        mount(); compose.onNodeWithTag("live-chart-plot").reveal()
        val plot = compose.onNodeWithTag("live-chart-plot").reveal().captureToImage().asAndroidBitmap()
        var inkCount = 0
        for (x in 0 until plot.width) for (y in 0 until plot.height) {
            if (plot.getPixel(x, y) == android.graphics.Color.rgb(0, 85, 255)) inkCount++
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
            val plot = compose.onNodeWithTag("live-chart-plot").reveal().captureToImage().asAndroidBitmap()
            fun inkAt(x: Int): Int = (0 until plot.height).count { y -> plot.getPixel(x, y) == android.graphics.Color.rgb(239, 68, 68) }
            assertTrue(inkAt(plot.width / 5) > 0)
            assertEquals(0, inkAt(plot.width / 2))
            assertTrue(inkAt(plot.width * 4 / 5) > 0)
            // The lower filled area must also end before the missing interval.
            val y = plot.height * 3 / 4
            val leftFill = android.graphics.Color.red(plot.getPixel(plot.width / 5, y)) -
                android.graphics.Color.green(plot.getPixel(plot.width / 5, y))
            val gapFill = android.graphics.Color.red(plot.getPixel(plot.width / 2, y)) -
                android.graphics.Color.green(plot.getPixel(plot.width / 2, y))
            assertTrue(leftFill > gapFill + 10)
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
            compose.runOnIdle { dark.value = night; mean.value = 85.0; maximum.value = 145.0 }
            visible("HR"); noOverflow("HR")
            noOverflow("bpm")
            compose.onNodeWithTag("live-chart-plot").reveal(); capture("$prefix-${if(night) "dark" else "light"}-hr")
            noOverflow("Average HR: 85 bpm"); noOverflow("Max HR: 145 bpm")
            click("Cadence")
            compose.runOnIdle {
                mean.value = 96.0; maximum.value = 144.0
                points.value = listOf(ChartPoint(10_000.0, 108.0, true), ChartPoint(20_000.0, 0.0, false),
                    ChartPoint(40_000.0, 120.0, true))
            }
            noOverflow("steps/min"); noOverflow("Mean: 96 steps/min")
            compose.onNodeWithTag("live-chart-plot").reveal(); capture("$prefix-${if(night) "dark" else "light"}-cadence")
            click("ECG")
            compose.runOnIdle {
                points.value = (0 until 650).map { i -> ChartPoint(55_001.0 + i * 7.69, kotlin.math.sin(i / 10.0) * 200, i == 0) }
                ecg.value = SubscriptionState(SubscriptionStatus.FAILED,
                    "Controlled long ECG recovery message: check the chest strap and Devices, reconnect manually if required, then retry ECG.")
                status.value = SubscriptionStatus.FAILED
                readiness.value = readiness.value + (PolarDeviceDataType.ECG to DataReadiness(
                    selected = mapOf(com.polar.sdk.api.model.PolarSensorSetting.SettingType.SAMPLE_RATE to 130)))
            }
            noOverflow("Sampling Rate: 130 Hz"); noOverflow("Samples: 650")
            compose.onAllNodesWithText("Retry ECG").assertCountEquals(0)
            compose.onNodeWithTag("live-chart-plot").reveal(); capture("$prefix-${if(night) "dark" else "light"}-ecg")
            click("HR")
            compose.runOnIdle {
                points.value = listOf(ChartPoint(10_000.0, 80.0, true), ChartPoint(20_000.0, 100.0, false), ChartPoint(40_000.0, 110.0, true))
                ecg.value = SubscriptionState(SubscriptionStatus.RECEIVING); status.value = SubscriptionStatus.RECEIVING
            }
        }
    }
}

private fun SemanticsNodeInteraction.reveal(): SemanticsNodeInteraction {
    var ancestor = fetchSemanticsNode().parent
    while (ancestor != null) {
        if (ancestor.config.contains(SemanticsActions.ScrollBy)) return performScrollTo()
        ancestor = ancestor.parent
    }
    return this
}
