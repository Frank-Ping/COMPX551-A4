package com.example.polarh10activityviewer.session

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.SessionScreen
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.heartrate.HeartRateZone
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.storage.databaseFixture
import com.example.polarh10activityviewer.storage.SessionDatabase
import com.example.polarh10activityviewer.history.HistoryPanel
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.util.UUID
import kotlinx.coroutines.runBlocking

// Controlled states validate rendering and callback routing, not H10 acquisition.
class SessionMetricsTest {
    @get:Rule val compose = createComposeRule()
    private val reading = mutableStateOf<HeartRateReading?>(HeartRateReading(123, 1_700_000_000_000))
    private val statistics = mutableStateOf(HeartRateStatistics(3, 361, 110, 130))
    private val motion = mutableStateOf(StepState(totalSteps = 121, cadence = 123.6, speed = 1.25,
        distance = 100.04, maximumCadence = 168.0, minimumCadence = 0.0, maximumSpeed = 3.0,
        receivedAcc = true, durationMs = 60_000, message = "Controlled ACC observations."))
    private val zones = mutableStateOf(HeartRateZoneState(listOf(500, 1000, 1500, 2000, 2500),
        current = HeartRateZone.LIGHT, receivedValidHr = true, unclassifiedMs = 1300))
    private val session = mutableStateOf(SessionState(SessionStatus.RUNNING, elapsedMs = 60_000))
    private val connected = mutableStateOf(true)
    private val checking = mutableStateOf(false)
    private val hr = mutableStateOf(SubscriptionState(SubscriptionStatus.RECEIVING))
    private val acc = mutableStateOf(SubscriptionState(SubscriptionStatus.RECEIVING))
    private val message = mutableStateOf<String?>(null)
    private val dark = mutableStateOf(false)
    private val scale = mutableStateOf<Float?>(1f)
    private val retried = mutableListOf<PolarDeviceDataType>()

