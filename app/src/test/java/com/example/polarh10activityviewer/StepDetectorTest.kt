package com.example.polarh10activityviewer

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.ACC
import com.polar.sdk.api.model.PolarAccelerometerData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StepDetectorTest {
    private fun raw(index: Int, x: Int) = AccSample(index * 10_000_000L, x, 0, 0)
    private fun walking() = (0..303).map { index ->
        raw(index, if (index < 104) 1000 else when ((index - 104) % 50) {
            in 0..9 -> 1800
            in 10..19 -> 800
            else -> 1000
        })
    }

    @Test fun realProcessingPathCommitsFourOriginalPeakTimesAcrossBatches() {
        val detector = StepDetector()
        val commits = mutableListOf<List<StepCandidate>>()
        val buffer = AccBuffer { sample ->
            val result = detector.receive(sample)
            if (result.isNotEmpty()) commits += result
        }
        walking().chunked(37).forEach { chunk ->
            buffer.receive(PolarAccelerometerData(chunk.map {
                PolarAccelerometerData.PolarAccelerometerDataSample(it.timeStamp, it.x, it.y, it.z)
            }))
        }
        assertEquals(1, commits.size)
        assertEquals(listOf(108, 158, 208, 258).map { it * 10_000_000L }, commits.single().map { it.timeStamp })
        assertEquals(4L, detector.totalSteps)
    }

    @Test fun sensorTimeoutRewarmsOnceAndPreservesTotalWithoutAnyUiTimer() {
        val detector = StepDetector()
        walking().forEach { detector.receive(it) }
        val lastPeak = detector.latestCommitted.last().timeStamp
        val exactIndex = ((lastPeak + 2_000_000_000L) / 10_000_000L).toInt()
        for (i in 304..exactIndex) detector.receive(raw(i, 1000))
        assertNotNull(detector.preprocessor.warmupEndedAt)
        detector.receive(raw(exactIndex + 1, 1000))
        assertNull(detector.preprocessor.warmupEndedAt)
        assertNull(detector.preprocessor.latest!!.smoothed)
        assertTrue(detector.latestCommitted.isEmpty())
        assertEquals(4L, detector.totalSteps)
        for (i in exactIndex + 2..exactIndex + 104) detector.receive(raw(i, 1000))
        assertEquals((exactIndex + 104) * 10_000_000L, detector.preprocessor.warmupEndedAt)
        detector.receive(raw(exactIndex + 105, 1000))
        assertNotNull(detector.preprocessor.latest!!.previousMean)
        assertEquals(4L, detector.totalSteps)
    }

    @Test fun gapDropsPendingSequenceAndRewarmsBeforeConfirmingAgain() {
        val detector = StepDetector()
        walking().take(230).forEach { detector.receive(it) }
        assertEquals(0L, detector.totalSteps)
        detector.receive(raw(234, 1000).copy(gapBeforeNs = 50_000_000L))
        assertNull(detector.preprocessor.warmupEndedAt)
        walking().forEach { detector.receive(it.copy(timeStamp = it.timeStamp + 2_350_000_000L)) }
        assertEquals(4L, detector.totalSteps)
        detector.clearSegment()
        assertEquals(4L, detector.totalSteps)
        assertNull(detector.preprocessor.latest)
        detector.reset()
        assertEquals(0L, detector.totalSteps)
    }

    @Test fun subscriptionStopRetryAndStaleSourcePreserveOnlyCommittedTotals() = runTest {
        val detector = StepDetector()
        val buffer = AccBuffer { detector.receive(it) }
        val subscriptions = DataSubscriptions(this) { type, status ->
            buffer.onSubscriptionState(type, status)
            if (type == ACC && status != SubscriptionStatus.RECEIVING) detector.clearSegment()
        }
        val source = MutableSharedFlow<PolarAccelerometerData>()
        fun start(input: MutableSharedFlow<PolarAccelerometerData>) =
            subscriptions.start(ACC, { true }, { true }, { input }, buffer::receive)
        val batch = PolarAccelerometerData(walking().map {
            PolarAccelerometerData.PolarAccelerometerDataSample(it.timeStamp, it.x, it.y, it.z)
        })
        assertTrue(start(source))
        runCurrent()
        source.emit(batch)
        runCurrent()
        assertEquals(4L, detector.totalSteps)
        assertFalse(start(source))
        assertNotNull(detector.preprocessor.warmupEndedAt)
        subscriptions.stop(ACC)
        assertNull(detector.preprocessor.latest)
        runCurrent()
        val retry = MutableSharedFlow<PolarAccelerometerData>()
        assertTrue(start(retry))
        runCurrent()
        source.emit(batch)
        assertNull(detector.preprocessor.latest)
        assertEquals(4L, detector.totalSteps)
        retry.emit(batch)
        runCurrent()
        assertEquals(8L, detector.totalSteps)
        subscriptions.stop(ACC)
        runCurrent()
    }
}
