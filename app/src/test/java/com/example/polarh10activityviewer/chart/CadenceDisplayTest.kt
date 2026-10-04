package com.example.polarh10activityviewer.chart

import org.junit.Assert.*
import org.junit.Test

class CadenceDisplayTest {
    @Test fun denseJitterIsReducedWithoutChangingInputTimesOrReadings() {
        val input = (0..80).map { ChartPoint(it * 250.0, if (it % 2 == 0) 100.0 else 140.0, it == 0) }
        val original = input.toList()
        val output = smoothCadenceForDisplay(input)
        val settled = output.drop(40).map { it.value!! }
        assertTrue(settled.max() - settled.min() < 6.0)
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
        assertEquals(63.21205588, result, 1e-7)
    }
}
