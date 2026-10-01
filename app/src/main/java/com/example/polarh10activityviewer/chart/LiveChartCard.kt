package com.example.polarh10activityviewer.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.session.StreamRetryButton
import com.example.polarh10activityviewer.ui.theme.*
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType

@Composable
internal fun LiveChartCard(
    kind: ChartKind, motionSelection: ChartKind, snapshot: ChartSnapshot,
    subscriptions: Map<PolarDeviceDataType, SubscriptionState>,
    readiness: Map<PolarDeviceDataType, DataReadiness>,
    canRetryEcg: Boolean, onSelect: (ChartKind) -> Unit, onRetryEcg: () -> Unit
) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius),
        color = MaterialTheme.colorScheme.surface) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            Text("Live charts", style = MaterialTheme.typography.titleMedium)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
                verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                listOf("HR" to ChartKind.HEART_RATE, "Motion" to motionSelection,
                    "ECG" to ChartKind.ELECTROCARDIOGRAM).forEach { (label, choice) ->
                    FilterChip(selected = kind.type == choice.type, onClick = { onSelect(choice) },
                        label = { Text(label) }, modifier = Modifier.heightIn(min = MinimumTouchTarget))
                }
            }
            if (kind.type == PolarDeviceDataType.ACC) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
                    verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    listOf(ChartKind.CADENCE, ChartKind.SPEED).forEach { choice ->
                        FilterChip(selected = kind == choice, onClick = { onSelect(choice) },
                            label = { Text(if (choice == ChartKind.SPEED) "Speed" else choice.label) },
                            modifier = Modifier.heightIn(min = MinimumTouchTarget))
                    }
                }
            }
            Text("${kind.label} (${kind.unit})")
            SecondaryChartText("Window: ${if (kind == ChartKind.ELECTROCARDIOGRAM) 5 else 60} s · elapsed since Running (mm:ss)")
            val valid = snapshot.points.any { it.value != null }
            Text(when (snapshot.status) {
                SubscriptionStatus.IDLE -> "Not started"
                SubscriptionStatus.STARTING -> "Waiting for data"
                SubscriptionStatus.RECEIVING -> if (valid) "Live" else "Waiting for valid data"
                SubscriptionStatus.STOPPING -> "Stopping · chart frozen"
                SubscriptionStatus.STOPPED -> "Stopped · chart frozen"
                SubscriptionStatus.FAILED -> "Failed · chart frozen"
            })
            // Keep configuration failures reachable even after removing raw sample panels.
            listOf(PolarDeviceDataType.ACC, PolarDeviceDataType.ECG).forEach { type ->
                val state = readiness[type] ?: DataReadiness()
                state.error?.let { Text("$type: $it", color = MaterialTheme.colorScheme.error) }
                if (state.status == DataReadinessStatus.READY && !state.configurationComplete && state.error == null) {
                    Text("Open Devices to confirm $type configuration before starting.")
                }
            }
            val ecg = subscriptions[PolarDeviceDataType.ECG] ?: SubscriptionState()
            // ECG recovery stays visible while viewing HR or Motion, with the original guards.
            if (kind == ChartKind.ELECTROCARDIOGRAM || ecg.error != null || ecg.status == SubscriptionStatus.FAILED) {
                ecg.error?.let { Text("ECG: $it", color = MaterialTheme.colorScheme.error) }
                StreamRetryButton(PolarDeviceDataType.ECG, ecg, canRetryEcg, onRetryEcg)
            }
            LivePlot(snapshot, kind)
            if (kind == ChartKind.ELECTROCARDIOGRAM) {
                SecondaryChartText("Approximate time alignment includes transmission delay. All visible ECG samples are drawn.")
            }
        }
    }
}

@Composable
private fun SecondaryChartText(text: String) = Text(text, style = MaterialTheme.typography.bodySmall,
    color = MaterialTheme.colorScheme.onSurfaceVariant)

