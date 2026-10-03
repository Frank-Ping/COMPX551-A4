package com.example.polarh10activityviewer.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.chart.ChartKind
import com.example.polarh10activityviewer.chart.ChartPoint
import com.example.polarh10activityviewer.chart.ChartSnapshot
import com.example.polarh10activityviewer.chart.LivePlot
import com.example.polarh10activityviewer.chart.chartScale
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SummaryCard
import com.example.polarh10activityviewer.session.sessionBlue

import androidx.compose.ui.semantics.contentDescription

@Composable
internal fun HistoryCharts(snapshot: SessionSnapshot, modifier: Modifier = Modifier) {
    var kind by rememberSaveable(snapshot.record.id) { mutableStateOf(ChartKind.HEART_RATE) }
    val points = remember(snapshot, kind) {
        if (kind == ChartKind.HEART_RATE) snapshot.hrPoints.map {
            ChartPoint(it.elapsedMs.toDouble(), it.bpm?.toDouble(), it.breakBefore)
        } else snapshot.motionPoints.map {
            ChartPoint(it.elapsedMs.toDouble(), it.cadence, it.breakBefore)
        }
    }
    val duration = snapshot.record.durationMs.toDouble()
    val chart = remember(points, duration) { ChartSnapshot(points, duration, duration, SubscriptionStatus.STOPPED) }
    val mean = if (kind == ChartKind.HEART_RATE) snapshot.record.summary.meanHr else snapshot.record.summary.meanCadence
    val scale = remember(points, kind, mean) { chartScale(points, kind, mean) }
    val hasData = points.any { it.value != null }
    SummaryCard(modifier) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            val effectiveWidth = maxWidth / LocalDensity.current.fontScale
            val columns = if (effectiveWidth >= 300.dp) 4 else if (effectiveWidth >= 150.dp) 2 else 1
            val choices = listOf("HR" to ChartKind.HEART_RATE, "Cadence" to ChartKind.CADENCE,
                "ECG" to null, "RR" to null)
            Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                choices.chunked(columns).forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        row.forEach { (label, choice) ->
                            HistoryChartChoice(label, choice == kind, choice != null, Modifier.weight(1f)) {
                                if (choice != null) kind = choice
                            }
                        }
                    }
                }
            }
        }
        BoxWithConstraints(Modifier.fillMaxWidth().weight(1f).semantics {
            contentDescription = "${kind.label}, whole session. Dashed line: saved mean ${mean ?: "--"} ${kind.unit}. Gaps are not interpolated."
        }) {
            val axisHeight = with(LocalDensity.current) { MaterialTheme.typography.bodySmall.lineHeight.toDp() } * 2
            LivePlot(chart, kind, mean, statusLabel = null, scale = scale,
                height = (maxHeight - axisHeight).coerceAtLeast(36.dp), maximumTimeTicks = 5)
            if (!hasData) Text("No recorded ${if (kind == ChartKind.HEART_RATE) "heart rate" else "cadence"} data",
                Modifier.align(Alignment.Center), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun HistoryChartChoice(label: String, selected: Boolean, enabled: Boolean,
    modifier: Modifier, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val background = when {
        !enabled -> colors.onSurface.copy(alpha = 0.06f)
        selected -> sessionBlue()
        else -> sessionBlue().copy(alpha = 0.08f)
    }
    Box(modifier.heightIn(min = 32.dp).clip(RoundedCornerShape(50))
        .background(background).selectable(selected, enabled = enabled, role = Role.Tab, onClick = onClick)
        .semantics { if (!enabled) stateDescription = "Not recorded" }
        .padding(horizontal = 4.dp, vertical = 2.dp), contentAlignment = Alignment.Center) {
        Text(label, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            color = when {
                !enabled -> colors.onSurface.copy(alpha = 0.38f)
                selected -> colors.surface
                else -> colors.onSurface
            })
    }
}
