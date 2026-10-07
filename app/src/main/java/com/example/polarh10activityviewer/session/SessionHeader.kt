package com.example.polarh10activityviewer.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.Role
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.R
import com.example.polarh10activityviewer.ble.ConnectionState
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.ble.connectionStatusText
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.IconSize
import com.example.polarh10activityviewer.ui.theme.MinimumTouchTarget

@Composable
internal fun SessionHeader(
    availability: BluetoothAvailability,
    connection: ConnectionState,
    batteryLevel: Int?,
    hrSubscription: SubscriptionState,
    onOpenDevices: () -> Unit,
    accSubscription: SubscriptionState = SubscriptionState(),
    ecgSubscription: SubscriptionState = SubscriptionState(),
    onStopAcc: (() -> Unit)? = null
) {
    SessionCard {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                if (maxWidth < 300.dp * LocalDensity.current.fontScale) {
                    Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                        ConnectionDetails(availability, connection, batteryLevel, onOpenDevices)
                        StreamStates(hrSubscription, accSubscription, ecgSubscription, onStopAcc)
                    }
                } else {
                    Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.weight(1.1f)) { ConnectionDetails(availability, connection, batteryLevel, onOpenDevices) }
                        VerticalDivider(Modifier.fillMaxHeight(), color = sessionBorder())
                        Box(Modifier.weight(1f)) { StreamStates(hrSubscription, accSubscription, ecgSubscription, onStopAcc) }
                    }
                }
            }
    }
}

@Composable
private fun ConnectionDetails(
    availability: BluetoothAvailability,
    connection: ConnectionState,
    batteryLevel: Int?,
    onOpenDevices: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(Modifier.weight(1f).heightIn(min = MinimumTouchTarget).clickable(onClick = onOpenDevices),
            horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.polar_logo_icon), contentDescription = "Devices",
                    modifier = Modifier.size(32.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(connectionStatusText(availability, connection), style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold)
                    Text("Battery: ${batteryLevel?.let { "$it%" } ?: "--"}", style = MaterialTheme.typography.bodySmall)
                }
        }
        IconButton(onClick = onOpenDevices, modifier = Modifier.size(MinimumTouchTarget)) {
            Box(Modifier.size(32.dp).background(sessionBlue().copy(alpha = 0.07f), CircleShape),
                contentAlignment = Alignment.Center) {
                Icon(painterResource(R.drawable.ic_devices), contentDescription = "Open Devices",
                    tint = sessionBlue(), modifier = Modifier.size(IconSize))
            }
        }
    }
}

@Composable
private fun StreamStates(hr: SubscriptionState, acc: SubscriptionState, ecg: SubscriptionState, onStopAcc: (() -> Unit)?) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
        // Each state comes from its subscription, not data readiness.
        listOf("HR" to hr, "ACC" to acc, "ECG" to ecg).forEach { (label, stream) ->
            val icon = when (stream.status) {
                SubscriptionStatus.RECEIVING -> R.drawable.hr_status_green
                SubscriptionStatus.STARTING, SubscriptionStatus.STOPPING -> R.drawable.hr_status_yellow
                SubscriptionStatus.FAILED -> R.drawable.hr_status_red
                SubscriptionStatus.IDLE, SubscriptionStatus.STOPPED -> R.drawable.hr_status_gray
            }
            val stopAction = if (label == "ACC" && onStopAcc != null) {
                Modifier.clickable(
                    enabled = stream.status == SubscriptionStatus.STARTING || stream.status == SubscriptionStatus.RECEIVING,
                    onClickLabel = "Stop ACC stream", role = Role.Button, onClick = onStopAcc
                )
            } else Modifier
            Column(Modifier.weight(1f).heightIn(min = MinimumTouchTarget).then(stopAction).semantics(mergeDescendants = true) {
                contentDescription = "$label: ${stream.status.name.lowercase().replaceFirstChar { it.uppercase() }}"
                stateDescription = stream.status.name.lowercase()
            }, horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)) {
                Image(painterResource(icon), contentDescription = null, modifier = Modifier.size(20.dp))
                Text(label, Modifier.clearAndSetSemantics { }, style = MaterialTheme.typography.bodySmall)
            }
        }
        }
    }
}
