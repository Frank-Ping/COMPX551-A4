package com.example.polarh10activityviewer.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
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
internal fun SessionSummaryPanel(record: SessionRecord) {
    val summary = record.summary
    SummaryCard(title = "Overview") {
        SummaryMetrics(listOf(
            SummaryValue("Duration", formatZoneDuration(record.durationMs)),
            SummaryValue("Total Steps", summary.totalSteps?.toString() ?: "--"),
            SummaryValue("Estimated Distance", summaryNumber(summary.distanceMetres?.div(1000)), "km")
        ))
    }
    SummaryCard(title = "Heart rate") {
        SummaryMetrics(listOf(
            SummaryValue("Mean HR", summaryNumber(summary.meanHr), "bpm"),
            SummaryValue("Range", "${summary.minimumHr ?: "--"}–${summary.maximumHr ?: "--"}", "bpm")
        ))
    }
    SummaryCard(title = "Cadence") {
        SummaryMetrics(listOf(
            SummaryValue("Max", summaryNumber(summary.maximumCadence), "steps/min"),
            SummaryValue("Mean", summaryNumber(summary.meanCadence), "steps/min")
        ))
    }
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val titles = listOf("Intensity", "Cardio Load", "HR Recovery")
        if (maxWidth / LocalDensity.current.fontScale < 300.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                titles.forEach { PlaceholderCard(it) }
            }
        } else {
            Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                titles.forEach { PlaceholderCard(it, Modifier.weight(1f).fillMaxHeight()) }
            }
        }
    }
    PlaceholderCard("Session Strain")
}

@Composable
internal fun SessionDetails(record: SessionRecord, dateFormat: DateTimeFormatter) {
    var expanded by rememberSaveable(record.id) { mutableStateOf(false) }
    SummaryCard {
        TextButton(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()
            .semantics { stateDescription = if (expanded) "Expanded" else "Collapsed" }) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Session details", Modifier.weight(1f), fontWeight = FontWeight.Medium)
                Text(if (expanded) "−" else "+")
            }
        }
        if (expanded) {
            fun date(value: Long?) = value?.let { dateFormat.format(Instant.ofEpochMilli(it)) } ?: "--"
            val summary = record.summary
            Text("Session device: ${record.device?.name ?: "--"} (${record.device?.deviceId ?: "--"})")
            Text("Start requested: ${date(record.startRequestedAt)}")
            Text("Running started: ${date(record.startedAt)}")
            Text("Ended: ${date(record.endedAt)}")
            Text("End reason: ${record.endReason ?: "--"}")
            Text("Completeness: ${if (record.incomplete) "Incomplete" else "Complete"}")
            if (record.interrupted) Text("This session ended after an interruption.")
            if (!summary.receivedValidHr) Text("No valid HR observations. Heart rate statistics are unavailable.")
            Text("Min cadence: ${summaryNumber(summary.minimumCadence)} steps/min")
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
        }
    }
}

private data class SummaryValue(val label: String, val value: String, val unit: String? = null)

@Composable
private fun SummaryMetrics(values: List<SummaryValue>) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val stacked = maxWidth / LocalDensity.current.fontScale < (values.size * 90).dp
        if (stacked) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                values.forEachIndexed { index, value ->
                    if (index > 0) HorizontalDivider(color = sessionBorder())
                    SummaryMetric(value, Modifier.fillMaxWidth())
                }
            }
        } else {
            Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                values.forEachIndexed { index, value ->
                    if (index > 0) VerticalDivider(color = sessionBorder())
                    SummaryMetric(value, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun SummaryMetric(value: SummaryValue, modifier: Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(value.label, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium)
        Text(value.value, textAlign = TextAlign.Center, fontSize = 26.sp, lineHeight = 32.sp,
            fontWeight = FontWeight.SemiBold)
        value.unit?.let { Text(it, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium) }
    }
}

@Composable
private fun PlaceholderCard(title: String, modifier: Modifier = Modifier) {
    SummaryCard(modifier = modifier) {
        Text(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.weight(1f))
        Text("--", Modifier.fillMaxWidth().padding(vertical = 8.dp), textAlign = TextAlign.Center,
            fontSize = 26.sp, lineHeight = 32.sp, fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SummaryCard(modifier: Modifier = Modifier, title: String? = null,
    content: @Composable ColumnScope.() -> Unit) {
    Surface(modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface, border = BorderStroke(1.dp, sessionBorder())) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            title?.let { Text(it, fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.SemiBold) }
            content()
        }
    }
}

private fun summaryNumber(value: Double?) = value?.let { String.format(Locale.ENGLISH, "%.2f", it) } ?: "--"
