package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import org.junit.Assert.*
import org.junit.Test

class RecentSessionChartTest {
    private fun full(points: List<ChartPoint>, end: Double) =
        ChartSnapshot(points, end, end, SubscriptionStatus.RECEIVING)

    @Test fun recentMinuteDrivesStatisticsScaleAndEndpointLabelsWithoutChangingHistory() {
        val points = (0..330).map { second ->
            ChartPoint(second * 1000.0, if (second < 270) 250.0 else 100.0, second == 0)
        }
        val history = full(points, 330_000.0)
        for (kind in listOf(ChartKind.HEART_RATE, ChartKind.CADENCE)) {
            val recent = recentSessionChart(history, kind)
            assertEquals(270_000.0, recent.startMs, 0.0)
            assertEquals(61, recent.points.size)
            assertEquals(100.0, chartStatistics(recent, kind).mean!!, 0.0)
            assertEquals(100.0, chartStatistics(recent, kind).maximum!!, 0.0)
            assertTrue(chartScale(recent.points, kind).upper < 250.0)
            assertEquals(listOf("04:30", "05:30"),
                chartTimeTicks(recent, 400f, 8f, 2) { 40f }.map { it.label })
        }
        assertEquals(331, history.points.size)
        assertEquals(250.0, chartStatistics(history, ChartKind.CADENCE).maximum!!, 0.0)
        assertNull(history.meanReady)
        assertNull(history.cadenceDisplayPoints)
    }

    @Test fun startupUsesAvailableTimeAndQualificationDoesNotRestartWhenOldRunLeavesWindow() {
        val initial = (0..10).map { ChartPoint(it * 1000.0, 120.0, it == 0) }
        for (kind in listOf(ChartKind.HEART_RATE, ChartKind.CADENCE)) {
            val early = recentSessionChart(full(initial.take(10), 9_000.0), kind)
            assertEquals(0.0, early.startMs, 0.0)
            assertEquals(false, early.meanReady)
            assertEquals(true, recentSessionChart(full(initial, 10_000.0), kind).meanReady)
            val returned = recentSessionChart(full(initial + ChartPoint(120_000.0, 140.0, true), 120_000.0), kind)
            assertEquals(1, returned.points.size)
            assertEquals(true, returned.meanReady)
            assertEquals(140.0, chartStatistics(returned, kind).mean!!, 0.0)
            val empty = recentSessionChart(full(initial, 120_000.0), kind)
            assertTrue(empty.points.isEmpty())
            assertNull(chartStatistics(empty, kind).mean)
            assertNull(chartMeanInRange(null, chartScale(empty.points, kind), false))
        }
    }

    @Test fun rollingWindowPreservesSmoothedValuesSlopesAndRealGaps() {
        val points = (0..125).map { second ->
            ChartPoint(second * 1000.0, if (second == 80) null else if (second % 2 == 0) 150.0 else 90.0,
                second == 0 || second == 100)
        }
        val smoothed = smoothCadenceForDisplay(points)
        val recent = recentSessionChart(full(points, 125_000.0), ChartKind.CADENCE)
        val display = recent.cadenceDisplayPoints!!
        assertEquals(smoothed.drop(63), display)
        val originalSegment = chartSegments(smoothed).first()
        val croppedSegment = chartSegments(display).first()
        assertEquals(cadenceDisplayTangents(originalSegment)[65], cadenceDisplayTangents(croppedSegment)[2], 0.0)
        assertEquals(3, chartSegments(display).size)
        val earlier = recentSessionChart(full(points.dropLast(1), 124_000.0), ChartKind.CADENCE)
        assertEquals(earlier.cadenceDisplayPoints!!.first { it.elapsedMs == 65_000.0 },
            display.first { it.elapsedMs == 65_000.0 })
    }

    @Test fun frozenTimeStaysFixedNewSessionRequalifiesAndEcgIsUntouched() {
        val session = full((0..90).map { ChartPoint(it * 1000.0, 100.0, it == 0) }, 90_000.0)
        val paused = session.copy(status = SubscriptionStatus.STOPPED)
        assertEquals(30_000.0, recentSessionChart(paused, ChartKind.CADENCE).startMs, 0.0)
        assertEquals(90_000.0, recentSessionChart(paused, ChartKind.CADENCE).endMs, 0.0)
        val restarted = recentSessionChart(full(emptyList(), 0.0), ChartKind.CADENCE)
        assertEquals(false, restarted.meanReady)
        assertEquals(0.0, restarted.startMs, 0.0)
        assertSame(session, recentSessionChart(session, ChartKind.ELECTROCARDIOGRAM))
    }
}
