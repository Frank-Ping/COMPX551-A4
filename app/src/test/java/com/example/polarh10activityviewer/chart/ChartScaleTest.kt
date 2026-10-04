package com.example.polarh10activityviewer.chart

import org.junit.Assert.*
import org.junit.Test

class ChartScaleTest {
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

    @Test fun positiveHrUsesZeroAndTenPercentHeadroom() {
        assertEquals(ChartScale(0.0, 132.0), chartScale(points(100.0, 120.0), ChartKind.HEART_RATE))
    }
    @Test fun cadenceBoundsRoundUpToWholeUnits() {
        assertEquals(ChartScale(0.0, 136.0), chartScale(points(123.4), ChartKind.CADENCE))
    }
    @Test fun signedEcgRetainsNegativeValuesAndBothMargins() {
        assertEquals(ChartScale(-130.0, 230.0), chartScale(points(-100.0, 200.0), ChartKind.ELECTROCARDIOGRAM))
    }
    @Test fun singleNegativeEcgValueHasNonzeroRange() {
        assertEquals(ChartScale(-11.0, 1.0), chartScale(points(-10.0), ChartKind.ELECTROCARDIOGRAM))
    }
    @Test fun genuineZerosKeepFiniteScale() {
        assertEquals(ChartScale(0.0, 1.0), chartScale(points(0.0), ChartKind.CADENCE))
        assertEquals(ChartScale(-1.0, 1.0), chartScale(points(0.0), ChartKind.ELECTROCARDIOGRAM))
    }
    @Test fun missingValuesDoNotBecomeMeasurements() {
        val missing = points(null, null)
        assertEquals(ChartScale(0.0, 1.0), chartScale(missing, ChartKind.HEART_RATE))
        assertTrue(missing.all { it.value == null })
        assertEquals(ChartScale(0.0, 110.0), chartScale(points(null, 100.0), ChartKind.HEART_RATE))
    }
}