    @Composable private fun Fixture() {
        val density = LocalDensity.current
        val fixtureDensity = scale.value?.let { Density(density.density, it) } ?: density
        CompositionLocalProvider(LocalDensity provides fixtureDensity) {
            PolarH10ActivityViewerTheme(darkTheme = dark.value) {
                Scaffold { padding ->
                    Column(Modifier.padding(padding)) {
                        Text("Controlled UI fixture — no H10 data")
                        SessionScreen(availability = BluetoothAvailability.READY, actionEnabled = true,
                            errorMessage = null, onBluetoothAction = {}, scanState = ScanState(),
                            onStartScan = {}, onStopScan = {},
                            connectionState = if (connected.value) ConnectionState(ConnectionStatus.CONNECTED,
                                ConnectionDevice("Controlled device", "TEST1234")) else ConnectionState(),
                            onConnect = {}, savedDevicesState = SavedDevicesState(loading = false),
                            onDisconnect = {}, onRetryDisconnect = {},
                            dataReadiness = checkedDataTypes.associateWith {
                                DataReadiness(if (checking.value) DataReadinessStatus.CHECKING else DataReadinessStatus.READY,
                                    configurationComplete = true)
                            }, onRecheckData = {}, heartRate = reading.value, heartRateStatistics = statistics.value,
                            heartRateMessage = message.value, steps = motion.value, heartRateZones = zones.value,
                            session = session.value, hrSubscription = hr.value, accSubscription = acc.value,
                            ecgSubscription = SubscriptionState(SubscriptionStatus.RECEIVING),
                            onRetryStream = { retried += it },
                            charts = { _, _ -> Text("Controlled chart position") },
                            saveStatus = { Text("Controlled save recovery position") })
                    }
                }
            }
        }
    }
    private fun mount() = compose.setContent { Fixture() }
    private fun visible(text: String) = compose.onNodeWithText(text).performScrollTo().assertIsDisplayed()
    private fun screenshot(name: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.getExternalFilesDir(null), "step84b-controlled-visual").apply { mkdirs() }
        File(directory, "$name.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
    private fun noOverflow(text: String) {
        val results = mutableListOf<TextLayoutResult>()
        compose.onAllNodesWithText(text)[0].performScrollTo().performSemanticsAction(SemanticsActions.GetTextLayoutResult) {
            assertTrue(it(results))
        }
        val result = results.single()
        // A wrapping Text can retain a wider paragraph box than its measured content.
        // Check rendered line extents rather than that unused paragraph width.
        assertFalse("Truncated text: $text", result.multiParagraph.didExceedMaxLines)
        for (line in 0 until result.lineCount) {
            assertTrue("Right overflow: $text", result.getLineRight(line) <= result.size.width + 1)
            assertTrue("Left overflow: $text", result.getLineLeft(line) >= -1)
            assertTrue("Vertical overflow: $text", result.getLineBottom(line) <= result.size.height + 1)
        }
    }

    @Test fun validReadingsUseExistingStatisticsAndDisplayOnlyRounding() {
        mount()
        visible("123"); visible("Min HR: 110 bpm"); visible("Max HR: 130 bpm"); visible("Mean HR: 120.3 bpm")
        visible("124"); visible("4.5"); visible("Mean cadence: 121 steps/min"); visible("Min cadence: 0 steps/min")
        visible("Max cadence: 168 steps/min"); visible("Mean speed: 6.0 km/h"); visible("Max speed: 10.8 km/h")
        visible("01:00"); visible("121"); visible("100.0")
        assertEquals(123.6, motion.value.cadence!!, 0.0)
        assertEquals(100.04, motion.value.distance!!, 0.0)
    }

    @Test fun sessionOrdersChartSummaryZonesAndRecoveryAfterMetrics() {
        mount()
        val titles = listOf("Data streams", "Heart rate", "Motion", "Controlled chart position",
            "Activity summary", "Heart rate zones", "Session: Running", "Controlled save recovery position")
        visible("Data streams")
        val positions = titles.map { compose.onNodeWithText(it).fetchSemanticsNode().positionInRoot.y }
        assertTrue("Session sections out of order: $positions", positions.zipWithNext().all { (first, second) -> first < second })
        titles.forEach { visible(it) }
    }

    @Test fun staleZoneCannotShowValidIntensityWithoutAReadingDuringFailureOrAfterStop() {
        zones.value = zones.value.copy(current = HeartRateZone.MODERATE)
        mount(); visible("Moderate · Zone 3")
        compose.runOnIdle { reading.value = null }
        compose.onNodeWithText("Moderate · Zone 3").assertDoesNotExist()
        visible("Mean HR: 120.3 bpm")
        compose.runOnIdle { reading.value = HeartRateReading(130, 1_700_000_000_000)
            hr.value = SubscriptionState(SubscriptionStatus.FAILED) }
        compose.onNodeWithText("Moderate · Zone 3").assertDoesNotExist()
        visible("HR unavailable")
        compose.runOnIdle { hr.value = SubscriptionState(SubscriptionStatus.RECEIVING)
            session.value = session.value.copy(status = SessionStatus.STOPPED) }
        compose.onNodeWithText("Moderate · Zone 3").assertDoesNotExist()
        visible("Stopped"); visible("Min HR: 110 bpm")
        compose.onAllNodesWithText("Heart rate intensity").assertCountEquals(1)
    }

    @Test fun validZeroZonesUseEmptyHorizontalBarsAndNeverRunningPercentages() {
        zones.value = HeartRateZoneState(current = HeartRateZone.LIGHT, receivedValidHr = true)
        mount(); visible("All zones: 00:00")
        for (index in 1..5) {
            val bar = compose.onNodeWithContentDescription("Zone $index, cumulative duration 00:00")
            bar.performScrollTo()
            assertEquals(0f, bar.fetchSemanticsNode().boundsInRoot.width, 0f)
        }
        compose.onAllNodesWithText("No valid HR data").assertCountEquals(0)
        compose.onAllNodesWithText("%", substring = true).assertCountEquals(0)
        visible("Unclassified time: 00:00")
    }

    @Test fun normalMetricColumnsStackWhenFontScaleDoubles() {
        mount(); visible("123")
        val value = compose.onNodeWithText("123").fetchSemanticsNode().positionInRoot
        val stats = compose.onNodeWithText("Min HR: 110 bpm").fetchSemanticsNode().positionInRoot
        assertTrue("HR statistics should be to the right", stats.x > value.x)
        compose.runOnIdle { scale.value = 2f }
        visible("123")
        val largeValue = compose.onNodeWithText("123").fetchSemanticsNode().positionInRoot
        val largeStats = compose.onNodeWithText("Min HR: 110 bpm").fetchSemanticsNode().positionInRoot
        assertTrue("Large-font statistics should stack", largeStats.y > largeValue.y)
        assertEquals(largeValue.x, largeStats.x, 1f)
        noOverflow("Min HR: 110 bpm"); noOverflow("Mean cadence: 121 steps/min")
    }

    @Test fun unavailableReadingsRetainPlaceholdersRatherThanInventingZeros() {
        reading.value = null; statistics.value = HeartRateStatistics(); motion.value = StepState()
        zones.value = HeartRateZoneState(); mount()
        visible("Last received (phone): --"); visible("Min HR: -- bpm")
        visible("Mean cadence: -- steps/min"); visible("Mean speed: -- km/h")
        visible("No ACC observations. Statistics are unavailable.")
        compose.onAllNodesWithText("No valid HR data")[1].performScrollTo().assertIsDisplayed()
        visible("All zones: 00:00")
        compose.onAllNodesWithText("0").assertCountEquals(0)
        compose.onAllNodesWithText("0.0").assertCountEquals(0)
    }

    @Test fun initialWarmupShowsGenuineCurrentZerosWithItsStatus() {
        motion.value = StepState(cadence = 0.0, speed = 0.0, message = "Warming up ACC.")
        mount(); visible("0"); visible("0.0"); visible("Warming up ACC.")
        visible("Mean cadence: -- steps/min")
    }

    @Test fun invalidHrKeepsStatisticsAndRecoveryRouting() {
        reading.value = null; message.value = "No skin contact. Adjust the chest strap and retry if needed."
        hr.value = SubscriptionState(SubscriptionStatus.FAILED, "Controlled HR failure.")
        mount(); visible("Last received (phone): --"); visible("Mean HR: 120.3 bpm")
        visible(message.value!!); visible("Controlled HR failure.")
        compose.onNodeWithText("Retry HR").performScrollTo().performClick()
        assertEquals(listOf(PolarDeviceDataType.HR), retried)
    }

    @Test fun gapAndAccFailureKeepTotalsAndHaveOnlyOneAccRetry() {
        motion.value = motion.value.copy(cadence = null, speed = null, incompleteAcc = true,
            message = "ACC gap. Warming up a new continuous segment.")
        acc.value = SubscriptionState(SubscriptionStatus.FAILED, "Controlled ACC failure.")
        mount(); visible(motion.value.message); visible("121"); visible("100.0")
        compose.onAllNodesWithText("Retry ACC").assertCountEquals(1)
        compose.onNodeWithText("Retry ACC").performScrollTo().performClick()
        assertEquals(listOf(PolarDeviceDataType.ACC), retried)
        compose.onAllNodesWithText("Acceleration (development check)").assertCountEquals(0)
    }

    @Test fun checkingDisconnectionAndStoppedKeepOriginalRetryGuards() {
        hr.value = SubscriptionState(SubscriptionStatus.FAILED)
        acc.value = SubscriptionState(SubscriptionStatus.FAILED); checking.value = true; mount()
        compose.onNodeWithText("Retry HR").performScrollTo().assertIsEnabled()
        compose.onNodeWithText("Retry ACC").performScrollTo().assertIsNotEnabled()
        compose.runOnIdle { checking.value = false; connected.value = false }
        compose.onNodeWithText("Retry HR").performScrollTo().assertIsNotEnabled()
        compose.onNodeWithText("Retry ACC").performScrollTo().assertIsNotEnabled()
        compose.runOnIdle { connected.value = true; session.value = session.value.copy(status = SessionStatus.STOPPED) }
        compose.onNodeWithText("Retry HR").performScrollTo().assertIsNotEnabled()
        compose.onNodeWithText("Retry ACC").performScrollTo().assertIsNotEnabled()
    }

    @Test fun stoppedObservedZerosAndNewSessionStateAreRenderedWithoutCaching() {
        reading.value = null; session.value = session.value.copy(status = SessionStatus.STOPPED)
        motion.value = motion.value.copy(cadence = 0.0, speed = 0.0, message = "Stopped.")
        mount(); visible("0"); visible("0.0"); visible("Mean HR: 120.3 bpm"); visible("121")
        compose.runOnIdle { motion.value = StepState(); statistics.value = HeartRateStatistics()
            zones.value = HeartRateZoneState(); session.value = SessionState(SessionStatus.STARTING) }
        visible("Mean HR: -- bpm"); visible("All zones: 00:00")
        compose.onAllNodesWithText("121").assertCountEquals(0)
        compose.onAllNodesWithText("0").assertCountEquals(0)
    }

    @Test fun zeroAndSubsecondDurationsUseActualWidthsAndDynamicSharedScale() {
        zones.value = HeartRateZoneState(listOf(0, 500, 1000, 0, 0), receivedValidHr = true)
        mount()
        fun bar(zone: Int, duration: String) = compose.onNodeWithContentDescription("Zone $zone, cumulative duration $duration")
        bar(3, "00:01").performScrollTo()
        val maximum = bar(3, "00:01").fetchSemanticsNode().boundsInRoot.width
        assertEquals(maximum / 2, bar(2, "00:00").fetchSemanticsNode().boundsInRoot.width, 1f)
        assertEquals(0f, bar(1, "00:00").fetchSemanticsNode().boundsInRoot.width, 0f)
        compose.runOnIdle { zones.value = zones.value.copy(durationsMs = listOf(0, 500, 2000, 0, 0)) }
        bar(3, "00:02").performScrollTo()
        assertEquals(maximum / 4, bar(2, "00:00").fetchSemanticsNode().boundsInRoot.width, 1f)
        compose.runOnIdle { zones.value = zones.value.copy(durationsMs = listOf(3_000_000, 6_000_000, 0, 0, 0)) }
        bar(2, "100:00").performScrollTo()
        val longMaximum = bar(2, "100:00").fetchSemanticsNode().boundsInRoot.width
        assertEquals(longMaximum / 2, bar(1, "50:00").fetchSemanticsNode().boundsInRoot.width, 1f)
        visible("Unclassified time: 00:00")
    }

    @Test fun fiveZoneBarsRenderTheExactPaletteInBothThemes() {
        mount()
        val expected = listOf(0xFF22C55E.toInt(), 0xFF3B82F6.toInt(), 0xFFEAB308.toInt(),
            0xFFF97316.toInt(), 0xFFEF4444.toInt())
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            for (index in 0..4) {
                val duration = if (index < 1) "00:00" else if (index < 3) "00:01" else "00:02"
                val node = compose.onNodeWithContentDescription("Zone ${index + 1}, cumulative duration $duration")
                node.performScrollTo()
                val bitmap = node.captureToImage().asAndroidBitmap()
                assertEquals(expected[index], bitmap.getPixel(bitmap.width / 2, bitmap.height / 2))
            }
            screenshot(if (night) "dark-zone-bars" else "light-zone-bars")
        }
        visible("Zone 3 · Moderate"); visible("125–139 bpm")
        visible("Zone 5 · Very high"); visible("≥155 bpm")
        visible("Unclassified time: 00:01")
    }

