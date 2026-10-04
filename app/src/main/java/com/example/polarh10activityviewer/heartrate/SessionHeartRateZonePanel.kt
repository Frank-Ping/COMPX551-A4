package com.example.polarh10activityviewer.heartrate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.session.SessionCard
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import kotlin.math.roundToInt

@Composable
internal fun SessionHeartRateZonePanel(state: HeartRateZoneState) {
    SessionCard(title = "HR Zone") {
        HeartRateZoneRows(state.durationsMs, state.durationsMs.sum() + state.unclassifiedMs,
            state.receivedValidHr)
    }
}

@Composable
internal fun HeartRateZoneRows(durationsMs: List<Long>, elapsedMs: Long, hasHr: Boolean) {
    val style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp,
        fontWeight = FontWeight.Normal)
    val durations = durationsMs.map { if (hasHr) formatZoneDuration(it) else "--" }
    // Use active session time, including unclassified time and excluding pauses, on both pages.
    val fractions = durationsMs.map { if (hasHr && elapsedMs > 0) it.toDouble() / elapsedMs else 0.0 }
    val percentages = fractions.map { if (hasHr && elapsedMs > 0) "${(it * 100).roundToInt()}%" else "--" }
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    fun width(labels: List<String>) = with(density) {
        labels.maxOf { measurer.measure(it, style).size.width }.toDp()
    }
    val zoneWidth = width(HeartRateZone.entries.map { "Z${it.ordinal + 1}" })
    val rangeWidth = width(HeartRateZone.entries.map { "${it.range} bpm" })
    val durationWidth = width(durations)
    val percentWidth = width(percentages + "100%")
    HeartRateZone.entries.forEach { zone ->
        val index = zone.ordinal
        val color = HeartRateZoneColors[index]
        BoxWithConstraints(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
            val inline = maxWidth >= zoneWidth + rangeWidth + durationWidth + percentWidth +
                ContentSpacing * 4 + 40.dp
            val labels: @Composable () -> Unit = {
                Text("Z${index + 1}", Modifier.width(zoneWidth), style = style, color = color)
                Text("${zone.range} bpm", Modifier.width(rangeWidth), style = style, color = color)
            }
            val bar: @Composable () -> Unit = {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Box(Modifier.weight(1f).height(10.dp).clip(RoundedCornerShape(2.dp))
                        .background(color.copy(alpha = 0.12f)).semantics {
                            contentDescription = "Zone ${index + 1}, cumulative duration ${durations[index]}, ${percentages[index]} of active time"
                        }) {
                        Box(Modifier.fillMaxWidth(fractions[index].toFloat()).fillMaxHeight().background(color))
                    }
                    Text(durations[index], Modifier.width(durationWidth), style = style, textAlign = TextAlign.End)
                    Text(percentages[index], Modifier.width(percentWidth), style = style, textAlign = TextAlign.End)
                }
            }
            if (inline) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    labels()
                    Box(Modifier.weight(1f)) { bar() }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) { labels() }
                    bar()
                }
            }
        }
    }
}
