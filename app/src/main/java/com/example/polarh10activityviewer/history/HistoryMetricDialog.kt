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
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.session.SessionRecord
import java.util.Locale

@Composable
internal fun HistoryMetricDialog(title: String, record: SessionRecord, onDismiss: () -> Unit) {
    val metrics = record.summary.activityMetrics
    fun number(value: Double?) = value?.let { String.format(Locale.ENGLISH, "%.2f", it) } ?: "--"
    val zones = record.summary.zoneDurationsMs.map { it / 60_000.0 }
    val zoneDetails = "Recorded zone minutes (Z1–Z5): " + zones.joinToString(", ") { number(it) }
    val zoneRule = "Fixed HR zones: Z1 <110, Z2 110–124, Z3 125–139, Z4 140–154, Z5 ≥155 bpm. Unclassified time is excluded."
    val description: String
    val calculation: String
    val recorded: String
    when (title) {
        "Intensity" -> {
            description = "Average heart-rate zone during classified activity time. A higher score means more time in higher HR zones; it does not measure fitness or performance."
            calculation = "Raw intensity = (1×t1 + 2×t2 + 3×t3 + 4×t4 + 5×t5) / (t1+t2+t3+t4+t5).\n\nDisplayed score = round(2 × raw intensity), limited to 0–10. Each t is time in that zone.\n\n$zoneRule"
            recorded = "$zoneDetails\nRaw intensity: ${number(metrics.intensity)}\n\nNo classified HR time or incomplete collection gives --."
        }
        "Cardio Load" -> {
            description = "Accumulated heart-rate load, combining duration and HR zone. Longer activity and higher zones increase this app's estimated load."
            calculation = "Raw load (AU) = 1×t1 + 2×t2 + 3×t3 + 4×t4 + 5×t5, with time in minutes.\n\nDisplayed score = round(10 × raw load / (raw load + 100)), limited to 0–10. AU means arbitrary units.\n\n$zoneRule"
            recorded = "$zoneDetails\nRaw load: ${number(metrics.cardioLoad)} AU\n\nNo classified HR time or incomplete collection gives --."
        }
        "Cadence Stability" -> {
            description = "Variation in cadence, shown as a coefficient of variation (CV). Lower percentages mean steadier cadence; 0% means all selected cadence values are equal. This is not a score where higher is better."
            calculation = "CV = 100 × population standard deviation / mean cadence.\n\nMean = sum(cadence) / N.\nPopulation standard deviation = sqrt(sum((cadence − mean)²) / N).\n\nUse the final recorded point in each second, then exclude missing, zero and non-finite cadence values. At least 30 valid points are required. This uses recorded values, not the smoothed chart."
            recorded = "Valid points: ${metrics.cadencePointCount ?: "--"}\nStored CV: ${number(metrics.cadenceCvPercent)}%\n\nFewer than 30 valid points or incomplete collection gives --."
        }
        else -> {
            description = "Automatically estimated session load combining heart-rate and movement load. A higher score means greater estimated strain, not better performance."
            calculation = "Heart load = 1²×t1 + 2²×t2 + 3²×t3 + 4²×t4 + 5²×t5 (zone minutes).\n\nCadence level = 5 × clamp(mean cadence / 180, 0, 1).\nMovement load = active minutes × cadence level².\nRaw strain = 0.7 × heart load + 0.3 × movement load.\n\nDisplayed score = 100 × raw strain / (raw strain + 100), shown to one decimal. Active duration excludes pauses. The constants 180 and 100 are fixed app scoring parameters, not personal targets."
            recorded = "$zoneDetails\nActive minutes: ${number(record.durationMs / 60_000.0)}\nMean cadence: ${number(record.summary.meanCadence)} steps/min\nRaw strain: ${number(metrics.sessionStrain)} AU\nStored score: ${number(metrics.sessionStrainScore)} / 100\n\nRequires classified HR time, valid mean cadence, positive active duration and complete ACC collection; otherwise --."
        }
    }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("$title details") },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("What it means", style = MaterialTheme.typography.titleSmall)
                Text(description)
                Text("Calculation", style = MaterialTheme.typography.titleSmall)
                Text(calculation)
                Text("This session", style = MaterialTheme.typography.titleSmall)
                Text(recorded)
                Text("Values above are rounded for explanation. Cards use saved metrics; opening this explanation does not recalculate or change them.")
            }
        }, confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } })
}
