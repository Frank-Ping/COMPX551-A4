package com.example.polarh10activityviewer.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.R
import com.example.polarh10activityviewer.ble.ConnectionState
import com.example.polarh10activityviewer.ble.ConnectionStatus
import com.example.polarh10activityviewer.ble.DataReadiness
import com.example.polarh10activityviewer.ble.DataReadinessStatus
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.IconSize
import com.example.polarh10activityviewer.ui.theme.PagePadding

@Composable
internal fun SessionScaffold(
    showHistory: Boolean,
    onSelectHistory: (Boolean) -> Unit,
    controls: @Composable () -> Unit,
    sessionContent: @Composable () -> Unit,
    historyContent: @Composable () -> Unit
) {
    val pages = rememberSaveableStateHolder()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PrimaryTabRow(selectedTabIndex = if (showHistory) 1 else 0,
                modifier = Modifier.statusBarsPadding(), contentColor = MaterialTheme.colorScheme.primary) {
                listOf("Session", "History").forEachIndexed { index, label ->
                    Tab(selected = showHistory == (index == 1), onClick = { onSelectHistory(index == 1) },
                        text = { Text(label, style = MaterialTheme.typography.titleLarge) },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
        bottomBar = { if (!showHistory) controls() }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding).consumeWindowInsets(padding)) {
            // Keep each page's saveable UI state while cancelling off-screen queries.
            pages.SaveableStateProvider(if (showHistory) "history" else "session") {
                if (showHistory) historyContent() else sessionContent()
            }
        }
    }
}

internal fun startDisabledReason(
    availability: BluetoothAvailability,
    actionEnabled: Boolean,
    connection: ConnectionState,
    session: SessionState,
    savingBlocksStart: Boolean,
    subscriptions: List<SubscriptionState>,
    readiness: Collection<DataReadiness>
): String? = when {
    session.open -> "Stop the current session before starting another."
    session.status == SessionStatus.STOPPING -> "Waiting for streams to stop."
    savingBlocksStart -> "Finish saving or discard the failed session before Start."
    !actionEnabled -> "Complete the Bluetooth system request."
    availability != BluetoothAvailability.READY -> "Open Devices to enable Bluetooth access."
    connection.status != ConnectionStatus.CONNECTED -> "Open Devices and connect an H10 to Start."
    subscriptions.any { it.status in setOf(SubscriptionStatus.STARTING, SubscriptionStatus.RECEIVING,
        SubscriptionStatus.STOPPING) } -> "Waiting for streams to stop."
    readiness.none { it.status == DataReadinessStatus.READY && it.configurationComplete } ->
        "Open Devices to check data readiness."
    else -> null
}

@Composable
internal fun SessionControls(canStart: Boolean, canStop: Boolean, onStart: () -> Unit, onStop: () -> Unit,
    canPause: Boolean, canResume: Boolean, paused: Boolean,
    onPause: () -> Unit, onResume: () -> Unit) {
    Surface(shadowElevation = 3.dp) {
        Row(Modifier.fillMaxWidth().navigationBarsPadding().padding(PagePadding),
            horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)) {
            listOf(Triple("Pause", R.drawable.ic_pause, canPause),
                Triple(if (paused) "Continue" else "Start", R.drawable.ic_start, if (paused) canResume else canStart),
                Triple("Stop", R.drawable.ic_stop, canStop))
                .forEach { (label, icon, enabled) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                        Button(onClick = when (label) { "Pause" -> onPause; "Continue" -> onResume; "Start" -> onStart; else -> onStop }, enabled = enabled,
                            shape = CircleShape, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                            modifier = Modifier.size(64.dp).semantics { contentDescription = label }) {
                            Icon(painterResource(icon), contentDescription = null, modifier = Modifier.size(IconSize))
                        }
                        Text(label)
                    }
                }
        }
    }
}

@Composable
internal fun SessionStatusPanel(session: SessionState, disabledReason: String?) {
    if (session.status != SessionStatus.RUNNING) Text("Session: ${session.status.label}", style = MaterialTheme.typography.titleMedium)
    if (!session.open) disabledReason?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
    if (session.status == SessionStatus.STARTING) Text("Waiting for the first sensor data.")
    session.endReason?.let { Text(if (it == "TIME_LIMIT") "Session time limit reached." else it) }
}
