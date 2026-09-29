package com.example.polarh10activityviewer.motion

import org.junit.Assert.*
import org.junit.Test

class StepSequenceTest {
    private fun peak(time: Long) = StepCandidate(time, 12.0)

    @Test fun firstThreeArePendingFourthCommitsOriginalPeaksThenSingles() {
        val sequence = StepSequence()
        val peaks = (0L..3L).map { peak(it * 500_000_000L) }
        peaks.take(3).forEach {
            assertTrue(sequence.accept(it).isEmpty())
            assertEquals(0L, sequence.totalSteps)
        }
        assertEquals(peaks, sequence.accept(peaks.last()))
        assertEquals(4L, sequence.totalSteps)
        val fifth = peak(2_000_000_000L)
        assertEquals(listOf(fifth), sequence.accept(fifth))
        assertEquals(5L, sequence.totalSteps)
        assertTrue(sequence.accept(fifth).isEmpty())
        assertEquals(5L, sequence.totalSteps)
    }

    @Test fun rejectedClosePeaksDoNotMoveReferenceOrBreakPendingSequence() {
        val sequence = StepSequence()
        sequence.accept(peak(0))
        assertTrue(sequence.accept(peak(200_000_000)).isEmpty())
        assertTrue(sequence.accept(peak(249_999_999)).isEmpty())
        sequence.accept(peak(250_000_000))
        assertTrue(sequence.accept(peak(250_000_000)).isEmpty())
        sequence.accept(peak(500_000_000))
        val result = sequence.accept(peak(750_000_000))
        assertEquals(listOf(0L, 250_000_000L, 500_000_000L, 750_000_000L), result.map { it.timeStamp })
        assertEquals(4L, sequence.totalSteps)
        assertTrue(sequence.accept(peak(950_000_000)).isEmpty())
        assertTrue(sequence.accept(peak(999_999_999)).isEmpty())
        assertEquals(listOf(peak(1_000_000_000)), sequence.accept(peak(1_000_000_000)))
    }

    @Test fun exactTwoSecondIntervalsAcceptedButGreaterIntervalDiscardsSequence() {
        val sequence = StepSequence()
        for (i in 0L..2L) sequence.accept(peak(i * 2_000_000_000L))
        assertEquals(4, sequence.accept(peak(6_000_000_000L)).size)
        assertFalse(sequence.expire(8_000_000_000L))
        assertTrue(sequence.expire(8_000_000_001L))
        assertFalse(sequence.expire(20_000_000_000L))
        assertEquals(4L, sequence.totalSteps)
        sequence.accept(peak(21_000_000_000L))
        assertTrue(sequence.accept(peak(23_000_000_001L)).isEmpty())
        assertFalse(sequence.expire(30_000_000_000L))
        assertEquals(4L, sequence.totalSteps)
    }

    @Test fun pendingTimeoutDoesNotCountAndConfirmationMustStartOver() {
        val sequence = StepSequence()
        for (i in 0L..2L) sequence.accept(peak(i * 500_000_000L))
        assertTrue(sequence.expire(3_000_000_001L))
        for (i in 0L..2L) assertTrue(sequence.accept(peak(4_000_000_000L + i * 500_000_000L)).isEmpty())
        assertEquals(0L, sequence.totalSteps)
        assertEquals(4, sequence.accept(peak(5_500_000_000L)).size)
    }

    @Test fun segmentClearRetainsTotalWhileNewSessionResetClearsIt() {
        val sequence = StepSequence()
        repeat(4) { sequence.accept(peak(it * 500_000_000L)) }
        sequence.clearSegment()
        assertEquals(4L, sequence.totalSteps)
        assertTrue(sequence.accept(peak(2_000_000_000L)).isEmpty())
        sequence.clearSegment()
        assertEquals(4L, sequence.totalSteps)
        sequence.reset()
        assertEquals(0L, sequence.totalSteps)
        assertFalse(sequence.expire(100_000_000_000L))
    }

    @Test fun longSequenceReturnsOnlyNewlyCommittedSteps() {
        val sequence = StepSequence()
        var submitted = 0
        repeat(10_000) {
            val result = sequence.accept(peak(it * 250_000_000L))
            assertTrue(result.size <= 4)
            submitted += result.size
        }
        assertEquals(10_000, submitted)
        assertEquals(10_000L, sequence.totalSteps)
    }
}
