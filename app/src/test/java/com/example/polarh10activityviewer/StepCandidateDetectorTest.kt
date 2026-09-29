package com.example.polarh10activityviewer

import org.junit.Assert.*
import org.junit.Test

class StepCandidateDetectorTest {
    private fun point(t: Long, s: Double, mean: Double? = 10.0, sd: Double? = 0.0) =
        PreparedAcc(t, s, s, mean, sd)

    @Test fun warmupCannotStartCycleButLastWarmupValueCanPrecedeFirstCrossing() {
        val detector = StepCandidateDetector()
        assertNull(detector.receive(point(0, 12.0, null, null)))
        assertNull(detector.receive(point(10, 10.0, null, null)))
        assertNull(detector.receive(point(20, 11.0)))
        assertEquals(StepCandidate(20, 11.0), detector.receive(point(30, 10.0)))
    }

    @Test fun sameCurrentThresholdMustSeparatePreviousAndCurrentValues() {
        val detector = StepCandidateDetector()
        detector.receive(point(0, 11.0, 11.0))
        // Both values are above the new H=10.5: this is not an upward crossing.
        detector.receive(point(10, 11.2))
        assertNull(detector.receive(point(20, 10.0)))
        detector.receive(point(30, 10.5))
        // Equality at current H does not start a cycle; previous equality is allowed.
        assertNull(detector.receive(point(40, 10.5)))
        detector.receive(point(50, 10.6))
        assertEquals(StepCandidate(50, 10.6), detector.receive(point(60, 10.0)))
    }

    @Test fun deviationAboveMinimumRaisesThreshold() {
        val detector = StepCandidateDetector()
        detector.receive(point(0, 10.0, sd = 1.0))
        detector.receive(point(10, 10.8, sd = 1.0))
        assertNull(detector.receive(point(20, 10.0, sd = 1.0)))
        detector.receive(point(30, 11.0, sd = 1.0))
        detector.receive(point(40, 11.1, sd = 1.0))
        assertEquals(StepCandidate(40, 11.1), detector.receive(point(50, 9.9, sd = 1.0)))
    }

    @Test fun frozenThresholdsAndMultiplePeaksProduceOneCandidateAtHighestPeakTime() {
        val detector = StepCandidateDetector()
        detector.receive(point(0, 10.0))
        detector.receive(point(10, 11.0))
        assertNull(detector.receive(point(20, 12.0, mean = 30.0)))
        assertNull(detector.receive(point(30, 10.2, mean = 20.0)))
        assertNull(detector.receive(point(40, 13.0, mean = 0.0)))
        assertNull(detector.receive(point(50, 13.0)))
        // The first timestamp wins for equal maxima; fall time is not peak time.
        assertEquals(StepCandidate(40, 13.0), detector.receive(point(60, 10.0, mean = 0.0)))
        assertNull(detector.receive(point(70, 9.0)))
        detector.receive(point(80, 11.0))
        assertEquals(StepCandidate(80, 11.0), detector.receive(point(90, 10.0)))
    }

    @Test fun exactTwoSecondFallAcceptedButLaterFallDiscardedAndNewCrossingRequired() {
        val detector = StepCandidateDetector()
        detector.receive(point(0, 10.0))
        detector.receive(point(10, 11.0))
        assertEquals(StepCandidate(10, 11.0), detector.receive(point(2_000_000_010, 10.0)))
        detector.clear()
        detector.receive(point(0, 10.0))
        detector.receive(point(10, 11.0))
        detector.receive(point(1_900_000_010, 12.0))
        assertNull(detector.receive(point(2_000_000_011, 10.0)))
        assertNull(detector.latestCandidate)
        detector.receive(point(2_010_000_011, 11.0))
        assertEquals(StepCandidate(2_010_000_011, 11.0),
            detector.receive(point(2_020_000_011, 10.0)))
    }

    @Test fun clearDiscardsUnfinishedCycleAndPreviousCrossingReference() {
        val detector = StepCandidateDetector()
        detector.receive(point(0, 10.0))
        detector.receive(point(10, 11.0))
        detector.clear()
        assertNull(detector.receive(point(20, 12.0)))
        assertNull(detector.receive(point(30, 10.0)))
        assertNull(detector.latestCandidate)
    }

    @Test fun rawPipelineKeepsUpdatingWindowDuringCycleAndResetsAcrossGap() {
        val processor = AccPreprocessor()
        val detector = StepCandidateDetector()
        val candidates = mutableListOf<StepCandidate>()
        fun feed(index: Int, x: Int, gap: Long? = null) {
            if (gap != null) detector.clear()
            val prepared = processor.receive(AccSample(index * 10_000_000L, x, 0, 0, gap))
            detector.receive(prepared)?.let(candidates::add)
        }
        for (i in 0..103) feed(i, 1000)
        for (i in 104..113) feed(i, 2000)
        assertTrue(processor.latest!!.previousMean!! > 9.8)
        assertTrue(candidates.isEmpty())
        for (i in 114..118) feed(i, 900)
        assertEquals(1, candidates.size)
        assertEquals(108 * 10_000_000L, candidates.single().timeStamp)
        assertEquals(19.6, candidates.single().peak, 1e-10)
        for (i in 119..123) feed(i, 2000)
        feed(127, 1000, 40_000_000L)
        for (i in 128..135) feed(i, 1000)
        assertEquals(1, candidates.size)
        assertNull(detector.latestCandidate)
        assertNull(processor.warmupEndedAt)
    }
}