@Composable
private fun LivePlot(snapshot: ChartSnapshot, kind: ChartKind) {
    val scale = chartScale(snapshot.points, kind)
    val valid = snapshot.points.any { it.value != null }
    val lower = if (valid) chartScaleLabel(scale.lower, kind) else "--"
    val upper = if (valid) chartScaleLabel(scale.upper, kind) else "--"
    SecondaryChartText("Scale (${kind.unit}): $lower to $upper")
    val ink = MaterialTheme.colorScheme.primary
    val axis = MaterialTheme.colorScheme.outline
    Canvas(Modifier.fillMaxWidth().height(LivePlotHeight).testTag("live-chart-plot")
        .semantics { contentDescription = "${kind.label} line chart${if (valid) "" else ": no valid data"}" }) {
        val span = (snapshot.endMs - snapshot.startMs).coerceAtLeast(1.0)
        fun position(point: ChartPoint) = Offset(
            ((point.elapsedMs - snapshot.startMs) / span * size.width).toFloat(),
            (size.height * (1 - (point.value!! - scale.lower) / (scale.upper - scale.lower))).toFloat())
        clipRect {
            listOf(0f, size.height / 2, size.height).forEach { y ->
                drawLine(axis.copy(alpha = 0.5f), Offset(0f, y), Offset(size.width, y))
            }
            drawLine(axis, Offset.Zero, Offset(0f, size.height))
            if (scale.lower < 0 && scale.upper > 0) {
                val zero = (size.height * scale.upper / (scale.upper - scale.lower)).toFloat()
                drawLine(axis, Offset(0f, zero), Offset(size.width, zero))
            }
            val path = Path()
            var previous: ChartPoint? = null
            var segmentSize = 0
            fun markIsolated() {
                if (kind == ChartKind.ELECTROCARDIOGRAM && segmentSize == 1) {
                    previous?.let { drawCircle(ink, 2.dp.toPx(), position(it)) }
                }
            }
            snapshot.points.forEach { point ->
                if (point.value == null || point.breakBefore) {
                    markIsolated(); previous = null; segmentSize = 0
                }
                if (point.value != null) {
                    val xy = position(point)
                    if (previous == null) path.moveTo(xy.x, xy.y) else path.lineTo(xy.x, xy.y)
                    if (kind != ChartKind.ELECTROCARDIOGRAM) drawCircle(ink, 2.dp.toPx(), xy)
                    previous = point; segmentSize++
                }
            }
            markIsolated()
            drawPath(path, ink, style = Stroke(width = (if (kind == ChartKind.ELECTROCARDIOGRAM) 1 else 2).dp.toPx()))
        }
    }
    LiveTimeLabels(snapshot)
    if (!valid) Text("No valid chart data in this window")
}

@Composable
private fun LiveTimeLabels(snapshot: ChartSnapshot) {
    val start = formatZoneDuration(snapshot.startMs.toLong())
    val end = formatZoneDuration(snapshot.endMs.toLong())
    val middle = formatZoneDuration(((snapshot.startMs + snapshot.endMs) / 2).toLong())
    val style = MaterialTheme.typography.bodySmall
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val width = with(density) { maxWidth.toPx() }
        val gap = with(density) { ControlSpacing.toPx() }
        fun measured(text: String) = measurer.measure(text, style).size.width
        val endsWidth = measured(start) + measured(end)
        if (start == end) SecondaryChartText(start)
        else if (endsWidth + gap > width) {
            Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                SecondaryChartText("From $start"); SecondaryChartText("To $end")
            }
        } else {
            Box(Modifier.fillMaxWidth()) {
                Text(start, Modifier.align(Alignment.CenterStart), style = style,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (middle != start && middle != end &&
                    2 * maxOf(measured(start), measured(end)) + measured(middle) + 2 * gap <= width) {
                    Text(middle, Modifier.align(Alignment.Center), style = style,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(end, Modifier.align(Alignment.CenterEnd), style = style,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
