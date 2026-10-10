package com.example.polarh10activityviewer.storage

import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Density
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryMetricDialog
import com.example.polarh10activityviewer.session.ActivityMetrics
import com.example.polarh10activityviewer.session.withActivityMetrics
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.File

class MetricDialogPresentationTest {
    @get:Rule val compose = createComposeRule()

    @Test fun conciseDialogsFitBothThemesAndLargeTextWithoutChangingSavedResults() {
        val record = metricsFixture().withActivityMetrics().record
        val title = mutableStateOf("Total Steps")
        val night = mutableStateOf(false)
        val font = mutableStateOf(1f)
        val shown = mutableStateOf(true)
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, font.value)) {
                PolarH10ActivityViewerTheme(darkTheme = night.value) {
                    if (shown.value) HistoryMetricDialog(title.value, record) { shown.value = false }
                }
            }
        }
        val values = mapOf("Total Steps" to record.summary.totalSteps.toString(), "Intensity" to "6 / 10",
            "Cardio Load" to "5 / 10", "Cadence Stability" to "7.4%", "Session Strain" to "73.6 / 100")
        for (dark in listOf(false, true)) for (scale in listOf(1f, 2f)) for ((name, value) in values) {
            compose.runOnIdle { title.value = name; night.value = dark; font.value = scale; shown.value = true }
            compose.onNodeWithText(name).assertIsDisplayed()
            compose.onNodeWithText(value).assertIsDisplayed()
            compose.onNodeWithText("Close").assertIsDisplayed()
            compose.onNodeWithText("Calculation").assertDoesNotExist()
            if (name == "Cadence Stability") {
                compose.onNodeWithText("Lower percentages mean steadier cadence.").performScrollTo().assertIsDisplayed()
            }
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val folder = File(context.getExternalFilesDir(null), "metric-dialog-preview").apply { mkdirs() }
            File(folder, "$name-$dark-$scale.png").outputStream().use {
                compose.onNode(isDialog()).captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            compose.onNodeWithText("Close").performClick()
            compose.onNode(isDialog()).assertDoesNotExist()
        }
        assertEquals(metricsFixture().withActivityMetrics().record.summary, record.summary)
    }

    @Test fun missingResultsShowSpecificReasonsAndNoScoreDenominator() {
        val original = metricsFixture().withActivityMetrics().record
        val record = mutableStateOf(original.copy(summary = original.summary.copy(totalSteps = null,
            activityMetrics = ActivityMetrics(cadencePointCount = 12))))
        val title = mutableStateOf("Total Steps")
        compose.setContent { PolarH10ActivityViewerTheme { HistoryMetricDialog(title.value, record.value) {} } }
        compose.onNodeWithText("No recorded step data.").assertIsDisplayed()
        compose.runOnIdle { title.value = "Cadence Stability" }
        compose.onNodeWithText("Not enough valid cadence readings.").assertIsDisplayed()
        compose.runOnIdle { title.value = "Intensity"; record.value = record.value.copy(collectionIncomplete = true) }
        compose.onNodeWithText("Data collection was incomplete.").assertIsDisplayed()
        compose.onNodeWithText("--").assertIsDisplayed()
        compose.onNodeWithText("/ 10", substring = true).assertDoesNotExist()
        compose.runOnIdle { record.value = record.value.copy(collectionIncomplete = false,
            summary = record.value.summary.copy(zoneDurationsMs = List(5) { 0L })) }
        compose.onNodeWithText("No classified heart-rate data.").assertIsDisplayed()
    }
}
