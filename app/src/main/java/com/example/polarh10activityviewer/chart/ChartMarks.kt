package com.example.polarh10activityviewer.chart

import kotlin.math.floor

internal data class ChartMark(val elapsedMs: Double, val minimum: Double,
    val maximum: Double, val mean: Double)

// Aggregate only for drawing. Never bridge missing runs or blend a stopped interval into motion.
internal fun chartMarks(snapshot: ChartSnapshot, kind: ChartKind, columns: Int): List<ChartMark> {
    if (columns <= 0 || kind == ChartKind.ELECTROCARDIOGRAM) return emptyList()
    val result = mutableListOf<ChartMark>()
    val span = (snapshot.endMs - snapshot.startMs).coerceAtLeast(1.0)
    var bucket = -1
    var count = 0
    var sum = 0.0
    var timeSum = 0.0
    var minimum = 0.0
    var maximum = 0.0
    var stopped = false
    fun flush() {
        if (count > 0) result += ChartMark(timeSum / count, minimum, maximum, sum / count)
        count = 0
        sum = 0.0
        timeSum = 0.0
    }
    for (point in snapshot.points) {
        if (point.elapsedMs !in snapshot.startMs..snapshot.endMs) continue
        val value = point.value
        if (value == null || !value.isFinite() ||
            (if (kind == ChartKind.HEART_RATE) value <= 0 else value < 0)) {
            flush()
            continue
        }
        val nextBucket = floor((point.elapsedMs - snapshot.startMs) / span * columns)
            .toInt().coerceIn(0, columns - 1)
        val nextStopped = kind == ChartKind.CADENCE && value == 0.0
        if (point.breakBefore || nextBucket != bucket || nextStopped != stopped) flush()
        if (count == 0) { minimum = value; maximum = value }
        minimum = minOf(minimum, value)
        maximum = maxOf(maximum, value)
        sum += value
        timeSum += point.elapsedMs
        count++
        bucket = nextBucket
        stopped = nextStopped
    }
    flush()
    return result
}
