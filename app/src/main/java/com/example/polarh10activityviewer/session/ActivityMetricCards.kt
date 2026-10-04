package com.example.polarh10activityviewer.session

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
internal fun ColumnScope.ActivityMetricCards(record: SessionRecord, compact: Boolean) {
    val metrics = record.summary.activityMetrics
    fun number(value: Double?) = value?.let { String.format(Locale.ENGLISH, "%.1f", it) }
    val titles = listOf("Intensity", "Cardio Load", "Cadence Stability")
    val values = listOf(number(metrics.intensity)?.let { "$it / 5" } ?: "--",
        number(metrics.cardioLoad)?.let { "$it AU" } ?: "--",
        number(metrics.cadenceCvPercent)?.let { "CV $it%" } ?: "--")
    val cardHeight = with(LocalDensity.current) { 32.sp.toDp() + 30.sp.toDp() } + 10.dp
    if (compact) Row(Modifier.fillMaxWidth().height(cardHeight), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        titles.indices.forEach { index ->
            ActivityMetricCard(titles[index], values[index], Modifier.weight(1f).fillMaxHeight())
        }
    } else titles.indices.forEach { index ->
        ActivityMetricCard(titles[index], values[index], Modifier.heightIn(min = cardHeight))
    }
    ActivityMetricCard("Session Strain", number(metrics.sessionStrainScore)?.let { "$it / 100" } ?: "--",
        if (compact) Modifier.height(cardHeight) else Modifier.heightIn(min = cardHeight))
}

@Composable
private fun ActivityMetricCard(title: String, value: String, modifier: Modifier) {
    SummaryCard(modifier) {
        Text(title, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Medium)
        Text(value, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.SemiBold)
    }
}
