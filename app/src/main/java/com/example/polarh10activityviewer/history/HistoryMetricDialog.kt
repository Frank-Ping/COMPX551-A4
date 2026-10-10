package com.example.polarh10activityviewer.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.session.ActivityMetricsCalculator
import com.example.polarh10activityviewer.session.SessionRecord
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import java.util.Locale

@Composable
internal fun HistoryMetricDialog(title: String, record: SessionRecord, onDismiss: () -> Unit) {
    val metrics = record.summary.activityMetrics
    fun decimal(value: Double?) = value?.let { String.format(Locale.ENGLISH, "%.1f", it) }
    val value: String?
    val maximum: Int?
    val description: String
    val calculation: String
    when (title) {
        "Total Steps" -> {
            value = record.summary.totalSteps?.toString()
            maximum = null
            description = "Total confirmed steps during this session."
            calculation = "Detected from accelerometer data. Counting begins after four consecutive step candidates are confirmed, including those first four steps."
        }
        "Intensity" -> {
            value = ActivityMetricsCalculator.intensityRating(metrics.intensity)?.toString()
            maximum = 10
            description = "Higher scores mean higher average heart-rate intensity."
            calculation = "Based on time spent in HR zones 1–5, converted to a score out of 10."
        }
        "Cardio Load" -> {
            value = ActivityMetricsCalculator.cardioLoadRating(metrics.cardioLoad)?.toString()
            maximum = 10
            description = "Longer activity and higher heart-rate zones increase the load."
            calculation = "Adds up minutes in each HR zone, weighted from 1 to 5, then converts the total to a score out of 10."
        }
        "Cadence Stability" -> {
            value = decimal(metrics.cadenceCvPercent)?.let { "$it%" }
            maximum = null
            description = "Lower percentages mean steadier cadence."
            calculation = "Cadence variation (standard deviation) ÷ mean cadence × 100%.\nExcludes zero and missing readings.\nRequires at least 30 valid readings."
        }
        "Session Strain" -> {
            value = decimal(metrics.sessionStrainScore)
            maximum = 100
            description = "Higher scores mean greater overall session load."
            calculation = "Combines 70% heart-rate load and 30% movement load, then converts the result to a score out of 100."
        }
        else -> return
    }
    val unavailable = if (value != null) null else when {
        title == "Total Steps" -> "No recorded step data."
        record.collectionIncomplete -> "Data collection was incomplete."
        title == "Cadence Stability" && metrics.cadencePointCount != null &&
            metrics.cadencePointCount < ActivityMetricsCalculator.MIN_CADENCE_POINTS -> "Not enough valid cadence readings."
        title in listOf("Intensity", "Cardio Load", "Session Strain") &&
            record.summary.zoneDurationsMs.sum() <= 0 -> "No classified heart-rate data."
        title == "Session Strain" && record.durationMs <= 0 -> "No active session duration."
        title == "Session Strain" && record.streams[PolarDeviceDataType.ACC]?.let {
            it.received && !it.missing && !it.failed
        } != true -> "Complete accelerometer data is required."
        title == "Session Strain" && record.summary.meanCadence?.let { it.isFinite() && it >= 0 } != true ->
            "No valid mean cadence."
        else -> "This metric was not calculated for this session."
    }
    val result = buildAnnotatedString {
        append(value ?: "--")
        if (value != null && maximum != null) {
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)) {
                append("/ $maximum")
            }
        }
    }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(title) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(result, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface)
                Text(description, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                Text(calculation, style = MaterialTheme.typography.bodyMedium)
                unavailable?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            }
        }, confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } })
}
