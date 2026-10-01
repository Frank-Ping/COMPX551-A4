package com.example.polarh10activityviewer.chart

import org.junit.Assert.*
import org.junit.Test

class ChartScaleTest {
    private fun points(vararg values: Double?) = values.mapIndexed { i, v -> ChartPoint(i.toDouble(), v, false) }

    @Test fun positiveHrUsesZeroAndTenPercentHeadroom() {
        assertEquals(ChartScale(0.0, 132.0), chartScale(points(100.0, 120.0), ChartKind.HEART_RATE))
    }
    @Test fun speedBoundsRoundOutwardsToOneDecimal() {
        val data = points(4.56)
        assertEquals(ChartScale(0.0, 5.1), chartScale(data, ChartKind.SPEED))
        assertEquals(4.56, data.single().value!!, 0.0)
        assertEquals("5.1", chartScaleLabel(5.1, ChartKind.SPEED))
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
