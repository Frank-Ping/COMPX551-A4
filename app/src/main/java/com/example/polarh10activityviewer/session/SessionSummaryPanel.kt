package com.example.polarh10activityviewer.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.ACC
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun ColumnScope.SessionSummaryPanel(record: SessionRecord, compact: Boolean) {
    val summary = record.summary
    fun section(weight: Float) = if (compact) Modifier.weight(weight) else Modifier
    SummaryCard(section(1.15f)) {
        SummaryMetrics(listOf(
            SummaryValue("Duration", formatZoneDuration(record.durationMs)),
            SummaryValue("Total Steps", summary.totalSteps?.toString() ?: "--"),
            SummaryValue("Estimated Distance", summaryNumber(summary.distanceMetres?.div(1000)), "km")
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
            PlaceholderCard(it, Modifier.weight(1f).fillMaxHeight())
        }
    } else listOf("Intensity", "Cardio Load", "HR Recovery").forEach {
        PlaceholderCard(it, Modifier.height(placeholderHeight))
    }
    PlaceholderCard("Session Strain", Modifier.height(placeholderHeight))
}

@Composable
internal fun SessionDetails(record: SessionRecord, dateFormat: DateTimeFormatter) {
    fun date(value: Long?) = value?.let { dateFormat.format(Instant.ofEpochMilli(it)) } ?: "--"
    val summary = record.summary
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Session device: ${record.device?.name ?: "--"} (${record.device?.deviceId ?: "--"})")
        Text("Start requested: ${date(record.startRequestedAt)}")
        Text("Running started: ${date(record.startedAt)}")
        Text("Ended: ${date(record.endedAt)}")
        Text("End reason: ${record.endReason ?: "--"}")
        Text("Completeness: ${if (record.incomplete) "Incomplete" else "Complete"}")
        if (record.interrupted) Text("This session ended after an interruption.")
        if (!summary.receivedValidHr) Text("No valid HR observations. Heart rate statistics are unavailable.")
        Text("Mean HR: ${summaryNumber(summary.meanHr)} bpm")
        Text("Mean / max / min cadence: ${summaryNumber(summary.meanCadence)} / ${summaryNumber(summary.maximumCadence)} / ${summaryNumber(summary.minimumCadence)} steps/min")
        Text("Mean estimated speed: ${summaryNumber(summary.meanSpeedMetresPerSecond?.times(3.6))} km/h")
        Text("Maximum estimated speed: ${summaryNumber(summary.maximumSpeedMetresPerSecond?.times(3.6))} km/h")
        val acc = record.streams.getValue(ACC)
        if (!acc.received) Text("No ACC observations. Cadence statistics are unavailable.")
        else {
            if (record.durationMs == 0L) Text("Mean cadence needs a positive Running duration.")
            if (summary.minimumCadence == null) Text("No qualifying five-second ACC window yet.")
            if (acc.missing || acc.failed) Text("Incomplete ACC data. Mean cadence includes missing time and may be lower.")
        }
        record.streams.forEach { (type, observation) ->
            val status = buildList {
                if (!observation.received) add("no observations")
                if (observation.missing) add("missing data")
                if (observation.failed) add("stream failed")
            }.ifEmpty { listOf("observed; no known gaps") }.joinToString("; ")
            Text("$type: $status")
        }
        Text("ECG and RR history were not recorded. Chart gaps are not interpolated. Dashed lines show the saved mean.")
        Text("Zone percentages use active duration.")
    }
}

private data class SummaryValue(val label: String, val value: String, val unit: String? = null)

@Composable
private fun SummaryMetrics(values: List<SummaryValue>, enlarged: Boolean = false) {
    if (LocalDensity.current.fontScale > 1.3f) {
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
                    Text(value.value, fontSize = if (enlarged) 26.sp else 23.sp,
                        lineHeight = if (enlarged) 30.sp else 27.sp,
                        fontWeight = FontWeight.SemiBold)
                    value.unit?.let {
                        Text(it, fontSize = 11.sp, lineHeight = 14.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceholderCard(title: String, modifier: Modifier) {
    SummaryCard(modifier) {
        Text(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
        Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
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
