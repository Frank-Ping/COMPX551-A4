package com.example.polarh10activityviewer.motion

import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.sensor.AccSample

import kotlin.math.pow
import org.junit.Assert.*
import org.junit.Test

class MotionStatisticsTest {
    private fun sample(index: Int, walking: Boolean = true) = AccSample(index * 10_000_000L,
        if (!walking || index < 104) 1000 else when ((index - 104) % 50) {
            in 0..9 -> 1800
            in 10..19 -> 800
            else -> 1000
        }, 0, 0)

    private fun detector() = StepDetector { 0L }.apply {
        reset()
        onSubscriptionState(SubscriptionStatus.STARTING)
    }

    @Test fun speedUsesOriginalTimesLeftOpenRightClosedAndShortDuration() {
        val window = MotionWindow()
        val steps = listOf(
            StepCandidate(1_000_000_000, 12.0, 99.0),
            StepCandidate(2_000_000_000, 12.0, 1.0),
            StepCandidate(5_000_000_000, 12.0, 2.0),
            StepCandidate(6_000_000_000, 12.0, 3.0)
        )
        window.receive(steps, 6_000_000_000)
        assertEquals(1.2, window.rawSpeed(6_000_000_000), 1e-10)
        assertEquals(36.0, window.rawCadence(6_000_000_000), 0.0)
        assertEquals(0.6, window.rawSpeed(5_900_000_000), 1e-10)
        window.clear()
        window.receive(listOf(StepCandidate(1_500_000_000, 12.0, 1.0)), 2_000_000_000)
        assertEquals(1.0, window.speed(2_000_000_000, 1_000_000_000), 1e-10)
        assertEquals(0.0, window.speed(1_000_000_000, 1_000_000_000), 0.0)
        assertEquals(0.0, window.speed(1_400_000_000, 1_000_000_000), 0.0)
    }

    @Test fun twoSecondDisplayZeroDoesNotChangeRawWindowOrEvictStoredSteps() {
        val window = MotionWindow()
        window.receive(listOf(StepCandidate(1_000_000_000, 12.0, 2.0)), 1_100_000_000)
        assertTrue(window.speed(2_999_999_999, 0) > 0)
        assertEquals(0.0, window.speed(3_000_000_000, 0), 0.0)
        assertEquals(0.0, window.value(3_000_000_000, 0), 0.0)
        assertEquals(0.4, window.rawSpeed(3_000_000_000), 0.0)
        assertEquals(12.0, window.rawCadence(3_000_000_000), 0.0)
        window.speed(30_000_000_000, 0)
        assertEquals(0.4, window.rawSpeed(3_000_000_000), 0.0)
        window.receive(emptyList(), 6_000_000_000)
        assertEquals(0.0, window.rawSpeed(6_000_000_000), 0.0)
    }

    @Test fun rawPipelineCommitsOnlyThreeLengthsAtFourthStepAndNoDistanceOnRefresh() {
        val detector = detector()
        (0..229).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(2_290_000_000, 0)
        assertEquals(0.0, detector.state.value.distance!!, 0.0)
        assertEquals(0L, detector.totalSteps)
        val commits = (230..303).flatMap { detector.receive(sample(it)) }
        detector.receivedBatch(3_030_000_000, 0)
        assertEquals(4, commits.size)
        assertNull(commits.first().length)
        val length = 0.5 * (1000 * 0.0098).pow(0.25)
        commits.drop(1).forEach { assertEquals(length, it.length!!, 1e-10) }
        val distance = length * 3
        assertEquals(distance, detector.state.value.distance!!, 1e-10)
        assertEquals(distance / 2, detector.state.value.speed!!, 1e-10)
        repeat(20) { detector.refresh() }
        assertEquals(distance, detector.state.value.distance!!, 1e-10)
        (304..353).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(3_530_000_000, 0)
        assertEquals(5L, detector.totalSteps)
        assertEquals(length * 4, detector.state.value.distance!!, 1e-10)
    }

