package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import kotlin.math.exp
import kotlin.math.abs
import kotlin.math.min

// Display-only one-second low-pass filter. Restart at real gaps, preserving sample times.
internal fun smoothCadenceForDisplay(points: List<ChartPoint>): List<ChartPoint> {
    var previous: ChartPoint? = null
    return points.map { point ->
        val last = previous
        val value = point.value
        val displayed = if (value == null || point.breakBefore || last?.value == null) point else {
            val weight = 1.0 - exp(-(point.elapsedMs - last.elapsedMs) / 1000.0)
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

internal fun chartYTicks(scale: ChartScale): List<Double> =
    (0..4).map { chartScaleLabel(scale.lower + (scale.upper - scale.lower) * it / 4).toDouble() }
        .distinct().sortedDescending()

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
