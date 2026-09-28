package com.example.polarh10activityviewer

import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.*
import com.polar.sdk.api.model.EcgSample
import com.polar.sdk.api.model.PolarAccelerometerData
import com.polar.sdk.api.model.PolarHrData
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionStateTest {
    private class Fixture(scope: CoroutineScope) {
        var now = 0L
        var connected = true
        val hr = LatestHeartRate()
        val acc = AccBuffer()
        val ecg = EcgBuffer()
        lateinit var session: SessionController
        val subscriptions = DataSubscriptions(scope) { type, status ->
            hr.onSubscriptionState(type, status)
            acc.onSubscriptionState(type, status)
            ecg.onSubscriptionState(type, status)
            session.onSubscriptionState(status)
        }
        init {
            session = SessionController(subscriptions, { now },
                { hr.clear(); acc.clear(); ecg.clear() }, hr::clear)
        }
        val state get() = session.state.value
        fun receive(type: PolarDeviceDataType, value: Int) {
            when (type) {
                HR -> hr.receive(PolarHrData(listOf(
                    PolarHrData.PolarHrSample(value, 0, 0, emptyList(), emptyList(), false, true, true)
                )), now)
                ACC -> acc.receive(PolarAccelerometerData(listOf(
                    PolarAccelerometerData.PolarAccelerometerDataSample(now, value, 0, 1000)
                )))
                ECG -> ecg.receive(listOf(EcgSample(now, value)))
                else -> error("Unexpected test type")
            }
        }
        fun startStream(type: PolarDeviceDataType, source: Flow<Int>): Boolean {
            val generation = state.generation
            return subscriptions.start(type, { connected && session.accepts(generation) },
                { connected && session.accepts(generation) }, { source }, { receive(type, it) })
        }
        fun running(value: Int = 1) = flow { emit(value); awaitCancellation() }
        fun start(types: List<PolarDeviceDataType> = checkedDataTypes) = session.start(connected) {
            types.forEach { startStream(it, running()) }
        }
    }

    @Test fun prerequisitesRejectWithoutClearingAndCannotStartStreamsOutsideSession() = runTest {
        val f = Fixture(this)
        f.receive(ACC, 7)
        var attempts = 0
        assertFalse(f.session.start(false) { attempts++ })
        assertFalse(f.startStream(HR, f.running()))
        assertFalse(f.session.retry(ECG, true) { attempts++; true })
        assertEquals(0, attempts)
        assertEquals(7, f.acc.samples.value.single().x)
        assertEquals(SessionStatus.IDLE, f.state.status)
        assertEquals(0L, f.state.generation)
    }

    @Test fun firstSampleStartsClockOnceAndRefreshUsesElapsedTimeNotTickCount() = runTest {
        val f = Fixture(this)
        val source = MutableSharedFlow<Int>()
        f.session.start(true) { f.startStream(HR, source); f.startStream(ACC, source) }
        runCurrent()
        f.now = 5_000
        f.session.refresh(f.state.generation)
        assertEquals(SessionStatus.STARTING, f.state.status)
        assertEquals(0L, f.state.elapsedMs)
        source.emit(70)
        runCurrent()
        assertEquals(SessionStatus.RUNNING, f.state.status)
        f.now = 6_500
        source.emit(71)
        runCurrent()
        f.session.refresh(f.state.generation)
        assertEquals(1_500L, f.state.elapsedMs)
        f.now = 18_000
        f.session.refresh(f.state.generation)
        assertEquals(13_000L, f.state.elapsedMs)
        f.session.stop("Stop")
    }

    @Test fun repeatedStartAndStopDoNotClearActiveDataOrCountCleanupTime() = runTest {
        val f = Fixture(this)
        val cleanup = CompletableDeferred<Unit>()
        f.session.start(true) {
            f.startStream(HR, flow {
                try { emit(80); awaitCancellation() }
                finally { withContext(NonCancellable) { cleanup.await() } }
            })
            f.startStream(ACC, f.running(9))
        }
        runCurrent()
        val generation = f.state.generation
        f.now = 1_000
        assertFalse(f.start())
        assertEquals(80, f.hr.reading.value?.bpm)
        assertEquals(9, f.acc.samples.value.single().x)
        f.session.stop("User stopped")
        assertEquals(SessionStatus.STOPPING, f.state.status)
        assertEquals(1_000L, f.state.elapsedMs)
        assertNull(f.hr.reading.value)
        runCurrent()
        f.now = 20_000
        f.session.refresh(generation)
        f.session.stop("Must not replace reason")
        assertFalse(f.start())
        assertFalse(f.session.retry(ECG, true) { error("Retry during stop") })
        assertEquals(1_000L, f.state.elapsedMs)
        assertEquals("User stopped", f.state.endReason)
        assertTrue(f.connected)
        cleanup.complete(Unit)
        runCurrent()
        assertEquals(SessionStatus.STOPPED, f.state.status)
        assertEquals(1_000L, f.state.elapsedMs)
        assertEquals(9, f.acc.samples.value.single().x)
        assertTrue(f.session.start(true) { f.startStream(HR, flow { awaitCancellation() }) })
        assertEquals(generation + 1, f.state.generation)
        assertEquals(0L, f.state.elapsedMs)
        assertTrue(f.acc.samples.value.isEmpty())
        f.session.stop("Done")
    }

    @Test fun newSessionClearsBuffersEvenWhenThoseStreamsCannotStart() = runTest {
        val f = Fixture(this)
        checkedDataTypes.forEach { f.receive(it, 42) }
        assertTrue(f.session.start(true) {
            f.startStream(HR, flow { awaitCancellation() })
            f.subscriptions.unavailable(ACC, "ACC configuration incomplete")
            f.subscriptions.unavailable(ECG, "ECG not ready")
        })
        assertNull(f.hr.reading.value)
        assertTrue(f.acc.samples.value.isEmpty())
        assertTrue(f.ecg.samples.value.isEmpty())
        assertEquals(SessionStatus.STARTING, f.state.status)
        assertEquals("ECG not ready", f.subscriptions.states.value.getValue(ECG).error)
        f.session.stop("Stopped before data")
        runCurrent()
        assertEquals(SessionStatus.STOPPED, f.state.status)
        assertEquals(0L, f.state.elapsedMs)
    }

    @Test fun immediateFailureCannotEndSessionBeforeOtherStartupAttempts() = runTest {
        val f = Fixture(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
        val attempted = mutableListOf<PolarDeviceDataType>()
        assertTrue(f.session.start(true) {
            for (type in checkedDataTypes) {
                attempted += type
                if (type == ECG) f.startStream(type, f.running(55))
                else f.startStream(type, flow { error("Immediate $type failure") })
            }
        })
        assertEquals(checkedDataTypes, attempted)
        assertEquals(SessionStatus.RUNNING, f.state.status)
        assertEquals(55, f.ecg.samples.value.single().voltage)
        f.session.stop("Done")
    }

    @Test fun allStartupFailuresEndAtZeroAndAllowANewAttempt() = runTest {
        val f = Fixture(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
        f.session.start(true) {
            checkedDataTypes.forEach { f.startStream(it, flow { error("Cannot start") }) }
        }
        assertEquals(SessionStatus.STOPPED, f.state.status)
        assertEquals(0L, f.state.elapsedMs)
        assertTrue(f.state.endReason!!.contains("No data"))
        assertTrue(f.start(listOf(HR)))
        assertEquals(SessionStatus.RUNNING, f.state.status)
        f.session.stop("Done")
    }

    @Test fun retryRechecksOneStreamAndPreservesSessionTimeAndOtherReadings() = runTest {
        val f = Fixture(this)
        f.session.start(true) {
            f.startStream(HR, f.running(75))
            f.startStream(ACC, f.running(5))
            f.startStream(ECG, flow { emit(-10); error("ECG failure") })
        }
        runCurrent()
        val generation = f.state.generation
        assertEquals(SessionStatus.RUNNING, f.state.status)
        assertEquals(-10, f.ecg.samples.value.single().voltage)
        assertFalse(f.session.retry(ECG, false) { error("Prerequisites unavailable") })
        assertFalse(f.session.retry(HR, true) { error("Duplicate") })
        f.now = 4_000
        var settingsChecks = 0
        assertTrue(f.session.retry(ECG, true) {
            f.startStream(ECG, flow { settingsChecks++; emit(20); awaitCancellation() })
        })
        assertTrue(f.ecg.samples.value.isEmpty())
        assertEquals(75, f.hr.reading.value?.bpm)
        assertEquals(5, f.acc.samples.value.single().x)
        runCurrent()
        f.session.refresh(generation)
        assertEquals(1, settingsChecks)
        assertEquals(4_000L, f.state.elapsedMs)
        assertEquals(generation, f.state.generation)
        assertNull(f.subscriptions.states.value.getValue(ECG).error)
        f.session.stop("Done")
    }

    @Test fun naturalCompletionEndsOnlyAfterLastTaskAndFreezesTime() = runTest {
        val f = Fixture(this)
        val end = CompletableDeferred<Unit>()
        f.session.start(true) {
            f.startStream(HR, flow { emit(70) })
            f.startStream(ECG, flow { emit(10); end.await() })
        }
        runCurrent()
        assertEquals(SessionStatus.RUNNING, f.state.status)
        f.now = 2_000
        end.complete(Unit)
        runCurrent()
        assertEquals(SessionStatus.STOPPED, f.state.status)
        assertEquals("All streams ended.", f.state.endReason)
        assertEquals(2_000L, f.state.elapsedMs)
        assertNull(f.hr.reading.value)
        assertEquals(10, f.ecg.samples.value.single().voltage)
        f.now = 9_000
        f.session.refresh(f.state.generation)
        assertEquals(2_000L, f.state.elapsedMs)
    }

    @Test fun oldDataAndTimerCannotAffectStoppedOrNewSession() = runTest {
        val f = Fixture(this)
        lateinit var oldCollector: FlowCollector<Int>
        f.session.start(true) {
            f.startStream(HR, object : Flow<Int> {
                override suspend fun collect(collector: FlowCollector<Int>) {
                    oldCollector = collector
                    collector.emit(70)
                    awaitCancellation()
                }
            })
        }
        runCurrent()
        val oldGeneration = f.state.generation
        f.now = 1_000
        f.session.stop("Stop")
        runCatching { oldCollector.emit(99) }
        runCurrent()
        assertNull(f.hr.reading.value)
        f.now = 2_000
        f.start(listOf(HR))
        runCurrent()
        f.now = 3_000
        f.session.refresh(oldGeneration)
        runCatching { oldCollector.emit(99) }
        runCurrent()
        assertEquals(0L, f.state.elapsedMs)
        assertEquals(1, f.hr.reading.value?.bpm)
        f.session.refresh(f.state.generation)
        assertEquals(1_000L, f.state.elapsedMs)
        f.session.stop("Done")
    }

    @Test fun interruptionFreezesAndCleansAllStreamsWithoutAutomaticResume() = runTest {
        val f = Fixture(this)
        for (reason in listOf("Disconnected", "Bluetooth unavailable", "Permissions lost", "Foreground left", "SDK released")) {
            f.connected = true
            f.start()
            runCurrent()
            f.now += 500
            f.session.stop(reason)
            f.connected = false
            runCurrent()
            assertEquals(SessionStatus.STOPPED, f.state.status)
            assertEquals(reason, f.state.endReason)
            assertEquals(500L, f.state.elapsedMs)
            assertNull(f.hr.reading.value)
            assertTrue(f.acc.samples.value.isNotEmpty())
            assertTrue(f.ecg.samples.value.isNotEmpty())
            assertTrue(checkedDataTypes.none(f.subscriptions::isActive))
            f.connected = true
            f.now += 1_000
            f.session.refresh(f.state.generation)
            assertEquals(SessionStatus.STOPPED, f.state.status)
            assertEquals(500L, f.state.elapsedMs)
        }
    }
}
