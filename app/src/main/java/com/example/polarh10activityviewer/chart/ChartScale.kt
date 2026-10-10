package com.example.polarh10activityviewer.chart

import java.util.Locale
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

internal data class ChartScale(val lower: Double, val upper: Double, val tickStep: Double? = null)

// Display bounds only: never feed rounded values back into samples or statistics.
internal fun chartScale(points: List<ChartPoint>, kind: ChartKind, referenceValue: Double? = null): ChartScale {
    val samples = points.mapNotNull { it.value }.filter { it.isFinite() &&
        (kind == ChartKind.ELECTROCARDIOGRAM || if (kind == ChartKind.HEART_RATE) it > 0 else it >= 0) }
    // A saved mean may fall outside the per-second history extrema.
    val values = if (samples.isNotEmpty() && referenceValue != null && referenceValue.isFinite())
        samples + referenceValue else samples
    if (values.isEmpty()) return if (kind == ChartKind.ELECTROCARDIOGRAM)
        ChartScale(-1.0, 1.0) else ChartScale(0.0, 1.0)
    if (kind == ChartKind.CADENCE) return ChartScale(0.0,
        maxOf(50.0, ceil(values.max() * 1.1 / 50.0) * 50.0), 50.0)
    if (kind == ChartKind.HEART_RATE) {
        val margin = maxOf(5.0, (values.max() - values.min()) * 0.1)
        var lower = maxOf(0.0, floor((values.min() - margin) / 20.0) * 20.0)
        var upper = ceil((values.max() + margin) / 20.0) * 20.0
        if (upper - lower < 80.0) {
            lower = maxOf(0.0, floor(((upper + lower) / 2 - 40) / 20.0) * 20.0)
            upper = lower + 80.0
        }
        return ChartScale(lower, upper, 20.0)
    }
    val lower = minOf(0.0, values.min())
    val upper = maxOf(0.0, values.max())
    if (lower == upper) return ChartScale(-1.0, 1.0, 1.0)
    val margin = (upper - lower) * 0.1
    val target = maxOf(1.0, (upper - lower + 2 * margin) / 5.0)
    val magnitude = 10.0.pow(floor(log10(target)))
    val step = listOf(1.0, 2.0, 5.0, 10.0).first { it * magnitude >= target } * magnitude
    return ChartScale(floor((lower - margin) / step) * step,
        ceil((upper + margin) / step) * step, step)
}

// Scale timing follows active chart time, so pause does not advance the shrink delay.
internal class LiveScaleTracker {
    private var current: ChartScale? = null
    private var pending: ChartScale? = null
    private var pendingSince = 0.0
    private var lastTime = Double.NEGATIVE_INFINITY

    fun update(target: ChartScale, timeMs: Double, hasData: Boolean = true): ChartScale {
        if (!hasData || timeMs < lastTime) {
            current = null
            pending = null
        }
        lastTime = timeMs
        if (!hasData) return target
        val old = current
        if (old == null) { current = target; return target }
        if (target.lower < old.lower || target.upper > old.upper) {
            val step = maxOf(old.tickStep ?: 1.0, target.tickStep ?: 1.0)
            val expanded = ChartScale(floor(minOf(old.lower, target.lower) / step) * step,
                ceil(maxOf(old.upper, target.upper) / step) * step, step)
            current = expanded
            pending = null
        } else if (target == old) {
            pending = null
        } else if (pending != target) {
            pending = target
            pendingSince = timeMs
        } else if (timeMs - pendingSince >= 10_000.0) {
            current = target
            pending = null
        }
        return current!!
    }
}

internal fun chartScaleLabel(value: Double): String =
    String.format(Locale.ENGLISH, "%.0f", value)