    @Test fun enlargedFontsKeepLongValuesUnitsErrorsAndRecoveryVisible() {
        scale.value = 2f; session.value = session.value.copy(elapsedMs = 14_400_000)
        motion.value = motion.value.copy(totalSteps = 123456, distance = 65432.14,
            message = "Controlled recovery status with a long explanation: the current segment is warming up after a known ACC gap.")
        acc.value = SubscriptionState(SubscriptionStatus.FAILED,
            "Controlled error with recovery instructions: check Devices, reconnect manually if required, then retry the ACC stream.")
        mount()
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            noOverflow("123"); visible("bpm"); screenshot("${if (night) "dark" else "light"}-font2-hr")
            noOverflow("124"); visible("steps/min"); visible("4.5"); visible("km/h")
            screenshot("${if (night) "dark" else "light"}-font2-motion")
            noOverflow(motion.value.message); noOverflow(acc.value.error!!)
            compose.onNodeWithText("Retry ACC").performScrollTo().assertIsDisplayed()
            screenshot("${if (night) "dark" else "light"}-font2-recovery")
            noOverflow("240:00"); noOverflow("123456"); noOverflow("65432.1"); visible("m")
            screenshot("${if (night) "dark" else "light"}-font2-summary")
            noOverflow("Zone 5 · Very high"); noOverflow("≥155 bpm")
            screenshot("${if (night) "dark" else "light"}-font2-zone-details")
        }
    }

    @Test fun unnecessarySessionDevelopmentDisplaysAreRemoved() {
        session.value = session.value.copy(record = databaseFixture("controlled-metadata", 1000).record)
        mount()
        compose.onAllNodesWithText("Session ID:", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("development check", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Eligible for saving:", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Steps (development check)").assertCountEquals(0)
        compose.onAllNodesWithText("Heart rate (development check)").assertCountEquals(0)
        compose.onAllNodesWithText("Running duration: 2500 ms").assertCountEquals(0)
        compose.onAllNodesWithText("HR min / max / mean:", substring = true).assertCountEquals(0)
        compose.onAllNodesWithText("Total steps:", substring = true).assertCountEquals(0)
    }

    @Test fun actualSystemFontAndLandscapeKeepAllCardsScrollableInOrder() {
        scale.value = null; mount()
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            for (title in listOf("Heart rate", "Motion", "Activity summary", "Heart rate zones")) visible(title)
            val prefix = "system-${if (night) "dark" else "light"}"
            noOverflow("123"); screenshot("$prefix-Heart-rate")
            noOverflow("124"); visible("steps/min")
            noOverflow("4.5"); visible("km/h"); screenshot("$prefix-Motion")
            noOverflow("100.0"); visible("m"); screenshot("$prefix-Activity-summary")
            visible("Unclassified time: 00:01"); screenshot("$prefix-Heart-rate-zones")
        }
    }

    @Test fun historyKeepsStoredSummaryAndPercentagesWithSharedZoneVisuals() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val name = "step82-history-${UUID.randomUUID()}.db"
        val db = SessionDatabase(context, name)
        try {
            val fixture = databaseFixture("controlled-history", 1000)
            runBlocking { db.save(fixture) }
            compose.setContent {
                val density = LocalDensity.current
                CompositionLocalProvider(LocalDensity provides Density(density.density, 2f)) {
                    PolarH10ActivityViewerTheme(darkTheme = dark.value) {
                        Scaffold { padding ->
                            Column(Modifier.padding(padding)) {
                                Text("Controlled SQLite fixture — no H10 data")
                                HistoryPanel(db, null, {})
                            }
                        }
                    }
                }
            }
            compose.waitUntil(10_000) { compose.onAllNodesWithText("Estimated distance:", substring = true)
                .fetchSemanticsNodes().isNotEmpty() }
            compose.onNodeWithText("Estimated distance:", substring = true).performScrollTo().performClick()
            compose.waitUntil(10_000) { compose.onAllNodesWithText("Delete session").fetchSemanticsNodes().isNotEmpty() }
            visible("Session ID: controlled-history")
            visible("HR min / max / mean: 80 / 140 / 110.12 bpm")
            visible("Running duration: 2500 ms")
            for (night in listOf(false, true)) {
                compose.runOnIdle { dark.value = night }
                visible("Heart rate zones"); screenshot("history-${if (night) "dark" else "light"}-font2-zones")
                val maximum = fixture.record.summary.zoneDurationsMs.withIndex().maxBy { it.value }
                val bar = compose.onNodeWithContentDescription(
                    "Zone ${maximum.index + 1}, cumulative duration ${com.example.polarh10activityviewer.heartrate.formatZoneDuration(maximum.value)}")
                bar.performScrollTo()
                val bounds = bar.fetchSemanticsNode().boundsInRoot
                assertTrue("History must retain its vertical plot", bounds.height > bounds.width * 3)
                screenshot("history-${if (night) "dark" else "light"}-font2-vertical-plot")
                noOverflow("Zone 3 · Moderate"); noOverflow("125–139 bpm · 00:00")
                visible("Unclassified time: 00:01")
                screenshot("history-${if (night) "dark" else "light"}-font2-details")
                visible("Very light: 4.00% of Running time")
                visible("Delete session")
            }
            assertEquals(fixture, runBlocking { db.detail("controlled-history") })
        } finally { db.close(); context.deleteDatabase(name) }
    }
}
