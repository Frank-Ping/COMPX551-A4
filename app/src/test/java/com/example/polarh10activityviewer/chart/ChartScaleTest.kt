package com.example.polarh10activityviewer.chart

import org.junit.Assert.*
import org.junit.Test

class ChartScaleTest {
    @Test fun liveScaleExpandsImmediatelyAndRequiresTenStableSecondsToShrink() {
        val tracker = LiveScaleTracker()
        val wide = ChartScale(0.0, 200.0, 50.0)
        val narrow = ChartScale(0.0, 150.0, 50.0)
        assertEquals(wide, tracker.update(wide, 0.0))
        assertEquals(wide, tracker.update(narrow, 1000.0))
        assertEquals(wide, tracker.update(narrow, 10_999.0))
        assertEquals(narrow, tracker.update(narrow, 11_000.0))
        assertEquals(wide, tracker.update(wide, 11_001.0))
        tracker.update(narrow, 12_000.0)
        tracker.update(wide, 20_000.0)
        assertEquals(wide, tracker.update(narrow, 22_000.0))
        assertEquals(wide, tracker.update(narrow, 22_000.0))
        assertEquals(narrow, tracker.update(narrow, 32_000.0))
    }

    @Test fun rangeExpansionPreservesOppositeBoundAndResetDropsOldScale() {
        val tracker = LiveScaleTracker()
        tracker.update(ChartScale(80.0, 160.0, 20.0), 20_000.0)
        assertEquals(ChartScale(60.0, 160.0, 20.0),
            tracker.update(ChartScale(60.0, 140.0, 20.0), 21_000.0))
        assertEquals(ChartScale(80.0, 160.0, 20.0),
            tracker.update(ChartScale(80.0, 160.0, 20.0), 0.0))
        tracker.update(ChartScale(0.0, 1.0), 1000.0, hasData = false)
        assertEquals(ChartScale(100.0, 180.0, 20.0),
            tracker.update(ChartScale(100.0, 180.0, 20.0), 2000.0))
    }

    @Test fun bandedTicksAreRegularAndCoverSignedEcgWithZero() {
        assertEquals(listOf(160.0, 140.0, 120.0, 100.0, 80.0),
            chartYTicks(ChartScale(80.0, 160.0, 20.0)))
        assertEquals(listOf(200.0, 150.0, 100.0, 50.0, 0.0),
            chartYTicks(ChartScale(0.0, 200.0, 50.0)))
        val ecg = chartScale(points(-250.0, 500.0), ChartKind.ELECTROCARDIOGRAM)
        assertEquals(ChartScale(-400.0, 600.0, 200.0), ecg)
        assertEquals(listOf(600.0, 400.0, 200.0, 0.0, -200.0, -400.0), chartYTicks(ecg))
    }
    private fun points(vararg values: Double?) = values.mapIndexed { i, v -> ChartPoint(i.toDouble(), v, false) }

    @Test fun savedMeanOutsideDownsampledExtremaRemainsVisibleWithoutChangingHistory() {
        val history = points(80.0, 90.0)
        val scale = chartScale(history, ChartKind.HEART_RATE, 125.0)
        assertTrue(scale.upper >= 125.0)
        assertEquals(125.0, chartMeanInRange(125.0, scale, true)!!, 0.0)
        assertEquals(listOf(80.0, 90.0), history.map { it.value })
    }

    @Test fun absentHistoryDoesNotBecomeMeasuredDataFromItsSavedMean() {
        assertEquals(ChartScale(0.0, 1.0), chartScale(points(null), ChartKind.HEART_RATE, 125.0))
        assertEquals(chartScale(points(80.0), ChartKind.HEART_RATE),
            chartScale(points(80.0), ChartKind.HEART_RATE, Double.NaN))
    }

    @Test fun heartRateUsesTwentyBpmBandsAndMinimumEightyBpmSpan() {
        assertEquals(ChartScale(60.0, 140.0, 20.0), chartScale(points(100.0, 120.0), ChartKind.HEART_RATE))
    }
    @Test fun cadenceBoundsRoundUpToFiftyWithHeadroom() {
        assertEquals(ChartScale(0.0, 150.0, 50.0), chartScale(points(123.4), ChartKind.CADENCE))
    }
    @Test fun signedEcgRetainsNegativeValuesAndBothMargins() {
        assertEquals(ChartScale(-200.0, 300.0, 100.0), chartScale(points(-100.0, 200.0), ChartKind.ELECTROCARDIOGRAM))
    }
    @Test fun singleNegativeEcgValueHasNonzeroRange() {
        assertEquals(ChartScale(-15.0, 5.0, 5.0), chartScale(points(-10.0), ChartKind.ELECTROCARDIOGRAM))
    }
    @Test fun genuineZerosKeepFiniteScale() {
        assertEquals(ChartScale(0.0, 50.0, 50.0), chartScale(points(0.0), ChartKind.CADENCE))
        assertEquals(ChartScale(-1.0, 1.0, 1.0), chartScale(points(0.0), ChartKind.ELECTROCARDIOGRAM))
    }
    @Test fun missingValuesDoNotBecomeMeasurements() {
        val missing = points(null, null)
        assertEquals(ChartScale(0.0, 1.0), chartScale(missing, ChartKind.HEART_RATE))
        assertTrue(missing.all { it.value == null })
        assertEquals(ChartScale(60.0, 140.0, 20.0), chartScale(points(null, 100.0), ChartKind.HEART_RATE))
    }
}
