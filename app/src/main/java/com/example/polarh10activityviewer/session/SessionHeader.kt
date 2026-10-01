package com.example.polarh10activityviewer.session

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
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
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
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
    zones: HeartRateZoneState,
    stopped: Boolean,
    hrSubscription: SubscriptionState,
    hrMessage: String?,
    onOpenDevices: () -> Unit
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        // Keep labels readable at enlarged system font sizes.
        if (maxWidth < 320.dp * LocalDensity.current.fontScale) {
            Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                ConnectionCard(availability, connection, batteryLevel, onOpenDevices, Modifier.fillMaxWidth())
                IntensityCard(zones, stopped, hrSubscription, hrMessage, Modifier.fillMaxWidth())
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                ConnectionCard(availability, connection, batteryLevel, onOpenDevices, Modifier.weight(1f))
                IntensityCard(zones, stopped, hrSubscription, hrMessage, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ConnectionCard(
    availability: BluetoothAvailability,
    connection: ConnectionState,
    batteryLevel: Int?,
    onOpenDevices: () -> Unit,
    modifier: Modifier
) {
    OutlinedCard(
        onClick = onOpenDevices,
        modifier = modifier.heightIn(min = MinimumTouchTarget),
        shape = RoundedCornerShape(CardCornerRadius)
    ) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            Row(horizontalArrangement = Arrangement.spacedBy(ContentSpacing), verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(R.drawable.ic_bluetooth), contentDescription = "Devices",
                    modifier = Modifier.size(IconSize))
                Text(connectionStatusText(availability, connection), modifier = Modifier.weight(1f))
            }
            Text("Battery: ${batteryLevel?.let { "$it%" } ?: "--"}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun IntensityCard(
    zones: HeartRateZoneState,
    stopped: Boolean,
    subscription: SubscriptionState,
    message: String?,
    modifier: Modifier
) {
    val current = zones.current.takeUnless { stopped }
    Surface(modifier = modifier, shape = RoundedCornerShape(CardCornerRadius)) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            Text("Heart rate intensity", style = MaterialTheme.typography.bodySmall)
            Row(horizontalArrangement = Arrangement.spacedBy(ContentSpacing), verticalAlignment = Alignment.CenterVertically) {
                current?.let { Box(Modifier.size(12.dp).background(HeartRateZoneColors[it.ordinal])) }
                Text(current?.let { "${it.label} · Zone ${it.ordinal + 1}" } ?: "--",
                    modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
            }
            if (current == null) {
                Text(when {
                    stopped -> "Stopped"
                    subscription.status == SubscriptionStatus.FAILED -> "HR unavailable"
                    message != null -> message
                    subscription.status == SubscriptionStatus.STARTING -> "Waiting for HR data"
                    else -> "No valid HR data"
                }, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
