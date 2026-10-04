package com.example.polarh10activityviewer.session

import com.example.polarh10activityviewer.ble.DataSubscriptions
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.HR
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionTimeLimitTest {
    private class Fixture(scope: CoroutineScope) {
        var now = 1000L
        var wall = 100_000L
        var freezes = 0
        var summaryTime = -1L
        lateinit var session: SessionController
        val subscriptions = DataSubscriptions(scope) { type, status -> session.onSubscriptionState(type, status, now) }
        init {
            session = SessionController(subscriptions, { now }, {}, {}, {
                summaryTime = it
                SessionSummary(validHrCount = 1, unclassifiedMs = it)
            }, { wall }, { freezes++ })
        }
        fun start(cleanup: CompletableDeferred<Unit>? = null) = session.start(true) {
            val generation = session.state.value.generation
            subscriptions.start(HR, { true }, { session.accepts(generation) }, {
                flow {
                    try { emit(120); awaitCancellation() }
                    finally { if (cleanup != null) withContext(NonCancellable) { cleanup.await() } }
                }
            }) { session.onValidData(now, wall) }
        }
    }

    @Test fun beforeBoundaryRunsAndExactBoundaryEndsOnceWithoutWaitingForCleanup() = runTest {
        val f = Fixture(this)
        val cleanup = CompletableDeferred<Unit>()
        f.start(cleanup); runCurrent()
        f.now += SessionController.TIME_LIMIT_MS - 1
        f.session.refresh(f.session.state.value.generation)
        assertEquals(SessionStatus.RUNNING, f.session.state.value.status)
        f.now++; f.session.refresh(f.session.state.value.generation); runCurrent()
        assertEquals(SessionStatus.STOPPING, f.session.state.value.status)
        assertEquals("TIME_LIMIT", f.session.state.value.endReason)
        assertEquals(SessionController.TIME_LIMIT_MS, f.summaryTime)
        val record = f.session.state.value.record
        f.now += 50_000; f.wall += 50_000
        f.session.stop("Late Stop"); f.session.refresh(f.session.state.value.generation)
        cleanup.complete(Unit); runCurrent()
        assertEquals(1, f.freezes)
        assertEquals(record, f.session.state.value.record)
        assertEquals(SessionStatus.STOPPED, f.session.state.value.status)
    }

    @Test fun delayedTickClampsDurationAndProjectsEndDateWithoutUsingDateForDuration() = runTest {
        val f = Fixture(this)
        f.start(); runCurrent()
        f.now += SessionController.TIME_LIMIT_MS + 1234
        f.wall = -10_000
        f.session.refresh(f.session.state.value.generation); runCurrent()
        val record = f.session.state.value.record!!
        assertEquals(SessionController.TIME_LIMIT_MS, record.durationMs)
        assertEquals(-11_234L, record.endedAt)
        assertEquals(100_000L, record.startedAt)
        assertFalse(record.interrupted)
        assertEquals(SessionController.TIME_LIMIT_MS, record.summary.unclassifiedMs)
    }

    @Test fun lateEventGuardRejectsMutationAndTerminationAndStopUseSameCutoff() = runTest {
        for (entry in 0..2) {
            val f = Fixture(this)
            f.start(); runCurrent()
            f.now += SessionController.TIME_LIMIT_MS + 9000
            var accepted = false
            when (entry) {
                0 -> if (!f.session.checkTimeLimit()) accepted = true
                1 -> f.session.stop("Disconnected")
                2 -> f.session.onSubscriptionState(HR, SubscriptionStatus.FAILED)
            }
            runCurrent()
            assertFalse(accepted)
            assertEquals("TIME_LIMIT", f.session.state.value.endReason)
            assertEquals(SessionController.TIME_LIMIT_MS, f.session.state.value.record!!.durationMs)
            assertEquals(1, f.freezes)
        }
    }

    @Test fun startingWaitAndOldGenerationCannotEndFreshSession() = runTest {
        val f = Fixture(this)
        f.start()
        f.now += SessionController.TIME_LIMIT_MS + 50_000
        assertFalse(f.session.checkTimeLimit())
        runCurrent()
        assertEquals(SessionStatus.RUNNING, f.session.state.value.status)
        assertEquals(0L, f.session.elapsedAt())
        val oldGeneration = f.session.state.value.generation
        f.session.stop("Stop"); runCurrent()
        f.start(); runCurrent()
        f.now += SessionController.TIME_LIMIT_MS
        f.session.refresh(oldGeneration)
        assertEquals(SessionStatus.RUNNING, f.session.state.value.status)
        assertEquals(SessionController.TIME_LIMIT_MS, f.session.elapsedAt())
        f.session.refresh(f.session.state.value.generation); runCurrent()
        assertEquals("TIME_LIMIT", f.session.state.value.endReason)
    }
}
