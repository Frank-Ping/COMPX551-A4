package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import org.junit.Assert.*
import org.junit.Test

class LiveChartPresentationTest {
    @Test fun heartRateReferenceWaitsTenSecondsFromFirstValidReading() {
        val points = listOf(ChartPoint(0.0, null, true), ChartPoint(1000.0, 0.0, false),
            ChartPoint(2000.0, Double.NaN, false), ChartPoint(5000.0, 90.0, true))
        val chart = ChartSnapshot(points, 14_999.0, 300_000.0, SubscriptionStatus.RECEIVING)
        assertFalse(heartRateMeanReady(chart))
        assertTrue(heartRateMeanReady(chart.copy(endMs = 15_000.0)))
        assertFalse(heartRateMeanReady(chart.copy(points = points.dropLast(1), endMs = 30_000.0)))
        assertFalse(heartRateMeanReady(chart.copy(points = emptyList())))
        assertFalse(heartRateMeanReady(chart.copy(endMs = 15_000.0, windowMs = 9000.0)))
        assertFalse(heartRateMeanReady(chart.copy(status = SubscriptionStatus.STOPPED)))
    }

    @Test fun cadenceReferenceWaitsForTenSecondsAtLiveAndHistoryRates() {
        for (interval in listOf(250, 1000)) {
            val points = (0..10_000 step interval).map {
                ChartPoint(it.toDouble(), 0.0, it == 0)
            }
            val chart = ChartSnapshot(points, 10_000.0, 300_000.0, SubscriptionStatus.RECEIVING)
            assertFalse(cadenceMeanReady(chart.copy(points = points.dropLast(1))))
            assertTrue(cadenceMeanReady(chart))
            assertFalse(cadenceMeanReady(chart.copy(windowMs = 9000.0)))
            assertFalse(cadenceMeanReady(chart.copy(points = emptyList())))
        }
    }

    @Test fun cadenceReferenceDoesNotCountMissingBrokenOrUnobservedTime() {
        val points = (0..15_000 step 1000).map { ChartPoint(it.toDouble(), 120.0, it == 0) }
        val chart = ChartSnapshot(points, 15_000.0, 300_000.0, SubscriptionStatus.STOPPED)
        for (value in listOf(null, Double.NaN, Double.POSITIVE_INFINITY, -1.0)) {
            val broken = points.map { if (it.elapsedMs == 5000.0) it.copy(value = value) else it }
            assertFalse(cadenceMeanReady(chart.copy(points = broken)))
            assertTrue(cadenceMeanReady(chart.copy(points = broken + ChartPoint(16_000.0, 120.0, false), endMs = 16_000.0)))
        }
        assertFalse(cadenceMeanReady(chart.copy(points = points.map {
            if (it.elapsedMs == 6000.0) it.copy(breakBefore = true) else it
        })))
        assertFalse(cadenceMeanReady(chart.copy(points = listOf(points.first(), points.last()))))
    }

    @Test fun sessionStatisticsFollowHistoryReplacementPauseResumeAndReset() {
        val history = com.example.polarh10activityviewer.history.MotionHistory()
        history.reset("first")
        history.onSubscriptionState(SubscriptionStatus.RECEIVING)
        fun record(time: Long, cadence: Double?) = history.record(time,
            com.example.polarh10activityviewer.motion.StepState(cadence = cadence, receivedAcc = true), false, 1)
        fun statistics() = chartStatistics(ChartSnapshot(history.snapshot().map {
            ChartPoint(it.elapsedMs.toDouble(), it.cadence, it.breakBefore)
        }, 400_000.0, 400_000.0, SubscriptionStatus.STOPPED), ChartKind.CADENCE)
        record(250, 200.0)
        record(750, 100.0)
        record(1000, null)
        record(2000, 0.0)
        record(310_000, 140.0)
        assertEquals(ChartStatistics(80.0, 140.0, 0.0), statistics())
        history.stop()
        record(320_000, 300.0)
        assertEquals(ChartStatistics(80.0, 140.0, 0.0), statistics())
        history.resume()
        record(330_000, 160.0)
        assertEquals(ChartStatistics(100.0, 160.0, 0.0), statistics())
        history.reset("second")
        assertEquals(ChartStatistics(), statistics())
    }

    @Test fun cadenceMeanUsesOnlySelectedWindowSamplesAndIncludesRealZeros() {
        val points = listOf(
            ChartPoint(999.0, 200.0, false),
            ChartPoint(1000.0, 0.0, true),
            ChartPoint(2000.0, null, false),
            ChartPoint(3000.0, 120.0, true),
            ChartPoint(4000.0, 180.0, false),
            ChartPoint(4001.0, 300.0, false))
        val chart = ChartSnapshot(points, 4000.0, 3000.0, SubscriptionStatus.STOPPED)
        assertEquals(100.0, chartStatistics(chart, ChartKind.CADENCE).mean!!, 0.0)
        assertEquals(180.0, chartStatistics(chart, ChartKind.CADENCE).maximum!!, 0.0)
        // Moving the window drops old samples instead of retaining a whole-session average.
        assertEquals(150.0, chartStatistics(chart.copy(windowMs = 1000.0), ChartKind.CADENCE).mean!!, 0.0)
        assertEquals(points, chart.points)
    }

    @Test fun cadenceMeanHandlesMissingInvalidAndZeroOnlyCharts() {
        val invalid = listOf(null, Double.NaN, Double.POSITIVE_INFINITY, -1.0)
            .mapIndexed { index, value -> ChartPoint(index.toDouble(), value, false) }
        val chart = ChartSnapshot(invalid, 5.0, 5.0, SubscriptionStatus.STOPPED)
        assertEquals(ChartStatistics(), chartStatistics(chart, ChartKind.CADENCE))
        assertEquals(ChartStatistics(), chartStatistics(chart.copy(points = emptyList()), ChartKind.CADENCE))
        assertEquals(ChartStatistics(0.0, 0.0, 0.0), chartStatistics(chart.copy(
            points = listOf(ChartPoint(0.0, 0.0, true))), ChartKind.CADENCE))
    }

    @Test fun heartRateMeanAndMaximumShareTheSelectedSamples() {
        val chart = ChartSnapshot(listOf(
            ChartPoint(0.0, 200.0, false), ChartPoint(1000.0, 0.0, false),
            ChartPoint(2000.0, 90.0, true), ChartPoint(3000.0, null, false),
            ChartPoint(4000.0, 110.0, true), ChartPoint(4001.0, 250.0, false)),
            4000.0, 3000.0, SubscriptionStatus.STOPPED)
        assertEquals(ChartStatistics(100.0, 110.0, 90.0), chartStatistics(chart, ChartKind.HEART_RATE))
        assertEquals(ChartStatistics(110.0, 110.0, 110.0), chartStatistics(chart.copy(windowMs = 500.0), ChartKind.HEART_RATE))
        assertEquals(ChartStatistics(), chartStatistics(chart, ChartKind.ELECTROCARDIOGRAM))
        assertEquals(ChartStatistics(), chartStatistics(chart.copy(points = listOf(
            ChartPoint(2000.0, Double.NaN, false), ChartPoint(3000.0, 0.0, false))), ChartKind.HEART_RATE))
    }

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
