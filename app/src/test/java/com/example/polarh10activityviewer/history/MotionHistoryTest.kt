package com.example.polarh10activityviewer.history

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.motion.StepState
import org.junit.Assert.*
import org.junit.Test

class MotionHistoryTest {
    private val moving = StepState(cadence = 123.456, receivedAcc = true)
    private fun history() = MotionHistory().apply {
        reset("session")
        onSubscriptionState(SubscriptionStatus.RECEIVING)
    }

    @Test fun keepsLastCadenceAndActualTimeWithoutRoundingOrFillingSkippedRefreshes() {
        val h = history()
        h.record(250, moving, false, 1)
        h.record(750, moving.copy(cadence = 101.23), false, 1)
        h.record(3678, moving, false, 1)
        assertEquals(listOf(0L, 3L), h.snapshot().map { it.secondBucket })
        assertEquals(listOf(750L, 3678L), h.snapshot().map { it.elapsedMs })
        assertEquals(101.23, h.snapshot().first().cadence!!, 0.0)
        assertEquals(750L, h.state.value.firstElapsedMs)
        assertEquals(3678L, h.state.value.lastElapsedMs)
        assertFalse(h.snapshot().last().breakBefore)
    }

    @Test fun warmupUnavailableAndUnobservedAreNullButStationaryZeroSurvives() {
        val h = history()
        val stationary = moving.copy(cadence = 0.0)
        h.record(0, stationary, true, 1)
        h.record(1000, stationary.copy(receivedAcc = false), false, 1)
        h.onSubscriptionState(SubscriptionStatus.FAILED)
        h.record(2000, stationary, false, 1)
        h.record(3000, stationary, false, 1)
        h.onSubscriptionState(SubscriptionStatus.RECEIVING)
        h.record(4000, stationary, false, 1)
        h.record(5000, moving.copy(cadence = null), false, 1)
        val points = h.snapshot()
        points.take(4).forEach {
            assertNull(it.cadence); assertTrue(it.breakBefore)
        }
        assertEquals(0.0, points[4].cadence!!, 0.0)
        assertTrue(points[4].breakBefore)
        assertNull(points[5].cadence)
        assertTrue(points[5].breakBefore)
    }

    @Test fun segmentResetAndNullBreaksSurviveLaterReplacementWithinSameBucket() {
        val h = history()
        h.record(0, moving, false, 1)
        h.record(1000, moving, false, 1)
        assertFalse(h.snapshot().last().breakBefore)
        h.record(1250, moving, false, 2)
        h.record(1750, moving, false, 2)
        assertTrue(h.snapshot().last().breakBefore)
        h.record(2000, moving, false, 2)
        assertFalse(h.snapshot().last().breakBefore)
        h.record(2250, moving, true, 2)
        h.record(2750, moving, false, 2)
        assertTrue(h.snapshot().last().breakBefore)
        assertEquals(2750L, h.snapshot().last().elapsedMs)
    }

    @Test fun retryBetweenTicksRetainsOldPointsAndBreakEvenWithoutAnUnavailableRecord() {
        val h = history()
        h.record(0, moving, false, 1)
        h.record(1000, moving, false, 1)
        val before = h.snapshot()
        h.onSubscriptionState(SubscriptionStatus.FAILED)
        h.onSubscriptionState(SubscriptionStatus.STARTING)
        h.onSubscriptionState(SubscriptionStatus.RECEIVING)
        assertEquals(before, h.snapshot())
        h.record(1750, moving, false, 1)
        h.record(1900, moving, false, 1)
        assertTrue(h.snapshot().last().breakBefore)
        assertEquals(before.first(), h.snapshot().first())
    }

    @Test fun firstFourHoursKeepAllBucketsAndStopAtSharedInclusiveDeadline() {
        val h = history()
        h.record(-1, moving, false, 1)
        assertTrue(h.snapshot().isEmpty())
        for (second in 0L..14_400L) h.record(second * 1000, moving, false, 1)
        val full = h.snapshot()
        assertEquals(HrHistory.MAX_POINTS, full.size)
        assertEquals(0L, full.first().elapsedMs)
        assertEquals(HrHistory.MAX_ELAPSED_MS, full.last().elapsedMs)
        assertTrue(h.state.value.limitReached)
        assertFalse(h.state.value.frozen)
        h.record(HrHistory.MAX_ELAPSED_MS + 1, moving.copy(cadence = 999.0), false, 1)
        h.record(HrHistory.MAX_ELAPSED_MS + 1000, moving, false, 1)
        assertEquals(full, h.snapshot())
        val sparse = history()
        sparse.record(100, moving, false, 1)
        sparse.record(HrHistory.MAX_ELAPSED_MS + 1, moving, false, 1)
        assertEquals(1, sparse.state.value.pointCount)
        assertTrue(sparse.state.value.limitReached)
    }

    @Test fun stopKeepsPartialSecondAndNewStartCannotMutatePreviousCopy() {
        val h = history()
        h.record(1789, moving, false, 1)
        h.stop()
        val frozen = h.snapshot()
        h.onSubscriptionState(SubscriptionStatus.STOPPED)
        h.record(1900, moving.copy(cadence = 0.0), false, 2)
        h.stop()
        assertEquals(frozen, h.snapshot())
        assertTrue(h.state.value.frozen)
        h.reset("next")
        assertEquals(0, h.state.value.pointCount)
        assertNull(h.state.value.lastElapsedMs)
        assertFalse(h.state.value.frozen)
        assertEquals("session", frozen.single().sessionId)
        assertEquals(1789L, frozen.single().elapsedMs)
        assertEquals(moving.cadence, frozen.single().cadence)
    }
}
