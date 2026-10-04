package com.example.polarh10activityviewer.chart

import java.util.Locale
import kotlin.math.ceil
import kotlin.math.floor

internal data class ChartScale(val lower: Double, val upper: Double)

// Display bounds only: never feed rounded values back into samples or statistics.
internal fun chartScale(points: List<ChartPoint>, kind: ChartKind, referenceValue: Double? = null): ChartScale {
    val samples = points.mapNotNull { it.value }
    // A saved mean may fall outside the per-second history extrema.
    val values = if (samples.isNotEmpty() && referenceValue != null && referenceValue.isFinite())
        samples + referenceValue else samples
    if (values.isEmpty()) return if (kind == ChartKind.ELECTROCARDIOGRAM)
        ChartScale(-1.0, 1.0) else ChartScale(0.0, 1.0)
    val lower = minOf(0.0, values.min())
    val upper = maxOf(0.0, values.max())
    if (lower == upper) return if (kind == ChartKind.ELECTROCARDIOGRAM)
        ChartScale(-1.0, 1.0) else ChartScale(0.0, 1.0)
    val margin = (upper - lower) * 0.1
    return ChartScale(
        if (kind == ChartKind.ELECTROCARDIOGRAM) floor(lower - margin) else 0.0,
        ceil(upper + margin)
    )
}

internal fun chartScaleLabel(value: Double): String =
    String.format(Locale.ENGLISH, "%.0f", value)
