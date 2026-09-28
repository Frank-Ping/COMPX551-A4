package com.example.polarh10activityviewer

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.HR
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.ACC
import com.polar.sdk.api.model.PolarHrData
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HeartRateTest {

    private fun batch(vararg values: Int) = PolarHrData(values.map {
        PolarHrData.PolarHrSample(it, 0, 0, emptyList(), emptyList(), false, true, true)
    })

    private fun DataSubscriptions.startHr(
        latest: LatestHeartRate,
        source: Flow<PolarHrData>,
        current: () -> Boolean = { true },
        now: () -> Long = { 1_000L }
    ) = start(HR, { true }, current,
        { source.filter { it.samples.isNotEmpty() } },
        { latest.receive(it, now()) })

    @Test fun batchKeepsLastRawSampleAndRepeatedValueUpdatesTime() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        val source = MutableSharedFlow<PolarHrData>()
        var time = 1_000L
        var clockReads = 0
        assertNull(latest.reading.value)
        subscriptions.startHr(latest, source, now = { clockReads++; time })
        runCurrent()
        source.emit(batch(70, 71, 72))
        runCurrent()
        assertEquals(HeartRateReading(72, 1_000), latest.reading.value)
        assertEquals(1, clockReads)
        time = 2_000
        source.emit(batch(72))
        runCurrent()
        assertEquals(HeartRateReading(72, 2_000), latest.reading.value)
        assertEquals(2, clockReads)
        // HR validation and statistics are deferred to step 5.1.
        source.emit(batch(0))
        runCurrent()
        assertEquals(0, latest.reading.value?.bpm)
        subscriptions.stopAll()
    }

    @Test fun emptyBatchDoesNotStartReceptionOrReplaceLastReading() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        val source = MutableSharedFlow<PolarHrData>()
        subscriptions.startHr(latest, source)
        runCurrent()
        source.emit(batch())
        runCurrent()
        assertEquals(SubscriptionStatus.STARTING, subscriptions.states.value.getValue(HR).status)
        assertNull(latest.reading.value)
        source.emit(batch(80))
        runCurrent()
        source.emit(batch())
        runCurrent()
        assertEquals(HeartRateReading(80, 1_000), latest.reading.value)
        assertEquals(SubscriptionStatus.RECEIVING, subscriptions.states.value.getValue(HR).status)
        subscriptions.stopAll()
    }

    @Test fun duplicateStartKeepsReadingAndStopClearsBeforeCleanupCompletes() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        val cleanup = CompletableDeferred<Unit>()
        val source = flow {
            try { emit(batch(75)); awaitCancellation() }
            finally { withContext(NonCancellable) { cleanup.await() } }
        }
        assertTrue(subscriptions.startHr(latest, source))
        runCurrent()
        assertFalse(subscriptions.startHr(latest, source))
        assertEquals(HeartRateReading(75, 1_000), latest.reading.value)
        subscriptions.stop(HR)
        assertNull(latest.reading.value)
        runCurrent()
        assertFalse(subscriptions.startHr(latest, source))
        assertEquals(SubscriptionStatus.STOPPING, subscriptions.states.value.getValue(HR).status)
        cleanup.complete(Unit)
        runCurrent()
        assertNull(subscriptions.states.value.getValue(HR).error)
        val next = MutableSharedFlow<PolarHrData>()
        assertTrue(subscriptions.startHr(latest, next, now = { 2_000 }))
        runCurrent()
        assertNull(latest.reading.value)
        next.emit(batch(85))
        runCurrent()
        assertEquals(HeartRateReading(85, 2_000), latest.reading.value)
        subscriptions.stopAll()
    }

    @Test fun completionAndFailureClearReadingAndAllowManualRetry() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        for (fails in listOf(false, true)) {
            val finish = CompletableDeferred<Unit>()
            assertTrue(subscriptions.startHr(latest, flow {
                emit(batch(76))
                finish.await()
                if (fails) error("controlled HR failure")
            }))
            runCurrent()
            assertNotNull(latest.reading.value)
            finish.complete(Unit)
            runCurrent()
            assertNull(latest.reading.value)
            assertEquals(if (fails) SubscriptionStatus.FAILED else SubscriptionStatus.STOPPED,
                subscriptions.states.value.getValue(HR).status)
        }
        assertEquals("HR stream failed: controlled HR failure", subscriptions.states.value.getValue(HR).error)
        assertTrue(subscriptions.startHr(latest, flow { emit(batch(81)); awaitCancellation() }))
        runCurrent()
        assertEquals(81, latest.reading.value?.bpm)
        assertNull(subscriptions.states.value.getValue(HR).error)
        subscriptions.stopAll()
    }

    @Test fun cleanupAllClearsImmediatelyAndDoesNotResumeAfterReconnection() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        var current = true
        val source = MutableSharedFlow<PolarHrData>()
        subscriptions.startHr(latest, source, current = { current })
        runCurrent()
        source.emit(batch(90))
        runCurrent()
        current = false
        subscriptions.stopAll()
        subscriptions.stopAll()
        assertNull(latest.reading.value)
        runCurrent()
        current = true
        source.emit(batch(91))
        runCurrent()
        assertNull(latest.reading.value)
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.states.value.getValue(HR).status)
    }

    @Test fun oldConnectionEventsCannotUpdateTheReading() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        var current = true
        val send = CompletableDeferred<Unit>()
        subscriptions.startHr(latest, flow {
            send.await()
            emit(batch(99))
            error("old connection")
        }, current = { current })
        runCurrent()
        current = false
        send.complete(Unit)
        runCurrent()
        assertNull(latest.reading.value)
        assertNull(subscriptions.states.value.getValue(HR).error)
    }

    @Test fun lateOldEmissionCannotOverwriteRestartedReading() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        lateinit var oldCollector: FlowCollector<PolarHrData>
        // Intentionally broken producer for a late SDK event; never used in production.
        val oldSource = object : Flow<PolarHrData> {
            override suspend fun collect(collector: FlowCollector<PolarHrData>) {
                oldCollector = collector
                collector.emit(batch(60))
            }
        }
        subscriptions.startHr(latest, oldSource)
        runCurrent()
        assertNull(latest.reading.value)
        subscriptions.startHr(latest, flow { emit(batch(82)); awaitCancellation() }, now = { 2_000 })
        runCurrent()
        runCatching { oldCollector.emit(batch(61)) }
        runCurrent()
        assertEquals(HeartRateReading(82, 2_000), latest.reading.value)
        subscriptions.stopAll()
    }

    @Test fun otherStreamStateChangesDoNotClearHeartRate() = runTest {
        val latest = LatestHeartRate()
        val subscriptions = DataSubscriptions(this, latest::onSubscriptionState)
        subscriptions.startHr(latest, flow { emit(batch(88)); awaitCancellation() })
        runCurrent()
        subscriptions.start(ACC, { true }, { true }, { flow<Int> { error("ACC failure") } }, {})
        runCurrent()
        assertEquals(HeartRateReading(88, 1_000), latest.reading.value)
        subscriptions.stopAll()
    }
}
