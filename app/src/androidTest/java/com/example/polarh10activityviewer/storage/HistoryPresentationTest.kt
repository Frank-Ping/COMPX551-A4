package com.example.polarh10activityviewer.storage

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.sp
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryDetailLayout
import com.example.polarh10activityviewer.session.*
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HistoryPresentationTest {
    @get:Rule val compose = createComposeRule()
    private val snapshot = mutableStateOf(fixture())
    private val dark = mutableStateOf(false)
    private val failure = mutableStateOf<String?>(null)
    private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX").withZone(ZoneId.of("Pacific/Auckland"))

    private fun fixture() = databaseFixture("presentation", Instant.parse("2026-10-02T21:10:00Z").toEpochMilli()).let {
        it.copy(record = it.record.copy(durationMs = 1_200_000, interrupted = false,
            streams = it.record.streams.mapValues { entry -> entry.value.copy(missing = false) },
            summary = it.record.summary.copy(meanHr = 125.0, minimumHr = 70, maximumHr = 160,
                totalSteps = 1920, distanceMetres = 1400.0, meanCadence = 96.0, maximumCadence = 144.0,
                zoneDurationsMs = listOf(240_000, 300_000, 420_000, 180_000, 60_000), unclassifiedMs = 0)),
            hrPoints = listOf(HrHistoryPoint("presentation", 0, 0, 70, true),
                HrHistoryPoint("presentation", 1, 300_000, 160, false),
                HrHistoryPoint("presentation", 2, 600_000, null, true),
                HrHistoryPoint("presentation", 3, 900_000, 100, true),
                HrHistoryPoint("presentation", 4, 1_200_000, 80, false)),
            motionPoints = listOf(MotionHistoryPoint("presentation", 0, 0, 0.0, 0.0, true),
                MotionHistoryPoint("presentation", 1, 1_200_000, 144.0, 1.2, false)))
    }

    private fun mount() {
        compose.setContent { PolarH10ActivityViewerTheme(darkTheme = dark.value) {
            SessionScaffold(true, {}, {}, {}, {
                Column {
                    Text("UI TEST DATA · no H10", fontSize = 10.sp, lineHeight = 12.sp)
                    Box(Modifier.weight(1f)) {
                        HistoryDetailLayout(snapshot.value, false, null, failure.value, format,
                            true, {}, {}, {}, {})
                    }
                }
            })
        } }
    }

    private fun show(text: String) = compose.onNodeWithText(text).performScrollTo().assertIsDisplayed()
    private fun selectCadence() = compose.onNode(hasText("Cadence") and hasClickAction()).performScrollTo().performClick()

    @Test fun summaryUsesStoredValuesAndKeepsFourUndefinedMetricsAndEnabledChoices() {
        mount()
        for (value in listOf("1920", "125", "70–160", "96", "144")) show(value)
        compose.onNodeWithText("Estimated Distance").assertDoesNotExist()
        compose.onNodeWithContentDescription("Session details").assertDoesNotExist()
        compose.onNodeWithText("10:10").assertHasNoClickAction()
        compose.onAllNodesWithText("--").assertCountEquals(4)
        for (title in listOf("Intensity", "Cardio Load", "HR Recovery", "Session Strain")) show(title)
        for (title in listOf("Overview", "Activity charts", "Unclassified")) compose.onNodeWithText(title).assertDoesNotExist()
        compose.onAllNodesWithText("Duration").assertCountEquals(1)
        for (choice in listOf("ECG", "RR")) {
            compose.onNodeWithText(choice).performScrollTo().assertIsEnabled().performClick()
            compose.onNodeWithText(choice).assertIsSelected()
        }
        compose.onNodeWithText("HR").performScrollTo().performClick()
        compose.onNodeWithContentDescription("Heart rate, whole session. Dashed line: saved mean 125.0 bpm. Gaps are not interpolated.").assertExists()
        selectCadence()
        compose.onNodeWithContentDescription("Cadence, whole session. Dashed line: saved mean 96.0 steps/min. Gaps are not interpolated.").assertExists()
        compose.onNode(hasText("20:00") and hasAnyAncestor(hasContentDescription(
            "Cadence, whole session. Dashed line: saved mean 96.0 steps/min. Gaps are not interpolated.")))
            .performScrollTo().assertIsDisplayed()
    }

    @Test fun zonesKeepActiveDurationDenominatorAndDistinguishZeroFromNoHr() {
        snapshot.value = fixture().let { it.copy(record = it.record.copy(durationMs = 100_000,
            summary = it.record.summary.copy(zoneDurationsMs = listOf(10_000, 20_000, 0, 0, 0), unclassifiedMs = 70_000))) }
        mount()
        show("10%")
        show("20%")
        compose.onAllNodesWithText("0%").assertCountEquals(3)
        compose.onNodeWithText("70%").assertDoesNotExist()
        compose.runOnIdle { snapshot.value = snapshot.value.let { it.copy(record = it.record.copy(
            durationMs = 0, summary = SessionSummary(totalSteps = 0, distanceMetres = 0.0)),
            hrPoints = emptyList(), motionPoints = emptyList()) } }
        show("No valid heart rate data")
        show("No recorded heart rate data")
        compose.onAllNodesWithText("0%").assertCountEquals(0)
        selectCadence()
        show("No recorded cadence data")
    }

    @Test fun zeroDurationSinglePointAndConstantSeriesRemainRenderable() {
        mount()
        for (duration in listOf(0L, 14_400_000L)) {
            compose.runOnIdle { snapshot.value = fixture().let { it.copy(record = it.record.copy(durationMs = duration),
                hrPoints = listOf(HrHistoryPoint("presentation", 0, 0, 100, true)),
                motionPoints = listOf(MotionHistoryPoint("presentation", 0, 0, 0.0, 0.0, true),
                    MotionHistoryPoint("presentation", 1, duration, 0.0, 0.0, false))) } }
            compose.onNodeWithText("HR").performScrollTo().performClick()
            compose.onNodeWithContentDescription("Heart rate line chart").assertExists()
            selectCadence()
            compose.onNodeWithContentDescription("Cadence line chart").assertExists()
            noTextOverflow()
        }
    }

    @Test fun finalDetailVisualsFitBothThemesAtActualSystemFont() {
        mount()
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night; snapshot.value = fixture(); failure.value = null }
            val prefix = if (night) "dark" else "light"
            compose.onNodeWithTag("history-detail-scroll").performSemanticsAction(SemanticsActions.ScrollBy) { it(0f, -100_000f) }
            show("Activity Summary"); noTextOverflow(); capture("$prefix-top")
            if (compose.density.fontScale <= 1f) {
                val viewport = compose.onNodeWithTag("history-detail-scroll").getUnclippedBoundsInRoot()
                val delete = compose.onNodeWithText("Delete session").assertIsDisplayed().getUnclippedBoundsInRoot()
                assertTrue("Normal-font summary must fit one viewport", delete.bottom <= viewport.bottom)
            }
            show("Session Strain"); noTextOverflow(); capture("$prefix-metrics")
            selectCadence(); noTextOverflow(); capture("$prefix-cadence")
            show("HR Zones"); noTextOverflow(); capture("$prefix-zones")
            show("Delete session"); noTextOverflow(); capture("$prefix-bottom")
            compose.runOnIdle {
                snapshot.value = fixture().let { it.copy(record = it.record.copy(durationMs = 14_400_000,
                    interrupted = true, summary = it.record.summary.copy(totalSteps = 57_600, distanceMetres = 123_450.0))) }
                failure.value = "Delete failed. Please retry Delete session."
            }
            show("Activity Summary"); noTextOverflow(); capture("$prefix-long-values")
            show("Delete failed. Please retry Delete session."); noTextOverflow(); capture("$prefix-error")
        }
    }

    private fun noTextOverflow() {
        val nodes = compose.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.GetTextLayoutResult), useUnmergedTree = true)
        repeat(nodes.fetchSemanticsNodes().size) { index ->
            val layouts = mutableListOf<TextLayoutResult>()
            nodes[index].performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
            layouts.forEach { layout ->
                assertFalse("Truncated: ${layout.layoutInput.text}", layout.multiParagraph.didExceedMaxLines)
                if (layout.size.height < layout.multiParagraph.height - 1) capture("overflow")
                assertTrue("Vertical overflow: ${layout.layoutInput.text}, box=${layout.size.height}, text=${layout.multiParagraph.height}",
                    layout.size.height >= layout.multiParagraph.height - 1)
                if (layout.layoutInput.text.text in listOf("km", "bpm", "steps/min")) {
                    assertEquals("Units must remain on one line", 1, layout.lineCount)
                }
                repeat(layout.lineCount) { line ->
                    // Aligned text uses paragraph coordinates, not the intrinsic Text box's origin.
                    assertTrue("Horizontal overflow: ${layout.layoutInput.text}",
                        layout.getLineRight(line) - layout.getLineLeft(line) <= layout.size.width + 1)
                }
            }
        }
    }

    private fun capture(name: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val dir = File(context.getExternalFilesDir(null), "step85d-captures").apply { mkdirs() }
        File(dir, "$name.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
}
