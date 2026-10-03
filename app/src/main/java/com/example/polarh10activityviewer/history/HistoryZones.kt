package com.example.polarh10activityviewer.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.heartrate.HeartRateZone
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SummaryCard
import com.example.polarh10activityviewer.session.sessionBlue
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import java.util.Locale

@Composable
internal fun HistoryZones(record: SessionRecord, modifier: Modifier = Modifier) {
    val summary = record.summary
    val hasHr = summary.receivedValidHr
    fun percent(duration: Long) = if (record.durationMs > 0)
        String.format(Locale.ENGLISH, "%.0f%%", duration * 100.0 / record.durationMs) else "--"
    val durations = summary.zoneDurationsMs.map { if (hasHr) formatZoneDuration(it) else "--" }
    val percentages = summary.zoneDurationsMs.map { if (hasHr) percent(it) else "--" }
    val style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 16.sp,
        fontWeight = FontWeight.Normal)
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    fun width(labels: List<String>) = with(density) { labels.maxOf { measurer.measure(it, style).size.width }.toDp() }
    val zoneWidth = width(listOf("Z1", "Z2", "Z3", "Z4", "Z5"))
    val rangeWidth = width(HeartRateZone.entries.map { "${it.range} bpm" })
    val durationWidth = width(durations)
    val percentWidth = width(percentages)
    SummaryCard(modifier, title = "HR Zones") {
        if (!hasHr) Text("No valid heart rate data", style = style)
        
        HeartRateZone.entries.forEachIndexed { index, zone ->
            val fraction = if (hasHr && record.durationMs > 0)
                (summary.zoneDurationsMs[index].toDouble() / record.durationMs).toFloat() else 0f
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                val inline = maxWidth >= zoneWidth + rangeWidth + durationWidth + percentWidth + 65.dp
                if (inline) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Z${index + 1}", Modifier.width(zoneWidth), style = style)
                        Text("${zone.range} bpm", Modifier.width(rangeWidth), style = style, color = sessionBlue())
                        ZoneBar(fraction, HeartRateZoneColors[index], Modifier.weight(1f))
                        Text(durations[index], Modifier.width(durationWidth), style = style, textAlign = TextAlign.End)
                        Text(percentages[index], Modifier.width(percentWidth), style = style, textAlign = TextAlign.End)
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Z${index + 1}", style = style)
                            Text("${zone.range} bpm", Modifier.weight(1f), style = style, color = sessionBlue())
                        }
                        ZoneBar(fraction, HeartRateZoneColors[index], Modifier.fillMaxWidth())
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(durations[index], Modifier.weight(1f), style = style)
                            Text(percentages[index], Modifier.weight(1f), style = style, textAlign = TextAlign.End)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ZoneBar(fraction: Float, color: Color, modifier: Modifier) {
    Box(modifier.height(9.dp).clip(RoundedCornerShape(3.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)) {
        Box(Modifier.fillMaxWidth(fraction).fillMaxHeight().background(color))
    }
}
