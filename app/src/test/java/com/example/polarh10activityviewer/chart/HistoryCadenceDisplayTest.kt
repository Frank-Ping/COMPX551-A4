package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import org.junit.Assert.*
import org.junit.Test

class HistoryCadenceDisplayTest {
    private fun chart(points: List<ChartPoint>, end: Double = 60_000.0) =
        ChartSnapshot(points, end, end, SubscriptionStatus.STOPPED)

    @Test fun preservesActualPeakTroughAndTheirTimeOrderWithoutChangingStatistics() {
        val points = listOf(120.0, 180.0, 140.0, 80.0, 130.0).mapIndexed { index, value ->
            ChartPoint(index * 1000.0, value, index == 0)
        }
        val snapshot = chart(points)
        val before = chartStatistics(snapshot, ChartKind.CADENCE)
        val retained = historyCadenceSegments(snapshot, 1).single()
        assertEquals(listOf(points[0], points[1], points[3], points[4]), retained)
        assertEquals(before, chartStatistics(snapshot, ChartKind.CADENCE))
        assertEquals(points, snapshot.points)
        val reversedExtrema = points.map { it.copy(value = 260.0 - it.value!!) }
        assertEquals(listOf(0.0, 1000.0, 3000.0, 4000.0),
            historyCadenceSegments(chart(reversedExtrema), 1).single().map { it.elapsedMs })
    }

    @Test fun retainsBothEndsOfStopsEvenWithinOneBucket() {
        val points = listOf(120.0, 125.0, 0.0, 0.0, 0.0, 130.0, 135.0).mapIndexed { index, value ->
            ChartPoint(index * 1000.0, value, index == 0)
        }
        assertEquals(listOf(0.0, 1000.0, 2000.0, 4000.0, 5000.0, 6000.0),
            historyCadenceSegments(chart(points), 1).single().map { it.elapsedMs })
    }

    @Test fun invalidReadingsAndExplicitBreaksNeverJoinSegments() {
        val points = listOf(100.0, null, 110.0, Double.NaN, 120.0, -1.0, 130.0, 140.0).mapIndexed { index, value ->
            ChartPoint(index * 1000.0, value, index == 7)
        }
        assertEquals(listOf(100.0, 110.0, 120.0, 130.0, 140.0),
            historyCadenceSegments(chart(points), 1).map { it.single().value })
        assertTrue(historyCadenceSegments(chart(emptyList()), 100).isEmpty())
    }

    @Test fun fourHourHistoryIsBoundedByScreenWidthWhileKeepingSingleSampleExtrema() {
        val points = (0..14_400).map { second ->
            ChartPoint(second * 1000.0, when (second) { 7321 -> 210.0; 7322 -> 45.0; else -> 130.0 }, second == 0)
        }
        val reduced = historyCadenceSegments(chart(points, 14_400_000.0), 80).single()
        assertTrue(reduced.size <= 320)
        assertEquals(points.first(), reduced.first())
        assertEquals(points.last(), reduced.last())
        assertTrue(reduced.contains(points[7321]))
        assertTrue(reduced.contains(points[7322]))
        assertTrue(reduced.zipWithNext().all { (a, b) -> a.elapsedMs < b.elapsedMs })
        assertEquals(points.take(3), historyCadenceSegments(chart(points.take(3), 2000.0), 80).single())
    }
}
