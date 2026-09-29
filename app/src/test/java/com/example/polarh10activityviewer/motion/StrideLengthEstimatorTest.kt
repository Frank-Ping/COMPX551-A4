package com.example.polarh10activityviewer.motion

import org.junit.Assert.*
import org.junit.Test

class StrideLengthEstimatorTest {
    private fun point(ms: Long, value: Double) = PreparedAcc(ms * 1_000_000, value, value, 10.0, 1.0)

    @Test fun includesBothPeaksButNotSamplesBetweenLaterPeakAndConfirmation() {
        val strides = StrideLengthEstimator()
        strides.receive(point(0, 26.0))
        strides.receive(point(10, 10.0))
        assertNull(strides.accept(0))
        strides.receive(point(250, 20.0))
        strides.receive(point(260, 1000.0))
        assertEquals(1.0, strides.accept(250_000_000)!!, 1e-10)
        strides.receive(point(500, 10.0))
        // The post-peak sample belongs to the next interval instead.
        assertEquals(0.5 * Math.pow(990.0, 0.25), strides.accept(500_000_000)!!, 1e-10)
    }

    @Test fun rejectedPeakNeverChangesReferenceAndFourStepsCommitThreeLengthsOnce() {
        val strides = StrideLengthEstimator()
        val sequence = StepSequence()
        fun accept(ms: Long): List<StepCandidate> = sequence.accept(StepCandidate(ms * 1_000_000, 26.0)) {
            strides.accept(ms * 1_000_000)
        }
        strides.receive(point(0, 26.0))
        assertTrue(accept(0).isEmpty())
        strides.receive(point(10, 10.0))
        strides.receive(point(200, 26.0))
        assertTrue(accept(200).isEmpty())
        // If rejection moved the reference, the first interval would have zero amplitude.
        strides.receive(point(250, 26.0))
        assertTrue(accept(250).isEmpty())
        strides.receive(point(260, 10.0))
        strides.receive(point(500, 26.0))
        assertTrue(accept(500).isEmpty())
        assertEquals(0L, sequence.totalSteps)
        strides.receive(point(510, 10.0))
        strides.receive(point(750, 26.0))
        val committed = accept(750)
        assertEquals(listOf(0L, 250_000_000L, 500_000_000L, 750_000_000L), committed.map { it.timeStamp })
        assertEquals(listOf(null, 1.0, 1.0, 1.0), committed.map { it.length })
        assertEquals(4L, sequence.totalSteps)
        assertTrue(accept(750).isEmpty())
        strides.receive(point(760, 10.0))
        strides.receive(point(1000, 26.0))
        assertEquals(1.0, accept(1000).single().length!!, 0.0)
    }

    @Test fun fullTwoSecondIntervalRetainsBothEndpointsAt100Hz() {
        val strides = StrideLengthEstimator()
        strides.receive(point(0, 26.0))
        assertNull(strides.accept(0))
        for (i in 1..200) strides.receive(point(i * 10L, 10.0))
        assertEquals(1.0, strides.accept(2_000_000_000)!!, 1e-10)
    }

    @Test fun longWarmupAndNewSegmentCannotCarryOldExtremaOrStartingDistance() {
        val strides = StrideLengthEstimator()
        for (i in 0..10_000) strides.receive(point(i * 10L, if (i == 0) 10000.0 else 10.0))
        assertNull(strides.accept(100_000_000_000))
        strides.receive(point(100250, 26.0))
        assertEquals(1.0, strides.accept(100_250_000_000)!!, 1e-10)
        strides.clear()
        strides.receive(point(0, 10.0))
        assertNull(strides.accept(0))
        strides.receive(point(250, 10.0))
        assertEquals(0.0, strides.accept(250_000_000)!!, 0.0)
    }
}
