package com.example.polarh10activityviewer.session

import androidx.compose.runtime.*
import com.example.polarh10activityviewer.motion.StepState
import kotlinx.coroutines.delay

// Sample only the displayed readings; collection and session accounting keep their own clocks.
@Composable
internal fun rememberCadenceDisplay(steps: StepState, paused: Boolean): StepState {
    val latest by rememberUpdatedState(steps)
    val available = steps.cadence != null
    val reset = steps.durationMs == 0L
    var displayed by remember(paused, available, reset, steps.receivedAcc) { mutableStateOf(steps) }
    LaunchedEffect(paused, available, reset, steps.receivedAcc) {
        while (true) {
            delay(1000)
            displayed = latest
        }
    }
    return displayed
}
