package com.example.polarh10activityviewer.session

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import org.junit.Rule
import org.junit.Test

class CadenceRefreshTest {
    @get:Rule val compose = createComposeRule()
    private val steps = mutableStateOf(StepState(cadence = 100.0, receivedAcc = true,
        totalSteps = 100, durationMs = 60_000, maximumCadence = 120.0))
    private val paused = mutableStateOf(false)

    private fun mount() {
        compose.mainClock.autoAdvance = false
        compose.setContent { PolarH10ActivityViewerTheme { MotionCard(steps.value, paused.value) } }
        compose.mainClock.advanceTimeBy(32)
    }

    @Test fun currentMeanAndMaxUseLatestValuesOncePerSecond() {
        mount()
        steps.value = steps.value.copy(cadence = 110.0, totalSteps = 110, maximumCadence = 140.0)
        compose.mainClock.advanceTimeBy(400)
        compose.onNodeWithText("100").assertExists()
        compose.onNodeWithText("Mean: 100 steps/min").assertExists()
        compose.onNodeWithText("Max: 120 steps/min").assertExists()
        steps.value = steps.value.copy(cadence = 130.0, totalSteps = 130, maximumCadence = 160.0)
        compose.mainClock.advanceTimeBy(700)
        compose.onNodeWithText("130").assertExists()
        compose.onNodeWithText("Mean: 130 steps/min").assertExists()
        compose.onNodeWithText("Max: 160 steps/min").assertExists()
        steps.value = steps.value.copy(cadence = 150.0)
        compose.mainClock.advanceTimeBy(400)
        compose.onNodeWithText("130").assertExists()
    }

    @Test fun missingPauseResumeAndResetDoNotWaitForTheNextSecond() {
        mount()
        steps.value = steps.value.copy(cadence = null)
        compose.mainClock.advanceTimeBy(32)
        compose.onNodeWithText("--").assertExists()
        steps.value = steps.value.copy(cadence = 108.0)
        compose.mainClock.advanceTimeBy(32)
        compose.onNodeWithText("108").assertExists()
        paused.value = true
        compose.mainClock.advanceTimeBy(32)
        compose.onNodeWithText("--").assertExists()
        steps.value = steps.value.copy(cadence = 144.0)
        paused.value = false
        compose.mainClock.advanceTimeBy(32)
        compose.onNodeWithText("144").assertExists()
        steps.value = StepState()
        compose.mainClock.advanceTimeBy(32)
        compose.onNodeWithText("--").assertExists()
        compose.onNodeWithText("Mean: -- steps/min").assertExists()
        compose.onNodeWithText("Max: -- steps/min").assertExists()
    }
}
