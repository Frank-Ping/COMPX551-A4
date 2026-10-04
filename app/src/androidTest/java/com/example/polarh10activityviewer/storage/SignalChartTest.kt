package com.example.polarh10activityviewer.storage

import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryCharts
import com.example.polarh10activityviewer.history.HistoryDetailLayout
import com.example.polarh10activityviewer.session.SessionSnapshot
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

    @Test fun swipesBrowseFixedWindowsAndClampAtBothEnds() = runBlocking<Unit> {
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
        plot.performTouchInput { swipeRight() }
        start("ECG window start: 0")
        plot.performTouchInput { swipeLeft() }
        start("ECG window start: 5000")
        plot.performTouchInput { swipeLeft() }
        start("ECG window start: 7000")
        plot.performTouchInput { swipeLeft() }
        start("ECG window start: 7000")
        plot.performTouchInput { swipeRight() }
        start("ECG window start: 2000")
        plot.performTouchInput { swipeRight() }
        start("ECG window start: 0")
        plot.performTouchInput { swipeRight() }
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
}