    @Test fun maximaNeedFiveSecondsOfActualCoverageAfterWarmup() {
        var phone = 0L
        val detector = StepDetector { phone }
        detector.onSubscriptionState(SubscriptionStatus.STARTING)
        // Warm-up ends at 1.03 s; 6.02 s supplies only 4.99 s of coverage.
        (0..602).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(6_020_000_000, phone)
        assertNull(detector.state.value.maximumCadence)
        assertNull(detector.state.value.minimumCadence)
        assertNull(detector.state.value.maximumSpeed)
        phone = 50_000
        detector.refresh()
        assertNull(detector.state.value.maximumCadence)
        assertNull(detector.state.value.minimumCadence)
        assertNull(detector.state.value.maximumSpeed)
        detector.receive(sample(603))
        detector.receivedBatch(6_030_000_000, phone)
        assertEquals(120.0, detector.state.value.maximumCadence!!, 0.0)
        assertEquals(120.0, detector.state.value.minimumCadence!!, 0.0)
        assertEquals(9 * 0.5 * 9.8.pow(0.25) / 5, detector.state.value.maximumSpeed!!, 1e-10)
        assertFalse(detector.state.value.incompleteAcc)
    }

    @Test fun stationaryCompleteWindowProducesGenuineZeroMaxima() {
        val detector = detector()
        (0..603).forEach { detector.receive(sample(it, false)) }
        detector.receivedBatch(6_030_000_000, 0)
        assertEquals(0.0, detector.state.value.maximumCadence!!, 0.0)
        assertEquals(0.0, detector.state.value.maximumSpeed!!, 0.0)
        assertEquals(0.0, detector.state.value.distance!!, 0.0)
        assertFalse(detector.state.value.incompleteAcc)
    }

    @Test fun gapAndRetryKeepDistanceAndMaximaWithoutConnectingSegments() {
        val detector = detector()
        (0..603).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(6_030_000_000, 0)
        val before = detector.state.value
        detector.receive(sample(608, false).copy(gapBeforeNs = 50_000_000))
        detector.receivedBatch(6_080_000_000, 0)
        assertNull(detector.state.value.speed)
        assertTrue(detector.state.value.incompleteAcc)
        assertEquals(before.distance, detector.state.value.distance)
        assertEquals(before.maximumSpeed, detector.state.value.maximumSpeed)
        detector.onSubscriptionState(SubscriptionStatus.FAILED, "ACC failed")
        detector.onSubscriptionState(SubscriptionStatus.STARTING)
        assertNull(detector.state.value.speed)
        (0..303).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(3_030_000_000, 0)
        assertEquals(before.distance!! + 3 * 0.5 * 9.8.pow(0.25), detector.state.value.distance!!, 1e-10)
        assertEquals(before.maximumSpeed, detector.state.value.maximumSpeed)
        assertTrue(detector.state.value.incompleteAcc)
        detector.reset()
        assertNull(detector.state.value.distance)
        assertNull(detector.state.value.maximumSpeed)
        assertFalse(detector.state.value.incompleteAcc)
    }

    @Test fun unconfirmedLengthsAreDiscardedOnGapAndTimeout() {
        for (gap in listOf(false, true)) {
            val detector = detector()
            (0..229).forEach { detector.receive(sample(it)) }
            if (gap) detector.receive(sample(234, false).copy(gapBeforeNs = 50_000_000))
            else (230..450).forEach { detector.receive(sample(it, false)) }
            val offset = if (gap) 2_350_000_000L else 4_510_000_000L
            (0..303).forEach { detector.receive(sample(it).copy(timeStamp = it * 10_000_000L + offset)) }
            detector.receivedBatch(3_030_000_000 + offset, 0)
            assertEquals(4L, detector.totalSteps)
            assertEquals(3 * 0.5 * 9.8.pow(0.25), detector.state.value.distance!!, 1e-10)
        }
    }

    @Test fun averageUsesFullRunningTimeAndDistinguishesUnknownFromStationary() {
        val moving = StepState(distance = 100.0, durationMs = 80_000)
        assertEquals(4.5, moving.averageSpeed!! * 3.6, 1e-10)
        assertEquals(3.6, moving.copy(durationMs = 100_000).averageSpeed!! * 3.6, 1e-10)
        assertNull(moving.copy(durationMs = 0).averageSpeed)
        assertNull(moving.copy(distance = null).averageSpeed)
        assertEquals(0.0, moving.copy(distance = 0.0).averageSpeed!!, 0.0)
    }

    @Test fun meanCadenceUsesAllRunningTimeAndKeepsUnknownSeparateFromZero() {
        val moving = StepState(totalSteps = 7, receivedAcc = true, durationMs = 12_345,
            minimumCadence = 60.0, maximumCadence = 120.0)
        assertEquals(420_000.0 / 12_345, moving.meanCadence!!, 0.0)
        assertTrue(moving.meanCadence!! < moving.minimumCadence!!)
        assertEquals(21.0, moving.copy(durationMs = 20_000).meanCadence!!, 0.0)
        assertEquals(21.0, moving.copy(durationMs = 20_000, incompleteAcc = true).meanCadence!!, 0.0)
        assertNull(moving.copy(durationMs = 0).meanCadence)
        assertNull(moving.copy(receivedAcc = false).meanCadence)
        assertNull(moving.copy(totalSteps = null).meanCadence)
        assertEquals(0.0, moving.copy(totalSteps = 0).meanCadence!!, 0.0)
    }

