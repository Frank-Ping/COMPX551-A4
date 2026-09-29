package com.example.polarh10activityviewer

import org.junit.Assert.*
import org.junit.Test

class CadenceWindowTest {
    private fun peaks(vararg ms: Long) = ms.map { StepCandidate(it * 1_000_000L, 12.0) }

    @Test fun shortWindowUsesTimeSinceWarmupAndKeepsFractionalResult() {
        val window = CadenceWindow()
        window.receive(peaks(1100, 1600, 2100, 2600), 2_800_000_000L)
        assertEquals(240.0 / 1.8, window.value(2_800_000_000L, 1_000_000_000L), 1e-10)
        assertEquals(0.0, window.value(1_000_000_000L, 1_000_000_000L), 0.0)
    }

    @Test fun fullWindowIsLeftOpenRightClosedAndExcludesFuturePeaks() {
        val window = CadenceWindow()
        window.receive(peaks(1000, 1200, 2000, 5000, 6000), 6_000_000_000L)
        assertEquals(48.0, window.value(6_000_000_000L, 0), 0.0)
        assertEquals(36.0, window.value(5_900_000_000L, 0), 0.0)
    }

    @Test fun backfilledPeaksUseOriginalTimesRatherThanConfirmationTime() {
        val window = CadenceWindow()
        window.receive(peaks(100, 2100, 4100, 6100), 6_200_000_000L)
        assertEquals(36.0, window.value(6_200_000_000L, 0), 0.0)
    }

    @Test fun noPeaksAndExactlyTwoSecondsSinceLastPeakShowZero() {
        val window = CadenceWindow()
        assertEquals(0.0, window.value(1_000_000_000L, 0), 0.0)
        window.receive(peaks(1000, 1500, 2000, 2500), 2_600_000_000L)
        assertTrue(window.value(4_499_999_999L, 0) > 0.0)
        assertEquals(0.0, window.value(4_500_000_000L, 0), 0.0)
    }

    @Test fun displayEstimatesDoNotEvictPeaksButNewSensorDataDoes() {
        val window = CadenceWindow()
        window.receive(peaks(1000, 1500, 2000, 2500), 2_600_000_000L)
        val original = window.value(2_600_000_000L, 0)
        assertEquals(0.0, window.value(20_000_000_000L, 0), 0.0)
        assertEquals(original, window.value(2_600_000_000L, 0), 0.0)
        window.receive(emptyList(), 10_000_000_000L)
        assertEquals(0.0, window.value(2_600_000_000L, 0), 0.0)
        window.clear()
        assertEquals(0.0, window.value(1, 0), 0.0)
    }
}
