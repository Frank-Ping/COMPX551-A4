package com.example.polarh10activityviewer.session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.BluetoothAvailability
import com.example.polarh10activityviewer.R
import com.example.polarh10activityviewer.ble.ConnectionState
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.ble.connectionStatusText
import com.example.polarh10activityviewer.ui.theme.CardCornerRadius
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import com.example.polarh10activityviewer.ui.theme.IconSize
import com.example.polarh10activityviewer.ui.theme.MinimumTouchTarget
import com.example.polarh10activityviewer.ui.theme.PagePadding

@Composable
internal fun SessionHeader(
    availability: BluetoothAvailability,
    connection: ConnectionState,
    batteryLevel: Int?,
    hrSubscription: SubscriptionState,
    onOpenDevices: () -> Unit,
    accSubscription: SubscriptionState = SubscriptionState(),
    ecgSubscription: SubscriptionState = SubscriptionState()
) {
    Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
        OutlinedCard(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
            BoxWithConstraints(Modifier.fillMaxWidth().padding(PagePadding)) {
                if (maxWidth < 560.dp * LocalDensity.current.fontScale) {
                    Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                        ConnectionDetails(availability, connection, batteryLevel, onOpenDevices)
                        StreamStates(hrSubscription, accSubscription, ecgSubscription)
                    }
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                        Box(Modifier.weight(1f)) { ConnectionDetails(availability, connection, batteryLevel, onOpenDevices) }
                        Box(Modifier.weight(1f)) { StreamStates(hrSubscription, accSubscription, ecgSubscription) }
                    }
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
            horizontalArrangement = Arrangement.spacedBy(ContentSpacing), verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(R.drawable.ic_bluetooth), contentDescription = "Devices",
                    modifier = Modifier.size(IconSize), tint = MaterialTheme.colorScheme.primary)
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Text(connectionStatusText(availability, connection), style = MaterialTheme.typography.titleMedium)
                    Text("Battery: ${batteryLevel?.let { "$it%" } ?: "--"}", style = MaterialTheme.typography.bodySmall)
                }
        }
        IconButton(onClick = onOpenDevices, modifier = Modifier.size(MinimumTouchTarget)) {
            Icon(painterResource(R.drawable.ic_devices), contentDescription = "Open Devices",
                tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(IconSize))
        }
    }
}

@Composable
private fun StreamStates(hr: SubscriptionState, acc: SubscriptionState, ecg: SubscriptionState) {
    Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        Text("Data streams", style = MaterialTheme.typography.titleMedium)
        // Each state comes from its subscription, not data readiness.
        listOf("HR" to hr, "ACC" to acc, "ECG" to ecg).forEach { (label, stream) ->
            val color = when (stream.status) {
                SubscriptionStatus.RECEIVING -> HeartRateZoneColors[0]
                SubscriptionStatus.STARTING, SubscriptionStatus.STOPPING -> HeartRateZoneColors[3]
                SubscriptionStatus.FAILED -> MaterialTheme.colorScheme.error
                SubscriptionStatus.IDLE, SubscriptionStatus.STOPPED -> MaterialTheme.colorScheme.outline
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                Box(Modifier.size(12.dp).background(color, CircleShape))
                Text("$label: ${stream.status.name.lowercase().replaceFirstChar { it.uppercase() }}")
            }
        }
    }
}
