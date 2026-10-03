package com.example.polarh10activityviewer.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import java.util.Locale

@Composable
internal fun ColumnScope.SessionSummaryPanel(record: SessionRecord, compact: Boolean) {
    val summary = record.summary
    fun section(weight: Float) = if (compact) Modifier.weight(weight) else Modifier
    SummaryCard(section(1.15f)) {
        SummaryMetrics(listOf(
            SummaryValue("Duration", formatZoneDuration(record.durationMs)),
            SummaryValue("Total Steps", summary.totalSteps?.toString() ?: "--")
        ), enlarged = true)
    }
    SummaryCard(section(1.35f), "Heart Rate") {
        SummaryMetrics(listOf(
            SummaryValue("Mean HR", summaryNumber(summary.meanHr, 0), "bpm"),
            SummaryValue("Range", "${summary.minimumHr ?: "--"}–${summary.maximumHr ?: "--"}", "bpm")
        ))
    }
    // Reserve the title, label and value line heights before adding the card's padding.
    val cadenceHeight = with(LocalDensity.current) { (18.sp.toDp() + 13.sp.toDp() + 27.sp.toDp()) } + 20.dp
    SummaryCard(if (compact) Modifier.height(cadenceHeight) else Modifier, "Cadence") {
        Box(Modifier.padding(bottom = 6.dp)) {
            SummaryMetrics(listOf(
                SummaryValue("Mean", summaryNumber(summary.meanCadence, 0), "steps/min"),
                SummaryValue("Max", summaryNumber(summary.maximumCadence, 0), "steps/min")
            ))
        }
    }
    val placeholderHeight = with(LocalDensity.current) { 16.sp.toDp() + 30.sp.toDp() } + 12.dp
    if (compact) Row(Modifier.fillMaxWidth().height(placeholderHeight), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf("Intensity", "Cardio Load", "HR Recovery").forEach {
            PlaceholderCard(it, Modifier.weight(1f).fillMaxHeight(), compact = true)
        }
    } else listOf("Intensity", "Cardio Load", "HR Recovery").forEach {
        PlaceholderCard(it, Modifier.heightIn(min = placeholderHeight), compact = false)
    }
    PlaceholderCard("Session Strain", if (compact) Modifier.height(placeholderHeight)
        else Modifier.heightIn(min = placeholderHeight), compact)
}

private data class SummaryValue(val label: String, val value: String, val unit: String? = null)

@Composable
private fun SummaryMetrics(values: List<SummaryValue>, enlarged: Boolean = false) {
    if (!enlarged && LocalDensity.current.fontScale > 1.3f) {
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            values.forEachIndexed { index, value ->
                if (index > 0) HorizontalDivider(color = sessionBorder())
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(value.label, fontSize = if (enlarged) 11.sp else 10.sp,
                        lineHeight = if (enlarged) 14.sp else 13.sp, letterSpacing = 0.sp)
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(value.value, fontSize = if (enlarged) 26.sp else 23.sp,
                            lineHeight = if (enlarged) 30.sp else 28.sp, fontWeight = FontWeight.SemiBold)
                        value.unit?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
                    }
                }
            }
        }
        return
    }
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        values.forEachIndexed { index, value ->
            if (index > 0) VerticalDivider(color = sessionBlue().copy(alpha = 0.45f))
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(value.label, textAlign = TextAlign.Center, fontSize = if (enlarged) 11.sp else 10.sp,
                    lineHeight = if (enlarged) 14.sp else 13.sp, letterSpacing = 0.sp)
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BasicText(value.value, Modifier.weight(1f, fill = false), maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = if (enlarged) 26.sp else 23.sp,
                            lineHeight = if (enlarged) 30.sp else 27.sp,
                            fontWeight = FontWeight.SemiBold),
                        autoSize = TextAutoSize.StepBased(minFontSize = 18.sp,
                            maxFontSize = if (enlarged) 26.sp else 23.sp))
                    value.unit?.let {
                        Text(it, fontSize = 11.sp, lineHeight = 14.sp, fontWeight = FontWeight.Normal,
                            maxLines = 1, softWrap = false)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderCard(title: String, modifier: Modifier, compact: Boolean) {
    SummaryCard(modifier) {
        Text(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
        val valueArea = if (compact) Modifier.weight(1f) else Modifier.heightIn(
            min = with(LocalDensity.current) { 30.sp.toDp() })
        Box(Modifier.fillMaxWidth().then(valueArea), contentAlignment = Alignment.Center) {
            Text("--", textAlign = TextAlign.Center,
                fontSize = 26.sp, lineHeight = 30.sp, letterSpacing = 3.sp, fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
internal fun SummaryCard(modifier: Modifier = Modifier, title: String? = null,
    content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp),
        color = MaterialTheme.colorScheme.surface, border = BorderStroke(1.dp, sessionBorder())) {
        Column(Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 3.dp),
            verticalArrangement = if (LocalDensity.current.fontScale > 1.3f) Arrangement.spacedBy(6.dp) else Arrangement.SpaceEvenly) {
            title?.let { Text(it, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold) }
            content()
        }
    }
}

private fun summaryNumber(value: Double?, decimals: Int = 2) =
    value?.let { String.format(Locale.ENGLISH, "%.${decimals}f", it) } ?: "--"
