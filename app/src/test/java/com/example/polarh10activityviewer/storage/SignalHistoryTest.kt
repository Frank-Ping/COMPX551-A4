package com.example.polarh10activityviewer.storage

import com.example.polarh10activityviewer.session.SessionSummary
import com.example.polarh10activityviewer.session.SessionRecord
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignalHistoryTest {
    private fun record(time: Long = 2000) = SessionRecord("test", 0, null, startedAt = 1, endedAt = 3000,
        durationMs = time, summary = SessionSummary(validHrCount = 1))
    private fun batch() = SignalBatch(record(), emptyList(), emptyList(), emptyList(), emptyList())

    @Test fun codecPreservesNanosecondsAndSignedVoltageExactly() {
        val points = listOf(RawEcg(9876543210987654, Int.MIN_VALUE), RawEcg(9876543218680001, Int.MAX_VALUE), RawEcg(0, 0))
        assertEquals(points, EcgCodec.decode(EcgCodec.encode(points)))
        assertTrue(runCatching { EcgCodec.decode(byteArrayOf(1)) }.isFailure)
    }

    @Test fun secondBucketsAndTailKeepEverySampleAcrossPauseAndResume() {
        val buffer = SignalBuffer()
        val first = List(260) { RawEcg(1_000_000_000L + it * 1_000_000_000L / 130, it - 130) }
        buffer.receiveEcg(first, 2000, 130)
        buffer.boundary(2000)
        val a = buffer.take(record(), emptyList(), emptyList(), true)
        assertEquals(first, a.ecg.flatMap { EcgCodec.decode(it.bytes) })
        assertTrue(a.ecg.all { it.bytes.size <= 130 * 12 })
        val next = listOf(RawEcg(100_000_000_000L, -42))
        buffer.receiveEcg(next, 2000, 130)
        val b = buffer.take(record(), emptyList(), emptyList(), true)
        assertNotEquals(a.ecg.last().segment, b.ecg.first().segment)
        assertTrue(b.ecg.first().index > a.ecg.last().index)
        assertEquals(2000L, b.segments.first().validStart)
    }

    @Test fun gapsDuplicateAndBackwardTimesCreateSegmentsWithoutDroppingValues() {
        val buffer = SignalBuffer()
        val points = listOf(RawEcg(1_000_000_000, 5), RawEcg(1_007_692_307, -5),
            RawEcg(1_100_000_000, 1), RawEcg(1_100_000_000, 2), RawEcg(1_000_000_000, 3))
        buffer.receiveEcg(points, 2000, 130)
        val result = buffer.take(record(), emptyList(), emptyList(), true)
        assertEquals(points, result.ecg.flatMap { EcgCodec.decode(it.bytes) })
        assertEquals(4, result.segments.size)
    }

    @Test fun writerRetainsFailedBatchUntilExplicitRetryAndInitializesOnce() = runTest {
        var attempts = 0; var initialized = 0; var paused = 0
        val writer = SignalWriter(this, { initialized++ }, { if (++attempts == 1) error("Disk full") }, {})
        writer.onFailure = { paused++ }
        runCurrent()
        writer.enqueue(batch()); runCurrent()
        assertEquals(1, paused); assertEquals(1, attempts)
        assertTrue(writer.pendingBytes > 0); assertTrue(writer.state.value.blocked)
        writer.retry(); runCurrent()
        assertEquals(2, attempts); assertEquals(1, initialized)
        assertEquals(0, writer.pendingBytes); assertFalse(writer.state.value.blocked)
    }

    @Test fun capacityIncludesInFlightWritesAndDrainWaitsForCommit() = runTest {
        val gate = CompletableDeferred<Unit>()
        val item = batch()
        val writer = SignalWriter(this, {}, { gate.await() }, {}, capacity = item.size * 2)
        runCurrent(); writer.enqueue(item); runCurrent()
        assertEquals(item.size, writer.pendingBytes)
        assertFalse(writer.canAccept(item.size + 1))
        writer.enqueue(item)
        gate.complete(Unit); writer.drain()
        assertEquals(0, writer.pendingBytes)
    }

    @Test fun initializationFailureCanRetryAndDiscardDeletesBeforeReleasingQueue() = runTest {
        var starts = 0; var deleted: String? = null
        val writer = SignalWriter(this, { if (++starts == 1) error("Unavailable") }, { error("Disk full") }, { deleted = it })
        runCurrent(); assertTrue(writer.state.value.blocked)
        writer.retry(); runCurrent(); assertTrue(writer.state.value.ready)
        writer.enqueue(batch()); runCurrent()
        writer.discard("test")
        assertEquals("test", deleted); assertEquals(0, writer.pendingBytes)
        assertFalse(writer.state.value.blocked)
    }
}
