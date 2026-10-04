package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import org.junit.Assert.*
import org.junit.Test

class LiveChartPresentationTest {
    @Test fun historyTicksCoverTheWholeSessionInsteadOfTheLiveWindow() {
        val history = snapshot(1_200_000.0, 1_200_000.0)
        assertEquals(listOf("00:00", "05:00", "10:00", "15:00", "20:00"),
            chartTimeTicks(history, 600f, 8f, maximum = 5) { 50f }.map { it.label })
        val narrow = chartTimeTicks(history, 220f, 8f, maximum = 5) { 90f }
        assertEquals(listOf("00:00", "20:00"), narrow.map { it.label })
        assertEquals(listOf("00:00"),
            chartTimeTicks(snapshot(0.0, 0.0), 300f, 8f, maximum = 5) { 50f }.map { it.label })
    }
    private fun snapshot(end: Double = 60_000.0, window: Double = 5000.0) =
        ChartSnapshot(emptyList(), end, window, SubscriptionStatus.RECEIVING)

    @Test fun nullAndExplicitBreakSplitBothDrawingAndFillSegments() {
        val points = listOf(ChartPoint(0.0, 0.0, true), ChartPoint(1.0, 5.0, false),
            ChartPoint(2.0, null, false), ChartPoint(3.0, -3.0, false), ChartPoint(4.0, 4.0, true))
        val segments = chartSegments(points)
        assertEquals(listOf(listOf(points[0], points[1]), listOf(points[3]), listOf(points[4])), segments)
    }
    @Test fun drawingRetainsEveryEcgSampleAndOriginalNegativeValues() {
        val points = (0 until 650).map { ChartPoint(it * 7.69, if (it % 2 == 0) -100.0 else 200.0, it % 130 == 0) }
        assertEquals(points, chartSegments(points).flatten())
        assertEquals(5, chartSegments(points).size)
    }
    @Test fun meanUsesExistingScaleWithoutExpandingItOrReplacingMissingData() {
        val scale = ChartScale(0.0, 110.0)
        assertEquals(85.0, chartMeanInRange(85.0, scale, true)!!, 0.0)
        assertEquals(0.0, chartMeanInRange(0.0, scale, true)!!, 0.0)
        for (mean in listOf(null, -1.0, 111.0, Double.NaN, Double.POSITIVE_INFINITY))
            assertNull(chartMeanInRange(mean, scale, true))
        assertNull(chartMeanInRange(85.0, scale, false))
        assertEquals(ChartScale(0.0, 110.0), scale)
    }
    @Test fun yTicksRetainBoundsPrecisionAndAvoidDuplicateZeroLabels() {
        assertEquals(listOf(1.0, 0.0), chartYTicks(ChartScale(0.0, 1.0)))
        assertEquals(listOf(1.0, 0.0, -1.0), chartYTicks(ChartScale(-1.0, 1.0)))
    }
    @Test fun ecgSecondsAdaptToActualWidthAndLargerTextWithoutOverlap() {
        val wide = chartTimeTicks(snapshot(), 600f, 8f) { 50f }
        assertEquals(listOf("00:55", "00:56", "00:57", "00:58", "00:59", "01:00"), wide.map { it.label })
        val larger = chartTimeTicks(snapshot(), 340f, 8f) { 100f }
        assertEquals(3, larger.size)
        assertEquals("00:55", larger.first().label); assertEquals("01:00", larger.last().label)
        assertTrue(chartTimeTicks(snapshot(), 100f, 8f) { 80f }.isEmpty())
    }
    @Test fun shortAndLongRunningTimesUseTrueElapsedLabelsWithoutDuplicates() {
        assertEquals(listOf("00:00"), chartTimeTicks(snapshot(400.0, 60_000.0), 300f, 8f) { 50f }.map { it.label })
        assertEquals(listOf("239:00", "239:30", "240:00"),
            chartTimeTicks(snapshot(14_400_000.0, 60_000.0), 600f, 8f) { 70f }.map { it.label })
    }
}
