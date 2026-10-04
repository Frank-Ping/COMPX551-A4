package com.example.polarh10activityviewer.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.ui.theme.*
import com.example.polarh10activityviewer.session.sessionBlue
import kotlin.math.roundToInt
import kotlin.math.abs

@Composable
internal fun LivePlot(snapshot: ChartSnapshot, kind: ChartKind, sessionMean: Double?, statusLabel: String?,
    modifier: Modifier = Modifier, scale: ChartScale = chartScale(snapshot.points, kind), height: Dp? = null,
    maximumTimeTicks: Int = if (snapshot.windowMs == 5000.0) 6 else 3,
    plainLine: Boolean = false,
    axisWidth: Dp? = null) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
    val plotHeight = height ?: (((maxWidth / 4).coerceIn(80.dp, 120.dp) + 12.dp) * LocalDensity.current.fontScale.coerceAtMost(1.5f))
    Column {
    val valid = snapshot.points.any { it.value != null }
    val mean = if (kind == ChartKind.ELECTROCARDIOGRAM) null else chartMeanInRange(sessionMean, scale, valid)
    val ticks = chartYTicks(scale, kind)
    val style = MaterialTheme.typography.bodySmall
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val ecg = kind == ChartKind.ELECTROCARDIOGRAM
    val labelHeight = measurer.measure("0", style).size.height
    val inset = labelHeight / 2f
    // Keep zero readable without overlapping a nearby ECG tick label.
    val axisTicks = if (valid && ecg && scale.lower <= 0 && scale.upper >= 0) {
        val availableHeight = with(density) { plotHeight.toPx() } - 2 * inset
        val labelGap = labelHeight + with(density) { 4.dp.toPx() }
        (ticks.filter { abs(it) / (scale.upper - scale.lower) * availableHeight >= labelGap } + 0.0)
            .sortedDescending()
    } else ticks
    val labels = if (valid) axisTicks.map { chartScaleLabel(it, kind) } else listOf("--", "--")
    val gutter = axisWidth ?: with(density) { (labels.maxOf { measurer.measure(it, style).size.width }).toDp() + ContentSpacing }
    val ink = if (kind == ChartKind.HEART_RATE) Color(0xFFEF4444) else sessionBlue()
    val axis = MaterialTheme.colorScheme.outline
    val meanInk = if (kind == ChartKind.HEART_RATE) Color(0xFFF59E0B) else ink
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(kind.unit, style = style, color = textColor)
        statusLabel?.let { Text(it, style = style, color = textColor) }
    }
    Row(Modifier.fillMaxWidth()) {
        Box(Modifier.width(gutter).height(plotHeight)) {
            labels.forEachIndexed { index, label ->
                val fraction = if (valid) ((scale.upper - axisTicks[index]) / (scale.upper - scale.lower)).toFloat()
                    else index.toFloat()
                Text(label, Modifier.align(Alignment.TopEnd).padding(end = ContentSpacing)
                    .offset { IntOffset(0, ((with(density) { plotHeight.toPx() } - 2 * inset) * fraction).roundToInt()) },
                    style = style, color = textColor)
            }
        }
        Canvas(Modifier.weight(1f).height(plotHeight).then(modifier).testTag("live-chart-plot")
            .semantics { contentDescription = "${kind.label} line chart${if (valid) "" else ": no valid data"}" }) {
            val span = (snapshot.endMs - snapshot.startMs).coerceAtLeast(1.0)
            fun y(value: Double) = inset + ((size.height - 2 * inset) *
                (1 - (value - scale.lower) / (scale.upper - scale.lower))).toFloat()
            fun position(point: ChartPoint) = Offset(
                ((point.elapsedMs - snapshot.startMs) / span * size.width).toFloat(), y(point.value!!))
            clipRect {
                ticks.filterNot { ecg && it == 0.0 }.forEach { value ->
                    drawLine(axis.copy(alpha = 0.25f), Offset(0f, y(value)), Offset(size.width, y(value)))
                }
                listOf(0f, 0.25f, 0.5f, 0.75f, 1f).forEach { fraction ->
                    drawLine(axis.copy(alpha = 0.18f), Offset(size.width * fraction, inset),
                        Offset(size.width * fraction, size.height - inset))
                }
                drawLine(axis, Offset(0f, inset), Offset(0f, size.height - inset))
                drawLine(axis, Offset(0f, size.height - inset), Offset(size.width, size.height - inset))
                if (scale.lower <= 0 && scale.upper >= 0 && (!ecg || valid)) {
                    drawLine(if (ecg) axis.copy(alpha = 0.25f) else axis,
                        Offset(0f, y(0.0)), Offset(size.width, y(0.0)),
                        strokeWidth = if (ecg) 0.5.dp.toPx() else 0f)
                }
                // Close each fill at its own endpoints; missing intervals never receive ink.
                chartSegments(snapshot.points).forEach { segment ->
                    val path = Path()
                    val first = position(segment.first())
                    path.moveTo(first.x, first.y)
                    segment.forEachIndexed { index, point ->
                        if (index > 0) position(point).let { path.lineTo(it.x, it.y) }
                    }
                    if (!plainLine && kind != ChartKind.ELECTROCARDIOGRAM && segment.size > 1) {
                        val fill = Path().apply {
                            addPath(path)
                            lineTo(position(segment.last()).x, y(scale.lower))
                            lineTo(first.x, y(scale.lower)); close()
                        }
                        drawPath(fill, ink.copy(alpha = 0.10f))
                    }
                    drawPath(path, ink, style = Stroke(width = (if (kind == ChartKind.ELECTROCARDIOGRAM) 1 else 2).dp.toPx()))
                    if (segment.size == 1 && (kind != ChartKind.ELECTROCARDIOGRAM || !plainLine)) {
                        segment.forEach { drawCircle(ink, 2.dp.toPx(), position(it)) }
                    }
                }
                mean?.let {
                    drawLine(meanInk, Offset(0f, y(it)), Offset(size.width, y(it)), 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 5.dp.toPx())))
                }
            }
        }
    }
    BoxWithConstraints(Modifier.fillMaxWidth().padding(start = gutter)) {
        val width = with(density) { maxWidth.toPx() }
        val timeTicks = chartTimeTicks(snapshot, width, with(density) { ContentSpacing.toPx() }, maximumTimeTicks) {
            measurer.measure(it, style).size.width.toFloat()
        }
        if (timeTicks.isEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                Text("From ${formatZoneDuration(snapshot.startMs.toLong())}", style = style, color = textColor)
                Text("To ${formatZoneDuration(snapshot.endMs.toLong())}", style = style, color = textColor)
            }
        } else {
            Box(Modifier.fillMaxWidth()) {
                timeTicks.forEach { tick ->
                    val labelWidth = measurer.measure(tick.label, style).size.width
                    Text(tick.label, Modifier.offset {
                        IntOffset((width * tick.fraction - labelWidth / 2f)
                            .coerceIn(0f, (width - labelWidth).coerceAtLeast(0f)).roundToInt(), 0)
                    }, style = style, color = textColor)
                }
            }
        }
    }
    }
    }
}
