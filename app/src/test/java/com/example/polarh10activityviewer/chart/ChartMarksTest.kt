package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import org.junit.Assert.*
import org.junit.Test

class ChartMarksTest {
    private fun chart(points: List<ChartPoint>, end: Double = 1000.0) =
        ChartSnapshot(points, end, end, SubscriptionStatus.STOPPED)

    @Test fun heartRateRangesKeepRealExtremaAndSingleValues() {
        val points = listOf(ChartPoint(0.0, 100.0, true), ChartPoint(100.0, 140.0, false),
            ChartPoint(200.0, 120.0, false), ChartPoint(1000.0, 110.0, false))
        val marks = chartMarks(chart(points), ChartKind.HEART_RATE, 2)
        assertEquals(listOf(ChartMark(100.0, 100.0, 140.0, 120.0),
            ChartMark(1000.0, 110.0, 110.0, 110.0)), marks)
        assertEquals(4, points.size)
        assertEquals(140.0, points[1].value!!, 0.0)
    }

    @Test fun cadenceColumnsAverageValidSamplesButRetainStoppedIntervals() {
        val points = listOf(ChartPoint(0.0, 100.0, true), ChartPoint(100.0, 140.0, false),
            ChartPoint(200.0, 0.0, false), ChartPoint(300.0, 120.0, false))
        val marks = chartMarks(chart(points), ChartKind.CADENCE, 1)
        assertEquals(listOf(120.0, 0.0, 120.0), marks.map { it.mean })
        assertEquals(listOf(50.0, 200.0, 300.0), marks.map { it.elapsedMs })
        assertEquals(90.0, chartStatistics(chart(points), ChartKind.CADENCE).mean!!, 0.0)
    }

    @Test fun missingAndBrokenRunsNeverBecomeOneRange() {
        val points = listOf(ChartPoint(0.0, 100.0, true), ChartPoint(100.0, null, false),
            ChartPoint(200.0, 150.0, false), ChartPoint(300.0, 120.0, true))
        val marks = chartMarks(chart(points), ChartKind.HEART_RATE, 1)
        assertEquals(listOf(100.0, 150.0, 120.0), marks.map { it.mean })
        assertTrue(marks.all { it.minimum == it.maximum })
    }

    @Test fun invalidAndOutsideValuesAreExcludedWithoutCreatingMeasurements() {
        val points = listOf(ChartPoint(-1.0, 500.0, false), ChartPoint(0.0, Double.NaN, false),
            ChartPoint(1.0, -20.0, false), ChartPoint(2.0, Double.POSITIVE_INFINITY, false),
            ChartPoint(3.0, 0.0, false), ChartPoint(1001.0, 800.0, false))
        assertTrue(chartMarks(chart(points), ChartKind.HEART_RATE, 10).isEmpty())
        assertEquals(listOf(ChartMark(3.0, 0.0, 0.0, 0.0)), chartMarks(chart(points), ChartKind.CADENCE, 10))
        assertTrue(chartMarks(chart(points), ChartKind.ELECTROCARDIOGRAM, 10).isEmpty())
        assertTrue(chartMarks(chart(emptyList()), ChartKind.CADENCE, 10).isEmpty())
    }

    @Test fun fourHourSessionIsBoundedByDrawingWidthWithoutLosingPeakValues() {
        val points = (0..14_400).map { ChartPoint(it * 1000.0, if (it == 7212) 190.0 else 120.0, it == 0) }
        val snapshot = chart(points, 14_400_000.0)
        val marks = chartMarks(snapshot, ChartKind.HEART_RATE, 80)
        assertEquals(80, marks.size)
        assertEquals(190.0, marks.maxOf { it.maximum }, 0.0)
        assertEquals(listOf("00:00", "240:00"),
            chartTimeTicks(snapshot, 300f, 8f, maximum = 2) { 50f }.map { it.label })
    }
}
