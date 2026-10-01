package com.example.polarh10activityviewer.heartrate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.ui.theme.CardCornerRadius
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import com.example.polarh10activityviewer.ui.theme.PagePadding
import com.example.polarh10activityviewer.ui.theme.ZonePlotHeight

internal fun formatZoneDuration(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    return "${(seconds / 60).toString().padStart(2, '0')}:${(seconds % 60).toString().padStart(2, '0')}"
}

@Composable
internal fun HeartRateZonePanel(state: HeartRateZoneState, stopped: Boolean) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
            Text("Heart rate zones", style = MaterialTheme.typography.titleMedium)
            if (stopped) Text("Stopped.")
            if (!state.receivedValidHr) Text("No valid HR data")
            Text("Estimated from received HR", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Without a new reading or an interruption notification, the last zone continues. Silent stream loss may overestimate its duration.",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            val maximum = state.durationsMs.max()
            Text("Duration (mm:ss)")
            Text("Scale: 00:00–${formatZoneDuration(maximum)}",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (maximum == 0L) Text("All zones: 00:00", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(Modifier.fillMaxWidth().height(ZonePlotHeight), verticalAlignment = Alignment.Bottom) {
                HeartRateZone.entries.forEach { zone ->
                    val duration = state.durationsMs[zone.ordinal]
                    val fraction = if (maximum == 0L) 0f else duration.toFloat() / maximum
                    Box(Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
                        Box(Modifier.width(24.dp).height(ZonePlotHeight * fraction)
                            .background(HeartRateZoneColors[zone.ordinal]).semantics {
                                contentDescription = "Zone ${zone.ordinal + 1}, cumulative duration ${formatZoneDuration(duration)}"
                            })
                    }
                }
            }
            Row(Modifier.fillMaxWidth()) {
                HeartRateZone.entries.forEach { zone ->
                    Text("Zone ${zone.ordinal + 1}", Modifier.weight(1f), textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall)
                }
            }
            HeartRateZone.entries.forEach { zone ->
                Row(horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Box(Modifier.padding(top = 6.dp).width(12.dp).height(12.dp)
                        .background(HeartRateZoneColors[zone.ordinal]))
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                        Text("Zone ${zone.ordinal + 1} · ${zone.label}")
                        Text("${zone.range} bpm · ${formatZoneDuration(state.durationsMs[zone.ordinal])}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Text("Unclassified time: ${formatZoneDuration(state.unclassifiedMs)}")
        }
    }
}
