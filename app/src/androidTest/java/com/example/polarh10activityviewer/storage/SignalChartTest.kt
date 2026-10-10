package com.example.polarh10activityviewer.storage

import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryCharts
import com.example.polarh10activityviewer.history.HistoryDetailLayout
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import android.graphics.Bitmap
import java.io.File
import org.junit.After
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SignalChartTest {
    @get:Rule val compose = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "signal-chart-${UUID.randomUUID()}.db"
    private val db = SessionDatabase(context, name)
    @After fun close() { db.close(); context.deleteDatabase(name) }

    @Test fun threeTabsAreEnabledWithoutRrAndInterruptedMessageOverridesEmptyText() {
        val fixture = databaseFixture().copy(record = databaseFixture().record.copy(collectionIncomplete = true))
        compose.setContent { PolarH10ActivityViewerTheme { HistoryCharts(fixture, Modifier.height(430.dp), db) } }
        for (label in listOf("HR", "Cadence", "ECG")) {
            compose.onNodeWithText(label, useUnmergedTree = true).assertIsEnabled().performClick()
            compose.waitUntil(5000) { compose.onAllNodesWithText("Data collection incomplete").fetchSemanticsNodes().size == 1 }
            compose.onNodeWithText("RR").assertDoesNotExist()
            compose.onNodeWithText("No ECG data in this interval").assertDoesNotExist()
        }
    }

    @Test fun ecgDragsContinuouslyAndClampsAtBothEnds() = runBlocking<Unit> {
        val buffer = SignalBuffer()
        val fixture = databaseFixture().copy(record = databaseFixture().record.copy(id = "window", durationMs = 12_000),
            hrPoints = emptyList(), motionPoints = emptyList())
        for (second in 0 until 12) buffer.receiveEcg(List(130) { i ->
            RawEcg(1_000_000_000L + second * 1_000_000_000L + i * 1_000_000_000L / 130, i - 65)
        }, (second + 1) * 1000L, 130)
        db.writeSignals(listOf(buffer.take(fixture.record, emptyList(), emptyList(), true)))
        db.save(fixture)
        compose.setContent { PolarH10ActivityViewerTheme { HistoryCharts(fixture, Modifier.height(430.dp), db) } }
        compose.onNodeWithText("ECG", useUnmergedTree = true).performClick()
        compose.waitUntil(5000) { compose.onAllNodesWithContentDescription("ECG line chart").fetchSemanticsNodes().size == 1 }
        val plot = compose.onNodeWithTag("live-chart-plot")
        fun start(value: String) = plot.assert(SemanticsMatcher.expectValue(
            androidx.compose.ui.semantics.SemanticsProperties.StateDescription, value))
        fun offset() = plot.fetchSemanticsNode().config[
            androidx.compose.ui.semantics.SemanticsProperties.StateDescription].substringAfterLast(": ").toLong()
        plot.performTouchInput { swipeRight() }
        start("ECG window start: 0")
        plot.performTouchInput { swipeLeft() }
        assertTrue(offset() in 1..4999)
        val first = offset()
        compose.mainClock.advanceTimeBy(1000)
        assertEquals(first, offset()) // No fling after release.
        repeat(3) { plot.performTouchInput { swipeLeft() } }
        start("ECG window start: 7000")
        plot.performTouchInput { swipeRight() }
        assertTrue(offset() in 2001..6999)
        repeat(3) { plot.performTouchInput { swipeRight() } }
        start("ECG window start: 0")
        compose.onNodeWithText("Browse").assertDoesNotExist()
        compose.onNodeWithText("Previous").assertDoesNotExist()
        compose.onNodeWithText("Next").assertDoesNotExist()
    }

    @Test fun switchingChartsKeepsSummaryCardsAndPlotBoundsFixed() {
        val fixture = databaseFixture().let { it.copy(record = it.record.copy(collectionIncomplete = true)) }
        compose.setContent { PolarH10ActivityViewerTheme {
            HistoryDetailLayout(fixture, false, null, null,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX").withZone(ZoneId.of("UTC")),
                true, {}, {}, {}, {}, database = db)
        } }
        compose.onNodeWithTag("history-chart-card").performScrollTo()
        val card = compose.onNodeWithTag("history-chart-card").getUnclippedBoundsInRoot()
        val plot = compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot()
        val titles = listOf("Duration", "Total Steps", "Intensity", "Cardio Load", "Cadence Stability", "Session Strain")
        val summaries = titles.map { compose.onNodeWithText(it).getUnclippedBoundsInRoot() }
        for (choice in listOf("Cadence", "ECG", "HR")) {
            compose.onNode(hasText(choice) and hasClickAction()).performClick()
            compose.waitForIdle()
            assertEquals(card, compose.onNodeWithTag("history-chart-card").getUnclippedBoundsInRoot())
            assertEquals(plot, compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot())
            assertEquals(summaries, titles.map { compose.onNodeWithText(it).getUnclippedBoundsInRoot() })
            compose.onNodeWithText("Data collection incomplete").assertExists()
            if (choice == "ECG") {
                compose.onNodeWithText("Browse").assertDoesNotExist()
                compose.onNodeWithTag("live-chart-plot").performTouchInput { swipeLeft() }
                assertEquals(card, compose.onNodeWithTag("history-chart-card").getUnclippedBoundsInRoot())
                assertEquals(plot, compose.onNodeWithTag("live-chart-plot").getUnclippedBoundsInRoot())
                assertEquals(summaries, titles.map { compose.onNodeWithText(it).getUnclippedBoundsInRoot() })
            }
            val font = context.resources.configuration.fontScale
            val night = context.resources.configuration.uiMode and 0x30
            val file = File(context.getExternalFilesDir(null), "fixed-summary-$choice-$font-$night.png")
            file.outputStream().use { checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot()).compress(Bitmap.CompressFormat.PNG, 100, it) }
        }
        if (context.resources.configuration.fontScale > 1f) {
            compose.onNode(hasText("ECG") and hasClickAction()).performClick()
            val before = compose.onNodeWithTag("history-chart-card").getUnclippedBoundsInRoot()
            compose.onNodeWithTag("live-chart-plot").performTouchInput { swipeUp() }
            val after = compose.onNodeWithTag("history-chart-card").getUnclippedBoundsInRoot()
            assertTrue(after.top < before.top)
            compose.onNodeWithTag("live-chart-plot").assert(SemanticsMatcher.expectValue(
                androidx.compose.ui.semantics.SemanticsProperties.StateDescription, "ECG window start: 0"))
        }
    }

    @Test fun cadenceDragsContinuouslyAndKeepsSessionMean() {
        val fixture = databaseFixture().let { original ->
            original.copy(record = original.record.copy(durationMs = 745_000, collectionIncomplete = false),
                motionPoints = (0..745).map { second ->
                    com.example.polarh10activityviewer.session.MotionHistoryPoint(original.record.id,
                        second.toLong(), second * 1000L, if (second < 300) 100.0 else 140.0, second == 0)
                })
        }
        compose.setContent { PolarH10ActivityViewerTheme { HistoryCharts(fixture, Modifier.height(430.dp), db) } }
        compose.onNodeWithText("Cadence", useUnmergedTree = true).performClick()
        val plot = compose.onNodeWithTag("live-chart-plot")
        val bounds = plot.getUnclippedBoundsInRoot()
        val description = compose.onNode(SemanticsMatcher("Cadence window description") { node ->
            node.config.getOrElse(androidx.compose.ui.semantics.SemanticsProperties.ContentDescription) { emptyList() }
                .any { it.startsWith("Cadence, five-minute window.") }
        }).fetchSemanticsNode().config[androidx.compose.ui.semantics.SemanticsProperties.ContentDescription].single()
        fun page(start: Long, from: String, to: String) {
            plot.assert(SemanticsMatcher.expectValue(androidx.compose.ui.semantics.SemanticsProperties.StateDescription,
                "Cadence window start: $start"))
            compose.onNodeWithText(from).assertIsDisplayed()
            compose.onNodeWithText(to).assertIsDisplayed()
            compose.onNodeWithContentDescription(description).assertExists()
            assertEquals(bounds, plot.getUnclippedBoundsInRoot())
        }
        page(0, "00:00", "05:00")
        plot.performTouchInput { swipeRight() }
        page(0, "00:00", "05:00")
        fun offset() = plot.fetchSemanticsNode().config[
            androidx.compose.ui.semantics.SemanticsProperties.StateDescription].substringAfterLast(": ").toLong()
        plot.performTouchInput {
            down(center)
            moveTo(androidx.compose.ui.geometry.Offset(width * 0.25f, center.y), 250)
        }
        assertTrue(offset() in 1..150_000) // Changes while the finger is still down.
        val held = offset()
        plot.performTouchInput { up() }
        compose.mainClock.advanceTimeBy(1000)
        assertEquals(held, offset())
        page(held, com.example.polarh10activityviewer.heartrate.formatZoneDuration(held),
            com.example.polarh10activityviewer.heartrate.formatZoneDuration(held + 300_000))
        repeat(3) { plot.performTouchInput { swipeLeft() } }
        page(445_000, "07:25", "12:25")
        plot.performTouchInput { swipeLeft() }
        page(445_000, "07:25", "12:25")
        plot.performTouchInput { swipeRight() }
        assertTrue(offset() in 145_001..444_999)
        compose.onNodeWithText("HR", useUnmergedTree = true).performClick()
        compose.onNodeWithText("00:00").assertIsDisplayed()
        compose.onNodeWithText("12:25").assertIsDisplayed()
        compose.onNodeWithText("Cadence", useUnmergedTree = true).performClick()
        page(0, "00:00", "05:00")
    }

    @Test fun cadenceShortAndExactLengthSessionsDoNotCreateEmptyPages() {
        val duration = androidx.compose.runtime.mutableStateOf(125_000L)
        val original = databaseFixture()
        compose.setContent { PolarH10ActivityViewerTheme {
            val fixture = original.copy(record = original.record.copy(durationMs = duration.value))
            HistoryCharts(fixture, Modifier.height(430.dp), db)
        } }
        compose.onNodeWithText("Cadence", useUnmergedTree = true).performClick()
        val plot = compose.onNodeWithTag("live-chart-plot")
        for ((end, label) in listOf(125_000L to "02:05", 300_000L to "05:00", 0L to "00:00")) {
            compose.runOnIdle { duration.value = end }
            plot.performTouchInput { swipeLeft() }
            plot.assert(SemanticsMatcher.expectValue(androidx.compose.ui.semantics.SemanticsProperties.StateDescription,
                "Cadence window start: 0"))
            compose.onNodeWithText(label).assertIsDisplayed()
        }
    }
}
