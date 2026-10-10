package com.example.polarh10activityviewer.session

import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.chart.ChartStatistics
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import org.junit.Rule
import org.junit.Test
import java.io.File

class CadenceDetailsTest {
    @get:Rule val compose = createComposeRule()

    @Test fun cardOpensIllustratedDetailsAndKeepsReadingsInBothThemes() {
        val dark = mutableStateOf(false)
        val paused = mutableStateOf(false)
        val steps = mutableStateOf(StepState(cadence = 120.0, receivedAcc = true, totalSteps = 600))
        compose.setContent { PolarH10ActivityViewerTheme(darkTheme = dark.value) {
            MotionCard(steps.value, paused.value, ChartStatistics(mean = 100.0, maximum = 160.0))
        } }
        for (night in listOf(false, true)) {
            compose.runOnIdle { dark.value = night }
            compose.onNodeWithTag("session-cadence-card").assertHasClickAction().performClick()
            compose.onNodeWithText("How many steps you take per minute.").assertIsDisplayed()
            compose.onNodeWithText("Step peaks · illustration").performScrollTo().assertIsDisplayed()
            compose.onNodeWithContentDescription("Illustration, not live data:", substring = true).assertExists()
            compose.onNodeWithText("Time → · Example only, not live data").performScrollTo().assertIsDisplayed()
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val folder = File(context.getExternalFilesDir(null), "cadence-details-preview").apply { mkdirs() }
            val scale = context.resources.configuration.fontScale
            File(folder, "cadence-$night-$scale.png").outputStream().use {
                compose.onNode(isDialog()).captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            compose.onNodeWithText("Mean and Max on the card", substring = true).performScrollTo().assertIsDisplayed()
            compose.onNodeWithText("Close").assertIsDisplayed().performClick()
            compose.onNode(isDialog()).assertDoesNotExist()
            compose.onNodeWithText("120").assertExists()
            compose.onNodeWithText("Mean: 100 steps/min").assertExists()
            compose.onNodeWithText("Max: 160 steps/min").assertExists()
        }
        compose.runOnIdle { paused.value = true; steps.value = StepState() }
        compose.onNodeWithTag("session-cadence-card").performClick()
        compose.onNodeWithText("Step peaks · illustration").assertExists()
        compose.onNodeWithText("Close").performClick()
        compose.onNodeWithText("--").assertExists()
    }
}
