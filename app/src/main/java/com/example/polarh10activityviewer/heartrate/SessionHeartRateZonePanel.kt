package com.example.polarh10activityviewer.heartrate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.ui.theme.CardCornerRadius
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import com.example.polarh10activityviewer.ui.theme.PagePadding

@Composable
internal fun SessionHeartRateZonePanel(state: HeartRateZoneState, stopped: Boolean) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
            Text("HR Zone", style = MaterialTheme.typography.titleMedium)
            if (stopped) Text("Stopped.")
            if (!state.receivedValidHr) Text("No valid HR data")
            val maximum = state.durationsMs.max()
            val textMeasurer = rememberTextMeasurer()
            val durationWidth = with(LocalDensity.current) {
                state.durationsMs.maxOf {
                    textMeasurer.measure(AnnotatedString(formatZoneDuration(it)),
                        style = MaterialTheme.typography.bodySmall).size.width
                }.toDp()
            }
            HeartRateZone.entries.forEach { zone ->
                val duration = state.durationsMs[zone.ordinal]
                val color = HeartRateZoneColors[zone.ordinal]
                BoxWithConstraints(Modifier.fillMaxWidth()) {
                    val inline = maxWidth >= 320.dp * LocalDensity.current.fontScale
                    val labels: @Composable () -> Unit = {
                        Text("Z${zone.ordinal + 1}", Modifier.width(28.dp * LocalDensity.current.fontScale))
                        Text("${zone.range} bpm", Modifier.width(100.dp * LocalDensity.current.fontScale),
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                    val bar: @Composable () -> Unit = {
                      Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                        Box(Modifier.weight(1f).padding(top = 4.dp).height(12.dp).background(color.copy(alpha = 0.12f))) {
                            // Scale uses milliseconds, including subsecond durations.
                            val fraction = if (maximum == 0L) 0f else duration.toFloat() / maximum
                            Box(Modifier.fillMaxWidth(fraction).height(12.dp).background(color).semantics {
                                contentDescription = "Zone ${zone.ordinal + 1}, cumulative duration ${formatZoneDuration(duration)}"
                            })
                        }
                        // Equal duration columns give every bar the same full-scale width.
                        Box(Modifier.width(durationWidth), contentAlignment = Alignment.CenterEnd) {
                            Text(formatZoneDuration(duration), style = MaterialTheme.typography.bodySmall)
                        }
                      }
                    }
                    if (inline) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(ContentSpacing)) {
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
            if (state.unclassifiedMs > 0) Text("Unclassified time: ${formatZoneDuration(state.unclassifiedMs)}")
        }
    }
}
