package com.example.polarh10activityviewer.chart

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import org.junit.Rule
import org.junit.Test
import java.io.File
import kotlin.math.sin

class ChartMarksUiTest {
    @get:Rule val compose = createComposeRule()

    @Test fun historyCadenceShowsWholeSessionWithPeaksStopsAndGaps() {
        val points = (0..3600).map { second ->
            val value = when {
                second in 1800..1900 -> null
                second in 2200..2400 -> 0.0
                second == 1001 -> 190.0
                second == 1002 -> 65.0
                else -> 125.0 + 20.0 * sin(second / 180.0) + 3.0 * sin(second / 13.0)
            }
            ChartPoint(second * 1000.0, value, second == 0)
        }
        val snapshot = ChartSnapshot(points, 3_600_000.0, 3_600_000.0, SubscriptionStatus.STOPPED)
        compose.setContent {
            PolarH10ActivityViewerTheme(darkTheme = true) {
                androidx.compose.material3.Surface {
                    Column(Modifier.fillMaxWidth().systemBarsPadding().padding(12.dp)) {
                        Text("UI TEST DATA · History cadence · no H10")
                        LivePlot(snapshot, ChartKind.CADENCE, chartStatistics(snapshot, ChartKind.CADENCE).mean,
                            statusLabel = null, historyCadence = true)
                    }
                }
            }
        }
        compose.onNodeWithContentDescription("Cadence line chart").assertIsDisplayed()
        compose.onNodeWithText("00:00").assertIsDisplayed()
        compose.onNodeWithText("60:00").assertIsDisplayed()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val folder = File(context.getExternalFilesDir(null), "history-cadence-preview").apply { mkdirs() }
        File(folder, "history-cadence.png").outputStream().use {
            compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    @Test fun recentMinuteChartsKeepOnlyEndpointsAcrossTabsAndThemes() {
        val kind = mutableStateOf(ChartKind.HEART_RATE)
        val dark = mutableStateOf(true)
        val font = mutableStateOf(1f)
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, font.value)) {
                PolarH10ActivityViewerTheme(darkTheme = dark.value) {
                    val points = (0..3600).map { second ->
                        val value = if (kind.value == ChartKind.HEART_RATE)
                            120.0 + 20.0 * sin(second / 300.0) + 3.0 * sin(second / 7.0)
                        else when {
                            second in 1800..2000 -> null
                            second in 2800..2950 -> 0.0
                            else -> 140.0 + 15.0 * sin(second / 100.0)
                        }
                        ChartPoint(second * 1000.0, value, second == 0 || second == 2001)
                    }
                    val snapshot = recentSessionChart(
                        ChartSnapshot(points, 3_600_000.0, 3_600_000.0, SubscriptionStatus.STOPPED), kind.value)
                    val stats = chartStatistics(snapshot, kind.value)
                    Column(Modifier.fillMaxSize().systemBarsPadding().padding(12.dp)) {
                        Text("UI TEST DATA · no H10")
                        LiveChartCard(kind.value, snapshot, stats.mean, stats.maximum, emptyMap(),
                            { kind.value = it }, paused = false)
                    }
                }
            }
        }
        for (scale in listOf(1f, 2f)) for (night in listOf(true, false)) {
            compose.runOnIdle { dark.value = night; font.value = scale }
            for ((tab, description) in listOf("HR" to "Heart rate range chart", "Cadence" to "Cadence line chart")) {
                compose.onNodeWithText(tab).performClick()
                compose.onNodeWithContentDescription(description).assertIsDisplayed()
                compose.onNodeWithText("59:00").assertIsDisplayed()
                compose.onNodeWithText("60:00").assertIsDisplayed()
                compose.onNodeWithText("00:00").assertDoesNotExist()
                compose.onNodeWithText("30:00").assertDoesNotExist()
                compose.mainClock.advanceTimeBy(500)
                compose.waitForIdle()
                val context = InstrumentationRegistry.getInstrumentation().targetContext
                val folder = File(context.getExternalFilesDir(null), "recent-minute-preview").apply { mkdirs() }
                File(folder, "$tab-$scale-$night.png").outputStream().use {
                    compose.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
                }
            }
        }
    }
}
