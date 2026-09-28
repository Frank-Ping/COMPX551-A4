package com.example.polarh10activityviewer

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataSubscriptionsTest {
    private val ready = DataReadiness(DataReadinessStatus.READY, configurationComplete = true)

    private fun DataSubscriptions.startTest(
        type: PolarDeviceDataType = HR,
        readiness: () -> DataReadiness = { ready },
        current: () -> Boolean = { true },
        stream: suspend () -> Flow<Int> = { flow { awaitCancellation() } },
        onData: (Int) -> Unit = {}
    ) = start(type, { readiness().let { it.status == DataReadinessStatus.READY && it.configurationComplete } }, current, stream, onData)

    private fun DataSubscriptions.status(type: PolarDeviceDataType = HR) = states.value.getValue(type).status

    @Test fun prerequisitesRejectWithoutCreatingAFlow() = runTest {
        val subscriptions = DataSubscriptions(this)
        var factories = 0
        val factory = { factories++; emptyFlow<Int>() }
        for (type in checkedDataTypes) {
            assertEquals(SubscriptionStatus.IDLE, subscriptions.status(type))
            assertFalse(subscriptions.startTest(type, current = { false }, stream = factory))
            for (status in DataReadinessStatus.entries.filter { it != DataReadinessStatus.READY }) {
                assertFalse(subscriptions.startTest(type, readiness = { ready.copy(status = status) }, stream = factory))
            }
            assertFalse(subscriptions.startTest(type,
                readiness = { ready.copy(configurationComplete = false) }, stream = factory))
        }
        assertFalse(subscriptions.startTest(PPI, stream = factory))
        runCurrent()
        assertEquals(0, factories)
    }

    @Test fun checksPrerequisitesAgainBeforeTheQueuedStart() = runTest {
        val subscriptions = DataSubscriptions(this)
        var configuration = ready
        var factories = 0
        assertTrue(subscriptions.startTest(readiness = { configuration }, stream = {
            factories++
            emptyFlow()
        }))
        configuration = ready.copy(configurationComplete = false)
        runCurrent()
        assertEquals(0, factories)
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
    }

    @Test fun duplicateStartsAreRejectedAndReceivingRequiresData() = runTest {
        val subscriptions = DataSubscriptions(this)
        val source = MutableSharedFlow<Int>()
        val received = mutableListOf<Int>()
        var factories = 0
        assertTrue(subscriptions.startTest(stream = { factories++; source }, onData = { received.add(it) }))
        assertEquals(SubscriptionStatus.STARTING, subscriptions.status())
        assertFalse(subscriptions.startTest())
        runCurrent()
        assertEquals(SubscriptionStatus.STARTING, subscriptions.status())
        source.emit(1)
        runCurrent()
        assertEquals(listOf(1), received)
        assertEquals(SubscriptionStatus.RECEIVING, subscriptions.status())
        assertFalse(subscriptions.startTest())
        assertEquals(1, factories)
        subscriptions.stopAll()
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
        assertNull(subscriptions.states.value.getValue(HR).error)
    }

    @Test fun stoppingWaitsForCleanupBeforeManualRestart() = runTest {
        val subscriptions = DataSubscriptions(this)
        val cleanup = CompletableDeferred<Unit>()
        assertTrue(subscriptions.startTest(stream = {
            flow {
                try { awaitCancellation() }
                finally { withContext(NonCancellable) { cleanup.await() } }
            }
        }))
        runCurrent()
        subscriptions.stop(HR)
        subscriptions.stop(HR)
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPING, subscriptions.status())
        assertFalse(subscriptions.startTest())
        cleanup.complete(Unit)
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
        assertTrue(subscriptions.startTest())
        runCurrent()
        assertEquals(SubscriptionStatus.STARTING, subscriptions.status())
        subscriptions.stopAll()
    }

    @Test fun cancellingBeforeExecutionStillReleasesOwnership() = runTest {
        val subscriptions = DataSubscriptions(this)
        var factories = 0
        subscriptions.startTest(stream = { factories++; emptyFlow() })
        subscriptions.stopAll()
        subscriptions.stopAll()
        assertFalse(subscriptions.startTest())
        runCurrent()
        assertEquals(0, factories)
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
        assertTrue(subscriptions.startTest(stream = { emptyFlow() }))
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
    }

    @Test fun oneFailureLeavesOtherStreamsRunningAndRetryRechecksReadiness() = runTest {
        val subscriptions = DataSubscriptions(this)
        for (type in listOf(HR, ECG)) {
            subscriptions.startTest(type, stream = { flow { emit(1); awaitCancellation() } })
        }
        subscriptions.startTest(ACC, stream = { throw IllegalStateException("controlled failure") })
        runCurrent()
        assertEquals(SubscriptionStatus.FAILED, subscriptions.status(ACC))
        assertEquals("ACC stream failed: controlled failure", subscriptions.states.value.getValue(ACC).error)
        for (type in listOf(HR, ECG)) assertEquals(SubscriptionStatus.RECEIVING, subscriptions.status(type))
        assertFalse(subscriptions.startTest(ACC, readiness = { DataReadiness() }))
        assertTrue(subscriptions.startTest(ACC, stream = { flow { emit(2); awaitCancellation() } }))
        runCurrent()
        assertEquals(SubscriptionStatus.RECEIVING, subscriptions.status(ACC))
        assertNull(subscriptions.states.value.getValue(ACC).error)
        subscriptions.stopAll()
    }

    @Test fun failureWaitsForChildCleanupBeforeRetry() = runTest {
        val subscriptions = DataSubscriptions(this)
        val cleanup = CompletableDeferred<Unit>()
        subscriptions.startTest(stream = {
            flow {
                coroutineScope {
                    launch(start = kotlinx.coroutines.CoroutineStart.UNDISPATCHED) {
                        try { awaitCancellation() }
                        finally { withContext(NonCancellable) { cleanup.await() } }
                    }
                    throw IllegalStateException("controlled failure")
                }
            }
        })
        runCurrent()
        assertFalse(subscriptions.startTest())
        cleanup.complete(Unit)
        runCurrent()
        assertEquals(SubscriptionStatus.FAILED, subscriptions.status())
        assertTrue(subscriptions.startTest())
        subscriptions.stopAll()
    }

    @Test fun cleanupAllStopsEveryTypeAndDoesNotResumeOnReconnection() = runTest {
        val subscriptions = DataSubscriptions(this)
        var connected = true
        var factories = 0
        checkedDataTypes.forEach { type -> subscriptions.startTest(type, current = { connected }, stream = {
            factories++
            flow { emit(1); awaitCancellation() }
        }) }
        runCurrent()
        connected = false
        subscriptions.stopAll()
        subscriptions.stopAll()
        runCurrent()
        checkedDataTypes.forEach { assertEquals(SubscriptionStatus.STOPPED, subscriptions.status(it)) }
        connected = true
        runCurrent()
        assertEquals(3, factories)
        checkedDataTypes.forEach { assertEquals(SubscriptionStatus.STOPPED, subscriptions.status(it)) }
    }

    @Test fun oldConnectionDataAndErrorsAreIgnored() = runTest {
        val subscriptions = DataSubscriptions(this)
        val nextEvent = CompletableDeferred<Unit>()
        var generation = 1
        val capturedGeneration = generation
        val received = mutableListOf<Int>()
        subscriptions.startTest(current = { generation == capturedGeneration }, stream = {
            flow {
                nextEvent.await()
                emit(1)
                throw IllegalStateException("old connection error")
            }
        }, onData = { received.add(it) })
        runCurrent()
        generation++
        nextEvent.complete(Unit)
        runCurrent()
        assertTrue(received.isEmpty())
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
        assertNull(subscriptions.states.value.getValue(HR).error)
        assertTrue(subscriptions.startTest())
        subscriptions.stopAll()
    }

    @Test fun cancellationCleanupErrorCannotBecomeFailureOrAffectRestart() = runTest {
        val subscriptions = DataSubscriptions(this)
        subscriptions.startTest(stream = { flow {
            try { awaitCancellation() }
            finally { throw IllegalStateException("late cleanup error") }
        } })
        runCurrent()
        subscriptions.stopAll()
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
        assertNull(subscriptions.states.value.getValue(HR).error)
        subscriptions.startTest(stream = { flow { emit(2); awaitCancellation() } })
        runCurrent()
        assertEquals(SubscriptionStatus.RECEIVING, subscriptions.status())
        subscriptions.stopAll()
    }

    @Test fun lateEmissionFromCompletedTaskCannotAffectNewTask() = runTest {
        val subscriptions = DataSubscriptions(this)
        lateinit var oldCollector: FlowCollector<Int>
        val received = mutableListOf<Int>()
        // Deliberately broken producer, confined to tests, to simulate a late callback.
        val oldFlow = object : Flow<Int> {
            override suspend fun collect(collector: FlowCollector<Int>) { oldCollector = collector }
        }
        subscriptions.startTest(stream = { oldFlow }, onData = { received.add(it) })
        runCurrent()
        assertEquals(SubscriptionStatus.STOPPED, subscriptions.status())
        subscriptions.startTest(stream = { flow { emit(2); awaitCancellation() } }, onData = { received.add(it) })
        runCurrent()
        runCatching { oldCollector.emit(1) }
        runCurrent()
        assertEquals(listOf(2), received)
        assertEquals(SubscriptionStatus.RECEIVING, subscriptions.status())
        subscriptions.stopAll()
    }
}
