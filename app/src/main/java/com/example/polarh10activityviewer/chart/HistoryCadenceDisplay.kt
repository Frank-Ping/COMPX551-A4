package com.example.polarh10activityviewer.chart

import kotlin.math.floor

// Drawing only: retain actual first/last/min/max readings in each screen-width bucket.
// Preserve time order, real gaps and both ends of stopped intervals.
internal fun historyCadenceSegments(snapshot: ChartSnapshot, columns: Int): List<List<ChartPoint>> {
    if (columns <= 0) return emptyList()
    val points = snapshot.points.filter { it.elapsedMs in snapshot.startMs..snapshot.endMs }.map {
        if (it.value?.let { value -> value.isFinite() && value >= 0.0 } == true) it else it.copy(value = null)
    }
    val span = (snapshot.endMs - snapshot.startMs).coerceAtLeast(1.0)
    return chartSegments(points).map { segment ->
        val result = mutableListOf<ChartPoint>()
        val bucket = mutableListOf<ChartPoint>()
        var previousColumn = -1
        var stopped = false
        fun flush() {
            if (bucket.isEmpty()) return
            val minimum = bucket.indices.minBy { bucket[it].value!! }
            val maximum = bucket.indices.maxBy { bucket[it].value!! }
            listOf(0, minimum, maximum, bucket.lastIndex).distinct().sorted().forEach { result += bucket[it] }
            bucket.clear()
        }
        segment.forEach { point ->
            val column = floor((point.elapsedMs - snapshot.startMs) / span * columns)
                .toInt().coerceIn(0, columns - 1)
            val isStopped = point.value == 0.0
            if (column != previousColumn || isStopped != stopped) flush()
            bucket += point
            previousColumn = column
            stopped = isStopped
        }
        flush()
        result
    }
}
