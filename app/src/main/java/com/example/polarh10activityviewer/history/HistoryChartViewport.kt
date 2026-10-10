package com.example.polarh10activityviewer.history

import com.example.polarh10activityviewer.chart.ChartPoint

internal fun draggedHistoryStart(start: Double, deltaPx: Float, widthPx: Int,
    windowMs: Long, durationMs: Long): Double =
    (start - deltaPx.toDouble() / widthPx.coerceAtLeast(1) * minOf(windowMs, durationMs))
        .coerceIn(0.0, (durationMs - windowMs).coerceAtLeast(0).toDouble())

// Retain the adjacent samples needed to draw through viewport edges, never across gaps.
internal fun historyViewportPoints(points: List<ChartPoint>, start: Long, end: Long): List<ChartPoint> =
    points.filterIndexed { i, p -> p.elapsedMs in start.toDouble()..end.toDouble() ||
        (p.elapsedMs < start && points.getOrNull(i + 1)?.let { !it.breakBefore && it.elapsedMs >= start } == true) ||
        (p.elapsedMs > end && !p.breakBefore && points.getOrNull(i - 1)?.elapsedMs?.let { it <= end } == true) }

// A small surrounding buffer lets ECG move immediately without querying for every drag event.
internal class HistoryEcgCache(private val durationMs: Long,
    private val read: suspend (Long, Long) -> List<ChartPoint>) {
    private var startMs = -1L
    private var endMs = -1L
    private var points = emptyList<ChartPoint>()

    suspend fun at(start: Long): List<ChartPoint> {
        val neededStart = (start - 2500).coerceAtLeast(0)
        val neededEnd = minOf(start + 7500, durationMs)
        if (startMs < 0 || neededStart < startMs || neededEnd > endMs) {
            val nextStart = (start - 10_000).coerceAtLeast(0)
            val nextEnd = minOf(start + 15_000, durationMs)
            val loaded = read(nextStart, nextEnd)
            points = loaded
            startMs = nextStart
            endMs = nextEnd
        }
        return points
    }
}
