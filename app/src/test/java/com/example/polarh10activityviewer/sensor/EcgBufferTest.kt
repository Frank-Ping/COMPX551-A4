package com.example.polarh10activityviewer.sensor

import com.example.polarh10activityviewer.ble.DataSubscriptions
import com.example.polarh10activityviewer.ble.SubscriptionStatus

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.*
import com.polar.sdk.api.model.EcgSample
import com.polar.sdk.api.model.FecgSample
import com.polar.sdk.api.model.PolarEcgData
import com.polar.sdk.api.model.PolarEcgDataSample
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EcgBufferTest {
    private fun batch(vararg samples: PolarEcgDataSample) = PolarEcgData(samples.toList())
    private fun DataSubscriptions.startEcg(buffer: EcgBuffer, source: Flow<PolarEcgData>,
        current: () -> Boolean = { true }) = start(ECG, { true }, current,
        { source.h10EcgSamples() }, buffer::receive)

    @Test fun extractsOnlyH10SamplesAndPreservesOrderSignedVoltageAndExactTiming() = runTest {
        val buffer = EcgBuffer()
        val subscriptions = DataSubscriptions(this, buffer::onSubscriptionState)
        val source = MutableSharedFlow<PolarEcgData>()
        subscriptions.startEcg(buffer, source)
        runCurrent()
        source.emit(batch(EcgSample(1, -250), FecgSample(2, 99, 88, 0u), EcgSample(7_692_308, 0)))
        runCurrent()
        source.emit(batch(EcgSample(50_000_000, 500)))
        runCurrent()
        assertEquals(listOf(1L, 7_692_308L, 50_000_000L), buffer.samples.value.map { it.timeStamp })
        assertEquals(listOf(-250, 0, 500), buffer.samples.value.map { it.voltage })
        assertEquals(SubscriptionStatus.RECEIVING, subscriptions.states.value.getValue(ECG).status)
        subscriptions.stopAll()
    }

    @Test fun emptyAndNonH10BatchesDoNotMarkReceivingOrReplaceSamples() = runTest {
        val buffer = EcgBuffer()
        val subscriptions = DataSubscriptions(this, buffer::onSubscriptionState)
        val source = MutableSharedFlow<PolarEcgData>()
        subscriptions.startEcg(buffer, source)
        runCurrent()
        source.emit(batch())
        source.emit(batch(FecgSample(1, 99, 88, 0u)))
        runCurrent()
        assertTrue(buffer.samples.value.isEmpty())
        assertEquals(SubscriptionStatus.STARTING, subscriptions.states.value.getValue(ECG).status)
        source.emit(batch(EcgSample(2, -1)))
        runCurrent()
        val snapshot = buffer.samples.value
        source.emit(batch())
        runCurrent()
        assertEquals(snapshot, buffer.samples.value)
        subscriptions.stopAll()
    }

    @Test fun timeWindowExcludesLeftEndpointWithoutFillingOrInterpolatingGaps() {
        val buffer = EcgBuffer()
        buffer.receive(listOf(EcgSample(0, 1), EcgSample(1, 2), EcgSample(10_000_000_000, 3)))
        assertEquals(listOf(1L, 10_000_000_000L), buffer.samples.value.map { it.timeStamp })
        buffer.receive(listOf(EcgSample(20_000_000_001, 4)))
        assertEquals(1, buffer.samples.value.size)
        assertEquals(4, buffer.samples.value.single().voltage)
    }

    @Test fun countLimitKeepsLatest1300SamplesInsideTimeWindow() {
        val buffer = EcgBuffer()
        buffer.receive((0L..1_400L).map { EcgSample(it * 1_000_000, it.toInt()) })
        assertEquals(1_300, buffer.samples.value.size)
        assertEquals(101_000_000L, buffer.samples.value.first().timeStamp)
        assertEquals(1_400, buffer.samples.value.last().voltage)
    }

    @Test fun duplicateRejectionPreservesDataAndRestartWaitsForCleanupThenClears() = runTest {
        val buffer = EcgBuffer()
        val subscriptions = DataSubscriptions(this, buffer::onSubscriptionState)
        val cleanup = CompletableDeferred<Unit>()
        val source = flow {
            try { emit(batch(EcgSample(1, -100))); awaitCancellation() }
            finally { withContext(NonCancellable) { cleanup.await() } }
        }
        assertTrue(subscriptions.startEcg(buffer, source))
        runCurrent()
        val snapshot = buffer.samples.value
        assertFalse(subscriptions.startEcg(buffer, source))
        assertEquals(snapshot, buffer.samples.value)
        subscriptions.stop(ECG)
        subscriptions.stop(ECG)
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPING, subscriptions.states.value.getValue(ECG).status)
        assertFalse(subscriptions.startEcg(buffer, source))
        assertEquals(snapshot, buffer.samples.value)
        cleanup.complete(Unit)
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.states.value.getValue(ECG).status)
        assertNull(subscriptions.states.value.getValue(ECG).error)
        assertEquals(snapshot, buffer.samples.value)
        val next = MutableSharedFlow<PolarEcgData>()
        assertTrue(subscriptions.startEcg(buffer, next))
        assertTrue(buffer.samples.value.isEmpty())
        runCurrent()
        next.emit(batch(EcgSample(2, 200)))
        runCurrent()
        assertEquals(200, buffer.samples.value.single().voltage)
        subscriptions.stopAll()
    }

    @Test fun completionFailureAndManualStopRetainSnapshotWithoutStoppingHrOrAcc() = runTest {
        val buffer = EcgBuffer()
        val subscriptions = DataSubscriptions(this, buffer::onSubscriptionState)
        for (type in listOf(HR, ACC)) {
            subscriptions.start(type, { true }, { true }, { flow { emit(1); awaitCancellation() } }, {})
        }
        for (fails in listOf(false, true)) {
            assertTrue(subscriptions.startEcg(buffer, flow {
                emit(batch(EcgSample(1, 10)))
                if (fails) error("controlled ECG failure")
            }))
            runCurrent()
            assertEquals(10, buffer.samples.value.single().voltage)
            assertEquals(if (fails) SubscriptionStatus.FAILED else SubscriptionStatus.STOPPED,
                subscriptions.states.value.getValue(ECG).status)
            for (type in listOf(HR, ACC)) {
                assertEquals(SubscriptionStatus.RECEIVING, subscriptions.states.value.getValue(type).status)
            }
        }
        assertTrue(subscriptions.startEcg(buffer, flow { emit(batch(EcgSample(2, 20))); awaitCancellation() }))
        runCurrent()
        assertNull(subscriptions.states.value.getValue(ECG).error)
        subscriptions.stop(ECG)
        runCurrent()
        assertEquals(20, buffer.samples.value.single().voltage)
        for (type in listOf(HR, ACC)) {
            assertEquals(SubscriptionStatus.RECEIVING, subscriptions.states.value.getValue(type).status)
        }
        subscriptions.stopAll()
    }

    @Test fun connectionCleanupRetainsSnapshotAndDoesNotAutomaticallyResume() = runTest {
        val buffer = EcgBuffer()
        val subscriptions = DataSubscriptions(this, buffer::onSubscriptionState)
        var connected = true
        val source = MutableSharedFlow<PolarEcgData>()
        subscriptions.startEcg(buffer, source, { connected })
        runCurrent()
        source.emit(batch(EcgSample(1, -5)))
        runCurrent()
        connected = false
        subscriptions.stopAll()
        subscriptions.stopAll()
        runCurrent()
        connected = true
        source.emit(batch(EcgSample(2, 99)))
        runCurrent()
        assertEquals(-5, buffer.samples.value.single().voltage)
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.states.value.getValue(ECG).status)
    }

    @Test fun oldTaskAndConnectionEventsCannotChangeNewSnapshot() = runTest {
        val buffer = EcgBuffer()
        val subscriptions = DataSubscriptions(this, buffer::onSubscriptionState)
        lateinit var oldCollector: FlowCollector<PolarEcgData>
        // Deliberately broken producer for a late SDK event, confined to tests.
        subscriptions.startEcg(buffer, object : Flow<PolarEcgData> {
            override suspend fun collect(collector: FlowCollector<PolarEcgData>) {
                oldCollector = collector
                collector.emit(batch(EcgSample(1, 10)))
            }
        })
        runCurrent()
        var current = true
        val source = MutableSharedFlow<PolarEcgData>()
        subscriptions.startEcg(buffer, source, { current })
        runCurrent()
        source.emit(batch(EcgSample(2, 20)))
        runCurrent()
        runCatching { oldCollector.emit(batch(EcgSample(3, 30))) }
        current = false
        source.emit(batch(EcgSample(4, 40)))
        runCurrent()
        assertEquals(listOf(20), buffer.samples.value.map { it.voltage })
        subscriptions.stopAll()
    }
}
