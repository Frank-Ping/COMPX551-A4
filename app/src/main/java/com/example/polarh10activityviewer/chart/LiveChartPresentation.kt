package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import kotlin.math.exp
import kotlin.math.abs
import kotlin.math.min

internal data class ChartStatistics(val mean: Double? = null, val maximum: Double? = null,
    val minimum: Double? = null)

// Session-only window. Keep full-session qualification and smoothing before cropping.
// History snapshots and stored readings are not changed.
internal fun recentSessionChart(snapshot: ChartSnapshot, kind: ChartKind): ChartSnapshot {
    if (kind == ChartKind.ELECTROCARDIOGRAM) return snapshot
    val start = (snapshot.endMs - 60_000.0).coerceAtLeast(0.0)
    val points = snapshot.points.filter { it.elapsedMs in start..snapshot.endMs }
    val meanReady = if (kind == ChartKind.HEART_RATE) heartRateMeanReady(snapshot)
        else cadenceMeanReady(snapshot)
    val display = if (kind == ChartKind.CADENCE) {
        val smoothed = smoothCadenceForDisplay(snapshot.points)
        val first = smoothed.indexOfFirst { it.elapsedMs >= start }
        // Two preceding points preserve the curve and its slope at the left clipping edge.
        if (first < 0) emptyList() else smoothed.drop((first - 2).coerceAtLeast(0))
            .takeWhile { it.elapsedMs <= snapshot.endMs }
    } else null
    return snapshot.copy(points = points, windowMs = 60_000.0,
        meanReady = meanReady, cadenceDisplayPoints = display)
}

// Use selected samples before visual smoothing; HR excludes zero, cadence retains actual zero.
internal fun chartStatistics(snapshot: ChartSnapshot, kind: ChartKind): ChartStatistics {
    if (kind == ChartKind.ELECTROCARDIOGRAM) return ChartStatistics()
    val values = snapshot.points.asSequence()
        .filter { it.elapsedMs in snapshot.startMs..snapshot.endMs }
        .mapNotNull { it.value }
        .filter { it.isFinite() && if (kind == ChartKind.HEART_RATE) it > 0.0 else it >= 0.0 }
        .toList()
    return if (values.isEmpty()) ChartStatistics() else ChartStatistics(values.average(), values.max(), values.min())
}

// Wait ten seconds of chart time after the first valid HR reading, excluding paused time.
internal fun heartRateMeanReady(snapshot: ChartSnapshot): Boolean {
    val first = snapshot.points.firstOrNull {
        it.elapsedMs in snapshot.startMs..snapshot.endMs &&
            it.value?.let { value -> value.isFinite() && value > 0.0 } == true
    } ?: return false
    return snapshot.endMs - first.elapsedMs >= 10_000.0
}

// Require ten observed seconds in one valid run before showing the cadence reference.
// Both live (250 ms) and saved (about one second) samples use elapsed time, not point count.
internal fun cadenceMeanReady(snapshot: ChartSnapshot): Boolean {
    var start: Double? = null
    var previous: Double? = null
    for (point in snapshot.points) {
        if (point.elapsedMs !in snapshot.startMs..snapshot.endMs) continue
        val value = point.value
        if (value == null || !value.isFinite() || value < 0.0) {
            start = null
            previous = null
            continue
        }
        val gap = previous?.let { point.elapsedMs - it }
        if (start == null || point.breakBefore || gap == null || gap <= 0.0 || gap > 1500.0) {
            start = point.elapsedMs
        }
        if (point.elapsedMs - start >= 10_000.0) return true
        previous = point.elapsedMs
    }
    return false
}

// Display-only two-second low-pass filter. Restart at real gaps, preserving sample times.
internal fun smoothCadenceForDisplay(points: List<ChartPoint>): List<ChartPoint> {
    var previous: ChartPoint? = null
    return points.map { point ->
        val last = previous
        val value = point.value
        val displayed = if (value == null || point.breakBefore || last?.value == null) point else {
            val weight = 1.0 - exp(-(point.elapsedMs - last.elapsedMs) / 2000.0)
            point.copy(value = last.value + weight * (value - last.value))
        }
        previous = displayed
        displayed
    }
}

// Shared endpoint slopes avoid flattening every sample. Limiting to both adjacent
// secants keeps the Bezier controls inside each interval's value range.
internal fun cadenceDisplayTangents(segment: List<ChartPoint>): List<Double> {
    if (segment.size < 2) return List(segment.size) { 0.0 }
    val slopes = segment.zipWithNext { a, b ->
        (b.value!! - a.value!!) / (b.elapsedMs - a.elapsedMs)
    }
    return segment.indices.map { index ->
        when (index) {
            0 -> slopes.first()
            segment.lastIndex -> slopes.last()
            else -> {
                val before = slopes[index - 1]
                val after = slopes[index]
                when {
                    before > 0 && after > 0 -> min(before, after)
                    before < 0 && after < 0 -> -min(abs(before), abs(after))
                    else -> 0.0
                }
            }
        }
    }
}

// Bounded drawing segments only; sample values and their original times remain unchanged.
internal fun chartSegments(points: List<ChartPoint>): List<List<ChartPoint>> {
    val segments = mutableListOf<MutableList<ChartPoint>>()
    var current: MutableList<ChartPoint>? = null
    points.forEach { point ->
        if (point.value == null || point.breakBefore) current = null
        if (point.value != null) {
            if (current == null) current = mutableListOf<ChartPoint>().also { segments.add(it) }
            current.add(point)
        }
    }
    return segments
}

internal fun chartMeanInRange(mean: Double?, scale: ChartScale, hasData: Boolean): Double? =
    mean?.takeIf { hasData && it.isFinite() && it in scale.lower..scale.upper }

internal fun chartYTicks(scale: ChartScale): List<Double> {
    val step = scale.tickStep ?: return (0..4).map {
        chartScaleLabel(scale.lower + (scale.upper - scale.lower) * it / 4).toDouble()
    }.distinct().sortedDescending()
    val stride = step * kotlin.math.ceil((scale.upper - scale.lower) / step / 6).coerceAtLeast(1.0)
    return generateSequence(kotlin.math.ceil(scale.lower / stride) * stride) { it + stride }
        .takeWhile { it <= scale.upper }.toList().sortedDescending()
}

internal data class ChartTimeTick(val fraction: Float, val label: String)

// Select equally spaced labels only if their measured boxes leave a readable gap.
internal fun chartTimeTicks(snapshot: ChartSnapshot, width: Float, gap: Float,
    maximum: Int = if (snapshot.windowMs == 5000.0) 6 else 3,
    measure: (String) -> Float): List<ChartTimeTick> {
    for (count in maximum downTo 2) {
        val ticks = (0 until count).map { i ->
            val fraction = i.toFloat() / (count - 1)
            ChartTimeTick(fraction, formatZoneDuration((snapshot.startMs +
                (snapshot.endMs - snapshot.startMs) * fraction).toLong()))
        }.distinctBy { it.label }
        if (ticks.size == 1) return listOf(ticks.first().copy(fraction = 0f))
        fun left(tick: ChartTimeTick): Float =
            (width * tick.fraction - measure(tick.label) / 2).coerceIn(0f, (width - measure(tick.label)).coerceAtLeast(0f))
        if (ticks.zipWithNext().all { (a, b) -> left(a) + measure(a.label) + gap <= left(b) }) return ticks
    }
    return emptyList() // The UI stacks From/To when even the endpoints cannot fit.
}
