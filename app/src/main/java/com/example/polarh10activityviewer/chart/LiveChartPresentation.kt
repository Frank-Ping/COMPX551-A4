package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.heartrate.formatZoneDuration

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

internal fun chartYTicks(scale: ChartScale, kind: ChartKind): List<Double> =
    (0..4).map { chartScaleLabel(scale.lower + (scale.upper - scale.lower) * it / 4, kind).toDouble() }
        .distinct().sortedDescending()

internal data class ChartTimeTick(val fraction: Float, val label: String)

// Select equally spaced labels only if their measured boxes leave a readable gap.
internal fun chartTimeTicks(snapshot: ChartSnapshot, width: Float, gap: Float,
    measure: (String) -> Float): List<ChartTimeTick> {
    val maximum = if (snapshot.windowMs == 5000.0) 6 else 3
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
