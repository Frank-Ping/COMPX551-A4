package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.PolarBleManager
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.DataReadiness
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.example.polarh10activityviewer.heartrate.formatZoneDuration

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
internal fun LiveChartPanel(manager: PolarBleManager,
    subscriptions: Map<PolarDeviceDataType, SubscriptionState>,
    readiness: Map<PolarDeviceDataType, DataReadiness>,
    canRetryEcg: Boolean, onRetryEcg: () -> Unit
) {
    val kind by manager.liveCharts.selection.collectAsState()
    val hrStatistics by manager.heartRateStatistics.collectAsState()
    val steps by manager.stepState.collectAsState()
    var snapshot by remember(manager, kind) { mutableStateOf(manager.chartSnapshot(kind)) }
    // Only the selected chart takes display snapshots. Sampling remains independent.
    LaunchedEffect(manager, kind) {
        while (true) {
            snapshot = manager.chartSnapshot(kind)
            delay(if (kind == ChartKind.ELECTROCARDIOGRAM) 100 else 250)
        }
    }
    val mean = when (kind) {
        ChartKind.HEART_RATE -> hrStatistics.average
        ChartKind.CADENCE -> steps.meanCadence
        ChartKind.SPEED -> steps.averageSpeed?.times(3.6)
        ChartKind.ELECTROCARDIOGRAM -> null
    }
    val maximum = when (kind) {
        ChartKind.HEART_RATE -> hrStatistics.max?.toDouble()
        ChartKind.CADENCE -> steps.maximumCadence
        ChartKind.SPEED -> steps.maximumSpeed?.times(3.6)
        ChartKind.ELECTROCARDIOGRAM -> null
    }
    LiveChartCard(kind, manager.liveCharts.motionSelection, snapshot, mean, maximum, subscriptions, readiness,
        canRetryEcg, manager.liveCharts::select, onRetryEcg)
}

@Composable
internal fun ChartPlot(snapshot: ChartSnapshot) {
    val values = snapshot.points.mapNotNull { it.value }
    val minimum = minOf(0.0, values.minOrNull() ?: 0.0)
    val maximum = maxOf(1.0, values.maxOrNull() ?: 1.0)
    val ink = MaterialTheme.colorScheme.primary
    val axis = MaterialTheme.colorScheme.outline
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(String.format(Locale.ENGLISH, "Min %.1f", minimum))
        Text(String.format(Locale.ENGLISH, "Max %.1f", maximum))
    }
    Canvas(Modifier.fillMaxWidth().height(180.dp)) {
        val span = (snapshot.endMs - snapshot.startMs).coerceAtLeast(1.0)
        fun position(point: ChartPoint) = Offset(
            ((point.elapsedMs - snapshot.startMs) / span * size.width).toFloat(),
            (size.height * (1 - (point.value!! - minimum) / (maximum - minimum))).toFloat())
        val zeroY = (size.height * (1 - (0 - minimum) / (maximum - minimum))).toFloat()
        drawLine(axis, Offset(0f, 0f), Offset(0f, size.height))
        drawLine(axis, Offset(0f, zeroY), Offset(size.width, zeroY))
        val path = Path()
        var connected = false
        snapshot.points.forEach { point ->
            if (point.value == null) connected = false else {
                val xy = position(point)
                if (!connected || point.breakBefore) path.moveTo(xy.x, xy.y) else path.lineTo(xy.x, xy.y)
                drawCircle(ink, 1.5.dp.toPx(), xy)
                connected = true
            }
        }
        drawPath(path, ink, style = Stroke(width = 1.dp.toPx()))
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(formatZoneDuration(snapshot.startMs.toLong()))
        Text(formatZoneDuration(snapshot.endMs.toLong()))
    }
    if (values.isEmpty()) Text("No valid chart data in this window")
}
