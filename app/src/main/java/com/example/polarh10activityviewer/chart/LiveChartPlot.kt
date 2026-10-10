package com.example.polarh10activityviewer.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
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
    axisWidth: Dp? = null, historyCadence: Boolean = false) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
    val plotHeight = height ?: (((maxWidth / 4).coerceIn(80.dp, 120.dp) + 12.dp) * LocalDensity.current.fontScale.coerceAtMost(1.5f))
    Column {
    val valid = snapshot.points.any { it.value != null }
    val meanReady = snapshot.meanReady ?: when (kind) {
        ChartKind.HEART_RATE -> heartRateMeanReady(snapshot)
        ChartKind.CADENCE -> cadenceMeanReady(snapshot)
        ChartKind.ELECTROCARDIOGRAM -> false
    }
    val mean = if (kind == ChartKind.ELECTROCARDIOGRAM || !meanReady) null
        else chartMeanInRange(sessionMean, scale, valid)
    val ticks = chartYTicks(scale)
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
    val labels = if (valid) axisTicks.map { chartScaleLabel(it) } else listOf("--", "--")
    val gutter = axisWidth ?: with(density) { (labels.maxOf { measurer.measure(it, style).size.width }).toDp() + ContentSpacing }
    val ink = if (kind == ChartKind.HEART_RATE) Color(0xFFEF4444) else sessionBlue()
    val axis = MaterialTheme.colorScheme.outline
    val meanInk = if (kind == ChartKind.HEART_RATE) sessionBlue() else Color(0xFFEAB308)
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
            .semantics { contentDescription = "${kind.label} ${if (kind == ChartKind.HEART_RATE) "range" else "line"} chart${if (valid) "" else ": no valid data"}" }) {
            val span = (snapshot.endMs - snapshot.startMs).coerceAtLeast(1.0)
            fun y(value: Double) = inset + ((size.height - 2 * inset) *
                (1 - (value - scale.lower) / (scale.upper - scale.lower))).toFloat()
            fun position(point: ChartPoint) = Offset(
                ((point.elapsedMs - snapshot.startMs) / span * size.width).toFloat(), y(point.value!!))
            clipRect {
                if (kind != ChartKind.HEART_RATE) {
                    ticks.filterNot { ecg && it == 0.0 }.forEach { value ->
                        drawLine(axis.copy(alpha = 0.25f), Offset(0f, y(value)), Offset(size.width, y(value)))
                    }
                    listOf(0f, 0.25f, 0.5f, 0.75f, 1f).forEach { fraction ->
                        drawLine(axis.copy(alpha = 0.18f), Offset(size.width * fraction, inset),
                            Offset(size.width * fraction, size.height - inset))
                    }
                    if (ecg && valid && scale.lower <= 0 && scale.upper >= 0) {
                        drawLine(axis.copy(alpha = 0.25f), Offset(0f, y(0.0)),
                            Offset(size.width, y(0.0)), strokeWidth = 0.5.dp.toPx())
                    }
                }
                drawLine(axis, Offset(0f, inset), Offset(0f, size.height - inset))
                drawLine(axis, Offset(0f, size.height - inset), Offset(size.width, size.height - inset))
                if (ecg) {
                    // Keep every raw ECG sample and its existing gaps, without smoothing.
                    chartSegments(snapshot.points).forEach { segment ->
                        val path = Path()
                        val first = position(segment.first())
                        path.moveTo(first.x, first.y)
                        segment.drop(1).forEach { point ->
                            val next = position(point)
                            path.lineTo(next.x, next.y)
                        }
                        drawPath(path, ink, style = Stroke(width = 1.dp.toPx()))
                        if (segment.size == 1 && !plainLine) drawCircle(ink, 2.dp.toPx(), first)
                    }
                } else if (kind == ChartKind.CADENCE) {
                    // History retains sampled extrema; Session keeps its timed low-pass filter.
                    val segments = if (historyCadence) historyCadenceSegments(snapshot,
                        (size.width / 4.dp.toPx()).toInt().coerceAtLeast(1))
                    else chartSegments(snapshot.cadenceDisplayPoints ?: smoothCadenceForDisplay(snapshot.points))
                    segments.forEach { segment ->
                        val tangents = cadenceDisplayTangents(segment)
                        val path = Path()
                        val first = position(segment.first())
                        path.moveTo(first.x, first.y)
                        segment.zipWithNext().forEachIndexed { index, (previousPoint, point) ->
                            val previous = position(previousPoint)
                            val next = position(point)
                            val thirdTime = (point.elapsedMs - previousPoint.elapsedMs) / 3.0
                            val thirdX = (next.x - previous.x) / 3f
                            path.cubicTo(previous.x + thirdX,
                                y(previousPoint.value!! + tangents[index] * thirdTime),
                                next.x - thirdX, y(point.value!! - tangents[index + 1] * thirdTime),
                                next.x, next.y)
                        }
                        if (!plainLine && segment.size > 1) {
                            val fill = Path().apply {
                                addPath(path)
                                lineTo(position(segment.last()).x, y(scale.lower))
                                lineTo(first.x, y(scale.lower))
                                close()
                            }
                            drawPath(fill, ink.copy(alpha = 0.10f))
                        }
                        drawPath(path, ink, style = Stroke(width = 2.dp.toPx()))
                        if (segment.size == 1) drawCircle(ink, 2.dp.toPx(), first)
                    }
                } else {
                    val columns = (size.width / 5.dp.toPx()).toInt().coerceAtLeast(1)
                    val strokeWidth = 2.dp.toPx()
                    chartMarks(snapshot, kind, columns).forEach { mark ->
                        val x = ((mark.elapsedMs - snapshot.startMs) / span * size.width).toFloat()
                            .coerceIn(strokeWidth / 2, (size.width - strokeWidth / 2).coerceAtLeast(strokeWidth / 2))
                        // A single-valued interval is a dot, not an invented HR range.
                        if (mark.minimum == mark.maximum) drawCircle(ink, strokeWidth / 2, Offset(x, y(mark.mean)))
                        else drawLine(ink, Offset(x, y(mark.minimum)), Offset(x, y(mark.maximum)),
                            strokeWidth = strokeWidth, cap = StrokeCap.Round)
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
        val timeTicks = chartTimeTicks(snapshot, width, with(density) { ContentSpacing.toPx() }, if (ecg) maximumTimeTicks else 2) {
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
