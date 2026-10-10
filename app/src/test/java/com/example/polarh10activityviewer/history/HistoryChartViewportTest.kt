package com.example.polarh10activityviewer.history

import com.example.polarh10activityviewer.chart.ChartPoint
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class HistoryChartViewportTest {
    @Test fun draggingIsProportionalAndReversesImmediatelyAtEdges() {
        assertEquals(150_000.0, draggedHistoryStart(0.0, -150f, 300, 300_000, 745_000), 0.0)
        assertEquals(2500.0, draggedHistoryStart(0.0, -150f, 300, 5000, 12_000), 0.0)
        assertEquals(445_000.0, draggedHistoryStart(440_000.0, -150f, 300, 300_000, 745_000), 0.0)
        assertEquals(435_000.0, draggedHistoryStart(445_000.0, 10f, 300, 300_000, 745_000), 0.0)
        assertEquals(0.0, draggedHistoryStart(0.0, -150f, 300, 300_000, 125_000), 0.0)
    }

    @Test fun cacheReusesNearbyDataAndPrefetchesBeforeWindowReachesItsEdge() = runBlocking {
        val reads = mutableListOf<Pair<Long, Long>>()
        val cache = HistoryEcgCache(60_000) { start, end ->
            reads += start to end
            listOf(ChartPoint(start.toDouble(), 1.0, true))
        }
        val first = cache.at(0)
        for (start in 1L..7000L step 100L) assertSame(first, cache.at(start))
        assertEquals(listOf(0L to 15_000L), reads)
        cache.at(8000)
        assertEquals(0L to 23_000L, reads.last())
        cache.at(55_000)
        assertEquals(45_000L to 60_000L, reads.last())
        cache.at(54_000)
        assertEquals(3, reads.size)
        cache.at(0)
        assertEquals(4, reads.size)
    }

    @Test fun emptyRangesAreCachedAndFailedReadsCanBeRetried() = runBlocking {
        var calls = 0
        val cache = HistoryEcgCache(60_000) { _, _ ->
            calls++
            if (calls == 1) error("read failed")
            emptyList()
        }
        try { cache.at(0); fail("Expected failure") } catch (_: IllegalStateException) { }
        assertTrue(cache.at(0).isEmpty())
        assertTrue(cache.at(1000).isEmpty())
        assertEquals(2, calls)
    }

    @Test fun clippingRetainsOnlyConnectedBoundaryNeighbors() {
        val points = (0..10).map { ChartPoint(it * 1000.0, it.toDouble(), it == 0 || it == 8) }
        assertEquals(listOf(2000.0, 3000.0, 4000.0, 5000.0, 6000.0),
            historyViewportPoints(points, 2500, 5500).map { it.elapsedMs })
        assertEquals(listOf(6000.0, 7000.0), historyViewportPoints(points, 6500, 7500).map { it.elapsedMs })
        assertTrue(historyViewportPoints(emptyList(), 0, 5000).isEmpty())
    }
}
