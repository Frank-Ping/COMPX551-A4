package com.example.polarh10activityviewer.heartrate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

internal fun formatZoneDuration(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    return "${(seconds / 60).toString().padStart(2, '0')}:${(seconds % 60).toString().padStart(2, '0')}"
}

@Composable
internal fun HeartRateZonePanel(state: HeartRateZoneState, stopped: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        if (stopped) Text("Stopped.")
        if (!state.receivedValidHr) Text("No valid HR data")
        Text("Estimated from received HR")
        Text("Without a new reading or an interruption notification, the last zone continues. Silent stream loss may overestimate its duration.")
        Text("Duration (mm:ss)")
        val maximum = state.durationsMs.max()
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            Column {
                Box(Modifier.height(150.dp)) {
                    Text(formatZoneDuration(maximum), style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.align(Alignment.TopStart))
                    Text("00:00", style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.align(Alignment.BottomStart))
                }
                Text("\n", style = MaterialTheme.typography.labelSmall)
            }
            HeartRateZone.entries.forEach { zone ->
                val duration = state.durationsMs[zone.ordinal]
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(formatZoneDuration(duration), style = MaterialTheme.typography.labelSmall)
                    Box(Modifier.height(150.dp).fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                        val fraction = if (maximum == 0L) 0f else duration.toFloat() / maximum
                        Box(Modifier.width(24.dp).height((150 * fraction).dp).background(HeartRateZoneColors[zone.ordinal]))
                    }
                    Text("Zone ${zone.ordinal + 1}\n${zone.range}", textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Text("Heart rate zones (bpm)")
        Text("Unclassified time: ${formatZoneDuration(state.unclassifiedMs)}")
    }
}
