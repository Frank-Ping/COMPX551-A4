package com.example.polarh10activityviewer.session

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
internal fun ColumnScope.ActivityMetricCards(record: SessionRecord, compact: Boolean) {
    val metrics = record.summary.activityMetrics
    var explanation by remember(record.id) { mutableStateOf<Pair<String, String>?>(null) }
    fun number(value: Double?) = value?.let { String.format(Locale.ENGLISH, "%.1f", it) }
    val titles = listOf("Intensity", "Cardio Load", "Cadence Stability")
    val values = listOf(number(metrics.intensity)?.let { "$it / 5" } ?: "--",
        number(metrics.cardioLoad)?.let { "$it AU" } ?: "--",
        number(metrics.cadenceCvPercent)?.let { "CV $it%" } ?: "--")
    val descriptions = listOf(
        "Time-weighted mean of this app's five fixed heart-rate zones (weights 1 to 5). Unclassified time is excluded. This is not a personalized intensity percentage.",
        "Classified minutes multiplied by zone weights 1 to 5, in arbitrary units (AU). Missing time is not estimated. This is an app estimate, not Polar Cardio Load or TRIMP.",
        "Lower CV means steadier recorded cadence. Population standard deviation divided by the mean of positive, valid per-second cadence records. At least 30 records are required. Pauses, missing values and zero cadence are excluded. Intervals and walk/run transitions can increase CV; this is not a health grade."
    )
    val cardHeight = with(LocalDensity.current) { 32.sp.toDp() + 30.sp.toDp() } + 10.dp
    if (compact) Row(Modifier.fillMaxWidth().height(cardHeight), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        titles.indices.forEach { index ->
            ActivityMetricCard(titles[index], values[index], Modifier.weight(1f).fillMaxHeight()) {
                explanation = titles[index] to descriptions[index]
            }
        }
    } else titles.indices.forEach { index ->
        ActivityMetricCard(titles[index], values[index], Modifier.heightIn(min = cardHeight)) {
            explanation = titles[index] to descriptions[index]
        }
    }
    ActivityMetricCard("Session Strain", number(metrics.sessionStrainScore)?.let { "$it / 100" } ?: "--",
        if (compact) Modifier.height(cardHeight) else Modifier.heightIn(min = cardHeight)) {
        explanation = "Session Strain" to
            "Automatically estimated from this activity's saved summary. " +
            "Activity load score = 100 × raw load / (raw load + 100), displayed out of 100. " +
            "Higher means more accumulated load, not better performance. Short activities usually score lower. " +
            "The 100 AU reference maps to 50 points and is an app parameter, not a medical threshold. " +
            "Heart component = sum of zone minutes × zone number squared (1–5). " +
            "Cadence level = 5 × clamp(mean cadence / 180, 0, 1). " +
            "Motion component = active minutes × cadence level squared. " +
            "Raw load = 70% heart component + 30% motion component, saved in AU. " +
            "Active time excludes pauses. Mean cadence includes stationary time. " +
            "Missing HR time is not estimated and can lower the score; incomplete ACC makes it unavailable. " +
            "The weights and 180 reference are app parameters, not medical thresholds or recommended cadence. " +
            "This is not Polar Strain or a validated fatigue measure."
    }
    explanation?.let { (title, description) ->
        AlertDialog(onDismissRequest = { explanation = null }, title = { Text(title) },
            text = { Text(description, Modifier.verticalScroll(rememberScrollState())) }, confirmButton = {
                TextButton(onClick = { explanation = null }) { Text("Close") }
            })
    }
}

@Composable
private fun ActivityMetricCard(title: String, value: String, modifier: Modifier, onClick: () -> Unit) {
    SummaryCard(modifier.clickable(role = Role.Button, onClickLabel = "About $title", onClick = onClick)) {
        Text(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
        Text(value, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.SemiBold)
    }
}
