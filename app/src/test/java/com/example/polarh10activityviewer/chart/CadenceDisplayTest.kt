package com.example.polarh10activityviewer.chart

import org.junit.Assert.*
import org.junit.Test

class CadenceDisplayTest {
    @Test fun denseJitterIsReducedWithoutChangingInputTimesOrReadings() {
        val input = (0..80).map { ChartPoint(it * 250.0, if (it % 2 == 0) 100.0 else 140.0, it == 0) }
        val original = input.toList()
        val output = smoothCadenceForDisplay(input)
        val settled = output.drop(40).map { it.value!! }
        assertTrue(settled.max() - settled.min() < 3.0)
        assertEquals(original, input)
        assertEquals(input.map { it.elapsedMs }, output.map { it.elapsedMs })
        assertEquals(input.map { it.breakBefore }, output.map { it.breakBefore })
        assertTrue(output.all { it.value!! in 100.0..140.0 })
    }

    @Test fun nullsAndExplicitBreaksResetWhileOrdinaryConnectionsRemainSmooth() {
        val input = listOf(ChartPoint(0.0, 120.0, true), ChartPoint(250.0, 0.0, false),
            ChartPoint(500.0, null, false), ChartPoint(750.0, 0.0, false),
            ChartPoint(1000.0, 140.0, true))
        val output = smoothCadenceForDisplay(input)
        assertTrue(output[1].value!! in 0.0..120.0)
        assertTrue(output[1].value!! > 0.0)
        assertNull(output[2].value)
        assertEquals(0.0, output[3].value!!, 0.0)
        assertEquals(140.0, output[4].value!!, 0.0)
        assertEquals(listOf(2, 1, 1), chartSegments(output).map { it.size })
        assertEquals(emptyList<ChartPoint>(), smoothCadenceForDisplay(emptyList()))
    }

    @Test fun responseDependsOnElapsedTimeRatherThanSampleCount() {
        val sparse = listOf(ChartPoint(0.0, 0.0, true), ChartPoint(1000.0, 100.0, false))
        val dense = listOf(sparse.first()) + (1..4).map { ChartPoint(it * 250.0, 100.0, false) }
        val result = smoothCadenceForDisplay(sparse).last().value!!
        assertEquals(result, smoothCadenceForDisplay(dense).last().value!!, 1e-10)
        assertEquals(39.34693403, result, 1e-7)
    }

    @Test fun halfSecondSamplesKeepTheSameTimedFilterResponse() {
        val input = listOf(ChartPoint(0.0, 0.0, true)) +
            (1..4).map { ChartPoint(it * 500.0, 100.0, false) }
        val output = smoothCadenceForDisplay(input)
        assertEquals(39.34693403, output[2].value!!, 1e-7)
        assertEquals(63.21205588, output[4].value!!, 1e-7)
        assertEquals(input.map { it.elapsedMs }, output.map { it.elapsedMs })
    }

    @Test fun risingCurveDoesNotFlattenAtEveryHalfSecondSample() {
        for (times in listOf(listOf(0.0, 250.0, 500.0, 750.0),
            listOf(0.0, 500.0, 1000.0, 1500.0), listOf(0.0, 500.0, 1250.0, 2250.0))) {
            val points = times.map { ChartPoint(it, 100.0 + it * 0.02, false) }
            val slopes = cadenceDisplayTangents(points)
            slopes.forEach { assertEquals(0.02, it, 1e-12) }
            // A linear trend must remain linear throughout each cubic, at either sample rate.
            points.zipWithNext().forEachIndexed { index, (a, b) ->
                for (step in 0..10) {
                    val fraction = step / 10.0
                    assertEquals(a.value!! + (b.value!! - a.value!!) * fraction,
                        cubicValue(a, b, slopes[index], slopes[index + 1], fraction), 1e-10)
                }
            }
        }
    }

    @Test fun irregularCurvesStayWithinEndpointsAndDoNotJoinAcrossGaps() {
        val input = listOf(ChartPoint(0.0, 100.0, true), ChartPoint(500.0, 120.0, false),
            ChartPoint(1250.0, 130.0, false), ChartPoint(1750.0, 90.0, false),
            ChartPoint(2500.0, 90.0, false), ChartPoint(3000.0, null, false),
            ChartPoint(3500.0, 150.0, false), ChartPoint(4000.0, 160.0, true))
        val original = input.toList()
        val segments = chartSegments(input)
        assertEquals(listOf(5, 1, 1), segments.map { it.size })
        val slopes = cadenceDisplayTangents(segments.first())
        assertTrue(slopes[1] > 0.0)
        assertEquals(0.0, slopes[2], 0.0) // A real peak has a horizontal tangent.
        assertEquals(0.0, slopes[3], 0.0)
        segments.first().zipWithNext().forEachIndexed { index, (a, b) ->
            val lower = minOf(a.value!!, b.value!!)
            val upper = maxOf(a.value!!, b.value!!)
            for (step in 0..100) {
                val value = cubicValue(a, b, slopes[index], slopes[index + 1], step / 100.0)
                assertTrue(value >= lower - 1e-10 && value <= upper + 1e-10)
            }
        }
        assertEquals(listOf(0.0), cadenceDisplayTangents(segments[1]))
        assertEquals(emptyList<Double>(), cadenceDisplayTangents(emptyList()))
        assertEquals(original, input)
    }

    private fun cubicValue(a: ChartPoint, b: ChartPoint, leftSlope: Double, rightSlope: Double,
        t: Double): Double {
        val thirdTime = (b.elapsedMs - a.elapsedMs) / 3.0
        val left = a.value!! + leftSlope * thirdTime
        val right = b.value!! - rightSlope * thirdTime
        val remaining = 1.0 - t
        return remaining * remaining * remaining * a.value!! + 3 * remaining * remaining * t * left +
            3 * remaining * t * t * right + t * t * t * b.value!!
    }
}