    @Test fun displayZeroAndStopDoNotLowerTheRawMinimum() {
        var phone = 0L
        val detector = StepDetector { phone }
        detector.onSubscriptionState(SubscriptionStatus.STARTING)
        (0..603).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(6_030_000_000, phone)
        detector.updateSessionTime(6030)
        val before = detector.state.value
        assertEquals(120.0, before.minimumCadence!!, 0.0)
        phone = 3000
        detector.refresh()
        assertEquals(0.0, detector.state.value.cadence!!, 0.0)
        assertEquals(before.minimumCadence, detector.state.value.minimumCadence)
        detector.stop()
        assertEquals(before.meanCadence, detector.state.value.meanCadence)
        assertEquals(before.minimumCadence, detector.state.value.minimumCadence)
        assertEquals(before.maximumCadence, detector.state.value.maximumCadence)
        detector.reset()
        assertNull(detector.state.value.meanCadence)
        assertNull(detector.state.value.minimumCadence)
    }

    @Test fun genuineStationaryWindowsCanSetMinimumToZero() {
        val detector = detector()
        (0..602).forEach { detector.receive(sample(it, false)) }
        detector.receivedBatch(6_020_000_000, 0)
        assertNull(detector.state.value.minimumCadence)
        detector.receive(sample(603, false))
        detector.receivedBatch(6_030_000_000, 0)
        assertEquals(0.0, detector.state.value.minimumCadence!!, 0.0)
        assertEquals(0.0, detector.state.value.maximumCadence!!, 0.0)
    }

    @Test fun gapsAndRetryRetainExtremaUntilNewSegmentHasFiveSeconds() {
        for (retry in listOf(false, true)) {
            val detector = detector()
            (0..603).forEach { detector.receive(sample(it)) }
            detector.receivedBatch(6_030_000_000, 0)
            val steps = detector.totalSteps
            if (retry) {
                detector.onSubscriptionState(SubscriptionStatus.FAILED)
                detector.onSubscriptionState(SubscriptionStatus.STARTING)
            }
            // A new continuous stationary segment begins at 10 seconds.
            (0..602).forEach {
                detector.receive(sample(it, false).copy(timeStamp = 10_000_000_000L + it * 10_000_000L,
                    gapBeforeNs = if (!retry && it == 0) 3_970_000_000L else null))
            }
            detector.receivedBatch(16_020_000_000L, 0)
            assertTrue(detector.state.value.incompleteAcc)
            assertEquals(120.0, detector.state.value.minimumCadence!!, 0.0)
            assertEquals(steps, detector.totalSteps)
            detector.receive(sample(603, false).copy(timeStamp = 16_030_000_000L))
            detector.receivedBatch(16_030_000_000L, 0)
            assertEquals(0.0, detector.state.value.minimumCadence!!, 0.0)
            assertEquals(120.0, detector.state.value.maximumCadence!!, 0.0)
        }
    }

    @Test fun stopFreezesObservedStatisticsButNeverCreatesUnobservedZeros() {
        val detector = detector()
        detector.onSubscriptionState(SubscriptionStatus.IDLE, "ACC unavailable")
        detector.updateSessionTime(5000)
        detector.stop()
        assertNull(detector.state.value.distance)
        assertNull(detector.state.value.speed)
        assertNull(detector.state.value.averageSpeed)
        assertNull(detector.state.value.maximumCadence)
        detector.reset()
        detector.onSubscriptionState(SubscriptionStatus.STARTING)
        (0..603).forEach { detector.receive(sample(it)) }
        detector.receivedBatch(6_030_000_000, 0)
        detector.updateSessionTime(7000)
        val before = detector.state.value
        detector.stop()
        repeat(3) { detector.refresh(); detector.stop() }
        assertEquals(0.0, detector.state.value.speed!!, 0.0)
        assertEquals(before.distance, detector.state.value.distance)
        assertEquals(before.averageSpeed, detector.state.value.averageSpeed)
        assertEquals(before.maximumCadence, detector.state.value.maximumCadence)
        assertEquals(before.maximumSpeed, detector.state.value.maximumSpeed)
        assertTrue(detector.state.value.receivedAcc)
    }
}
