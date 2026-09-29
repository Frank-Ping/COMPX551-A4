package com.example.polarh10activityviewer.session

import com.example.polarh10activityviewer.ble.ConnectionDevice
import com.example.polarh10activityviewer.ble.DataSubscriptions
import com.example.polarh10activityviewer.ble.HeartRateStatistics
import com.example.polarh10activityviewer.ble.LatestHeartRate
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.ble.checkedDataTypes
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.heartrate.HeartRateZones
import com.example.polarh10activityviewer.motion.StepDetector
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.sensor.AccBuffer
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.*
import com.polar.sdk.api.model.PolarAccelerometerData
import com.polar.sdk.api.model.PolarHrData
import java.util.UUID
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionRecordTest {
    private class Fixture(scope: CoroutineScope) {
        var now = 0L
        var wall = 1_000_000L
        var cleanupMs = 0L
        var freezeCount = 0
        var device = ConnectionDevice("Polar H10", "12345678")
        val hr = LatestHeartRate()
        val zones = HeartRateZones()
        val motion = StepDetector { now }
        val acc = AccBuffer { motion.receive(it) }
        lateinit var session: SessionController
        val subscriptions = DataSubscriptions(scope) { type, status ->
            if (type == HR && status != SubscriptionStatus.RECEIVING && session.state.value.ongoing) {
                zones.clearCurrent(session.elapsedAt(now))
            }
            hr.onSubscriptionState(type, status)
            acc.onSubscriptionState(type, status)
            if (type == ACC && session.state.value.ongoing) motion.onSubscriptionState(status)
            session.onSubscriptionState(type, status, now)
        }
        init {
            session = SessionController(subscriptions, { now }, {
                hr.reset(); zones.reset(); motion.reset(); acc.clear()
            }, {
                freezeCount++
                zones.clearCurrent(session.state.value.elapsedMs)
                hr.clear()
                motion.updateSessionTime(session.state.value.elapsedMs)
                motion.stop()
                now += cleanupMs
                wall += cleanupMs
            }, { elapsed ->
                zones.refresh(elapsed)
                motion.updateSessionTime(elapsed)
                SessionSummary.from(hr.statistics.value, zones.state.value, motion.state.value)
            }, { wall })
        }
        val record get() = session.state.value.record!!
        fun receiveHr(vararg values: Int) {
            val previousCount = hr.statistics.value.count
            val valid = hr.receive(PolarHrData(values.map {
                PolarHrData.PolarHrSample(it, 0, 0, emptyList(), emptyList(), false, true, true)
            }), wall)
            if (hr.statistics.value.count - previousCount < values.size) session.markMissing(HR)
            if (valid) session.onValidData(now, wall)
            zones.receive(hr.reading.value, valid, session.elapsedAt(now))
        }
        fun stream(type: PolarDeviceDataType, source: Flow<Int>): Boolean {
            val generation = session.state.value.generation
            return subscriptions.start(type, { session.accepts(generation) }, { session.accepts(generation) },
                { source }) { value ->
                when (type) {
                    HR -> receiveHr(value)
                    ACC -> {
                        acc.receive(PolarAccelerometerData(listOf(
                            PolarAccelerometerData.PolarAccelerometerDataSample(now * 1_000_000, 0, 0, 1000))))
                        motion.receivedBatch(now * 1_000_000, now)
                        if (motion.state.value.incompleteAcc) session.markMissing(ACC)
                        session.onValidData(now, wall)
                    }
                    ECG -> session.onValidData(now, wall)
                    else -> error("Unexpected stream")
                }
                session.refresh(generation, now)
            }
        }
        fun running(value: Int = 120) = flow { emit(value); awaitCancellation() }
        fun start(type: PolarDeviceDataType = HR, source: Flow<Int> = running()) =
            session.start(true, device) { stream(type, source) }
        fun tick(generation: Long = session.state.value.generation) = session.refresh(generation, now)
        fun stop() = session.stop("Stopped by user.", interrupted = false)
    }

    @Test fun onlyAcceptedStartCreatesUuidAndSnapshotsTheDevice() = runTest {
        val f = Fixture(this)
        assertFalse(f.session.start(false, f.device) { error("Rejected") })
        assertNull(f.session.state.value.record)
        assertTrue(f.start())
        runCurrent()
        val first = f.record
        assertEquals(first.id, UUID.fromString(first.id).toString())
        f.device = ConnectionDevice("Another device", "ABCDEF12")
        assertFalse(f.start())
        assertEquals(first, f.record)
        f.stop(); runCurrent()
        val frozen = f.record
        assertTrue(f.start()); runCurrent()
        assertNotEquals(frozen.id, f.record.id)
        assertEquals(f.device, f.record.device)
        assertEquals("12345678", frozen.device?.deviceId)
        assertEquals(first.startRequestedAt, frozen.startRequestedAt)
        f.stop()
    }

    @Test fun wallClockChangesCannotChangeDurationOrRunningRelativeTime() = runTest {
        val f = Fixture(this)
        val source = MutableSharedFlow<Int>()
        f.start(HR, source); runCurrent()
        assertEquals(1_000_000L, f.record.startRequestedAt)
        assertNull(f.record.startedAt)
        f.now = 4000; f.wall = 2_000_000
        source.emit(120); runCurrent()
        assertEquals(2_000_000L, f.record.startedAt)
        f.now = 5501; f.wall = 100
        f.tick()
        assertEquals(1501L, f.record.durationMs)
        assertEquals(1501L, f.session.elapsedAt())
        source.emit(125); runCurrent()
        assertEquals(2_000_000L, f.record.startedAt)
        f.stop()
        assertEquals(100L, f.record.endedAt)
        assertEquals(1501L, f.record.durationMs)
    }

    @Test fun noDataAndInvalidOnlyHrNeverQualifyAndUnknownMotionStaysNull() = runTest {
        for (values in listOf(emptyList(), listOf(0, -1))) {
            val f = Fixture(this)
            f.start(HR, flow { values.forEach { emit(it) } }); runCurrent()
            assertFalse(f.record.eligibleForSaving)
            assertNull(f.record.startedAt)
            assertEquals(0L, f.record.durationMs)
            assertEquals(values.isNotEmpty(), f.record.streams.getValue(HR).received)
            assertFalse(f.record.summary.receivedValidHr)
            assertNull(f.record.summary.minimumHr)
            assertNull(f.record.summary.totalSteps)
            assertNull(f.record.summary.distanceMetres)
            assertEquals(SessionStatus.STOPPED, f.session.state.value.status)
        }
    }

    @Test fun eachRealStreamCanQualifyImmediatelyWithoutMinimumDuration() = runTest {
        for (type in checkedDataTypes) {
            val f = Fixture(this)
            f.start(type); runCurrent()
            f.stop()
            assertTrue(f.record.eligibleForSaving)
            assertEquals(0L, f.record.durationMs)
            assertTrue(f.record.streams.getValue(type).received)
            assertEquals(type == HR, f.record.summary.receivedValidHr)
            if (type == ACC) {
                assertEquals(0L, f.record.summary.totalSteps)
                assertEquals(0.0, f.record.summary.distanceMetres!!, 0.0)
                assertNull(f.record.summary.maximumCadence)
                assertNull(f.record.summary.maximumSpeedMetresPerSecond)
                assertNull(f.record.summary.meanSpeedMetresPerSecond)
            } else assertNull(f.record.summary.totalSteps)
            assertNull(f.record.summary.meanCadence)
            assertNull(f.record.summary.minimumCadence)
            runCurrent()
        }
    }

    @Test fun mixedHrBatchRetainsValidStatisticsEvenWhenFinalSampleIsInvalid() = runTest {
        val f = Fixture(this)
        f.start(HR, flow { emit(0); awaitCancellation() }); runCurrent()
        f.now = 500; f.receiveHr(120, 121, 0); f.tick()
        assertTrue(f.record.eligibleForSaving)
        assertEquals(2L, f.record.summary.validHrCount)
        assertEquals(120.5, f.record.summary.meanHr!!, 0.0)
        assertNull(f.hr.reading.value)
        assertTrue(f.record.streams.getValue(HR).missing)
        f.now = 1750; f.stop()
        assertEquals(List(5) { 0L }, f.record.summary.zoneDurationsMs)
        assertEquals(1250L, f.record.summary.unclassifiedMs)
    }

    @Test fun summaryCopiesUnroundedOwnerResultsAndDoesNotAliasZoneStorage() {
        val durations = mutableListOf(1L, 2L, 3L, 4L, 5L)
        val motion = StepState(totalSteps = 7, distance = 3.14159265, maximumCadence = 96.0,
            maximumSpeed = 0.87654321, durationMs = 12345)
        val result = SessionSummary.from(HeartRateStatistics(3, 361, 120, 121),
            HeartRateZoneState(durationsMs = durations, unclassifiedMs = 19), motion)
        assertEquals(361.0 / 3, result.meanHr!!, 0.0)
        assertEquals(motion.distance, result.distanceMetres)
        assertEquals(motion.averageSpeed, result.meanSpeedMetresPerSecond)
        assertEquals(motion.maximumSpeed, result.maximumSpeedMetresPerSecond)
        assertEquals(motion.maximumCadence, result.maximumCadence)
        durations[0] = 999
        assertEquals(1L, result.zoneDurationsMs[0])
        assertNull(result.meanCadence)
        assertNull(result.minimumCadence)
    }

    @Test fun stopSettlesLastZoneIntervalOnceAndExcludesDelayedCleanup() = runTest {
        val f = Fixture(this)
        val cleanup = CompletableDeferred<Unit>()
        f.start(HR, flow {
            try { emit(120); awaitCancellation() }
            finally { withContext(NonCancellable) { cleanup.await() } }
        }); runCurrent()
        f.now = 1750; f.wall = 2_000_000; f.cleanupMs = 5000
        f.stop(); runCurrent()
        val frozen = f.record
        assertEquals(1750L, frozen.durationMs)
        assertEquals(2_000_000L, frozen.endedAt)
        assertEquals(listOf(0L, 1750L, 0L, 0L, 0L), frozen.summary.zoneDurationsMs)
        assertEquals(0L, frozen.summary.unclassifiedMs)
        assertFalse(frozen.streams.getValue(HR).missing)
        assertFalse(f.start())
        f.now = 99_000; f.wall = 999_999_999; f.tick(); f.stop()
        f.session.markMissing(HR)
        cleanup.complete(Unit); runCurrent()
        assertEquals(frozen, f.record)
        assertEquals(1, f.freezeCount)
    }

    @Test fun retryPreservesIdentityStatisticsAndFailureFlags() = runTest {
        val f = Fixture(this)
        f.session.start(true, f.device) {
            f.stream(HR, flow { emit(120); error("Lost HR") })
            f.stream(ECG, f.running())
        }; runCurrent()
        val id = f.record.id
        assertTrue(f.record.streams.getValue(HR).failed)
        f.now = 3000
        assertTrue(f.session.retry(HR, true) { f.stream(HR, f.running(140)) }); runCurrent()
        f.tick()
        assertEquals(id, f.record.id)
        assertEquals(2L, f.record.summary.validHrCount)
        assertEquals(130.0, f.record.summary.meanHr!!, 0.0)
        assertEquals(3000L, f.record.durationMs)
        assertTrue(f.record.streams.getValue(HR).missing)
        assertTrue(f.record.streams.getValue(HR).failed)
        assertFalse(f.session.retry(HR, true) { error("Duplicate") })
        f.stop()
    }

    @Test fun unavailableAndFailedStreamsAreRetainedWithoutInventingObservations() = runTest {
        val f = Fixture(this)
        f.session.start(true, f.device) {
            f.stream(HR, f.running())
            f.subscriptions.unavailable(ACC, "Not ready")
            f.stream(ECG, flow { error("Could not start") })
        }; runCurrent()
        f.stop()
        assertTrue(f.record.eligibleForSaving)
        assertTrue(f.record.incomplete)
        assertEquals(StreamObservation(missing = true), f.record.streams.getValue(ACC))
        assertEquals(StreamObservation(missing = true, failed = true), f.record.streams.getValue(ECG))
        assertNull(f.record.summary.totalSteps)
    }

    @Test fun knownAccGapPersistsAcrossRecoveryAndNewStartResetsIt() = runTest {
        val f = Fixture(this)
        val source = MutableSharedFlow<Int>()
        f.start(ACC, source); runCurrent()
        source.emit(1); runCurrent()
        f.now = 31; source.emit(1); runCurrent()
        assertTrue(f.record.streams.getValue(ACC).missing)
        assertEquals(0L, f.record.summary.totalSteps)
        f.stop(); runCurrent()
        val frozen = f.record
        f.start(ACC); runCurrent()
        assertFalse(f.record.streams.getValue(ACC).missing)
        assertTrue(frozen.streams.getValue(ACC).missing)
        f.stop()
    }

    @Test fun allStreamCompletionAndInterruptionsFreezeFinalMetadata() = runTest {
        for (reason in listOf<String?>(null, "Disconnected", "Foreground left", "Permissions lost")) {
            val f = Fixture(this)
            val end = CompletableDeferred<Unit>()
            f.start(HR, flow { emit(120); end.await() }); runCurrent()
            f.now = 2500; f.wall = 3_000_000
            if (reason == null) { end.complete(Unit); runCurrent() }
            else { f.session.stop(reason); runCurrent() }
            val frozen = f.record
            assertEquals(2500L, frozen.durationMs)
            assertEquals(3_000_000L, frozen.endedAt)
            assertEquals(reason ?: "All streams ended.", frozen.endReason)
            assertEquals(2500L, frozen.summary.zoneDurationsMs.sum())
            assertTrue(frozen.incomplete)
            f.now += 99_000; f.tick(); f.session.stop("Duplicate")
            assertEquals(frozen, f.record)
            assertEquals(1, f.freezeCount)
        }
    }

    @Test fun staleCollectorsAndOldTicksCannotChangeNewRecordOrPreviousSnapshot() = runTest {
        val f = Fixture(this)
        lateinit var oldCollector: FlowCollector<Int>
        f.start(HR, object : Flow<Int> {
            override suspend fun collect(collector: FlowCollector<Int>) {
                oldCollector = collector
                collector.emit(120)
                awaitCancellation()
            }
        }); runCurrent()
        val oldGeneration = f.session.state.value.generation
        f.now = 1000; f.stop(); runCurrent()
        val frozen = f.record
        f.start(HR, f.running(140)); runCurrent()
        val fresh = f.record
        runCatching { oldCollector.emit(199) }
        f.now = 5000; f.tick(oldGeneration); runCurrent()
        assertEquals(fresh, f.record)
        assertEquals(120, frozen.summary.maximumHr)
        assertEquals(1000L, frozen.durationMs)
        assertNotEquals(frozen.id, fresh.id)
        f.stop()
    }

    @Test fun reattachingStateCollectorsRetainsRecordAndNormalStopDoesNotMarkMissing() = runTest {
        val f = Fixture(this)
        f.session.start(true, f.device) { checkedDataTypes.forEach { f.stream(it, f.running()) } }
        runCurrent()
        val before = f.session.state.first().record
        // Rotation reuses the ViewModel owner; a new UI collector must see the same record.
        val reattached = f.session.state.first().record
        assertSame(before, reattached)
        f.now = 1500; f.tick(); f.stop(); runCurrent()
        assertEquals(before!!.id, f.record.id)
        assertFalse(f.record.incomplete)
        assertTrue(f.record.streams.values.all { it.received && !it.missing && !it.failed })
        assertEquals(0.0, f.record.summary.meanSpeedMetresPerSecond!!, 0.0)
        assertNull(f.record.summary.maximumSpeedMetresPerSecond)
    }
}
