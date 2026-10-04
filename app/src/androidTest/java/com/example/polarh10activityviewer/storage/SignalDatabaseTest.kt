package com.example.polarh10activityviewer.storage

import android.os.SystemClock
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.polarh10activityviewer.session.ActivityMetrics
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SessionSnapshot
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class SignalDatabaseTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var db: SessionDatabase
    private lateinit var name: String
    @Before fun open() { name = "signals-${UUID.randomUUID()}.db"; db = SessionDatabase(context, name) }
    @After fun close() { db.close(); context.deleteDatabase(name) }

    private fun batch(id: String = "signals", duration: Long = 6000): SignalBatch {
        val buffer = SignalBuffer()
        // Batches mimic reception at one-second boundaries, with sensor-relative spacing.
        for (second in 0..5) buffer.receiveEcg(List(130) { i ->
            RawEcg(1_000_000_000L + second * 1_000_000_000L + i * 1_000_000_000L / 130, i-65)
        }, (second+1)*1000L, 130)
        buffer.receiveRr(List(130) { 800 }, duration, 8000, 8000)
        buffer.boundary(duration)
        val record = SessionRecord(id, 0, null, startedAt = 1, endedAt = 8000, durationMs = duration, receivedRr = true)
        return buffer.take(record, emptyList(), emptyList(), true)
    }

    @Test fun stagingHiddenRecoveryPreservesPointsAndArchiveIsIdempotent() = runBlocking<Unit> {
        val data = batch()
        db.writeSignals(listOf(data))
        assertTrue(db.page().isEmpty()); assertNull(db.detail("signals"))
        db.close(); db = SessionDatabase(context, name)
        db.recoverInterrupted(); db.recoverInterrupted()
        val recovered = db.detail("signals")!!
        assertTrue(recovered.record.collectionIncomplete)
        assertEquals(6000L, recovered.record.durationMs)
        assertEquals(8000L, recovered.record.endedAt)
        assertEquals(1, db.page().size)
        assertEquals(130L, db.rrCount("signals"))
        val rr = db.rrWindow("signals", 61)
        assertEquals(60, rr.size); assertEquals(61.0, rr.first().elapsedMs, 0.0)
        assertTrue(rr.all { it.value == 800.0 })
        val ecg = db.ecgWindow("signals", 0, 5000)
        assertTrue(ecg.size in 649..652)
        assertTrue(ecg.any { it.value!! < 0 }); assertTrue(ecg.any { it.value!! > 0 })
    }

    @Test fun finalizationRetriesDoNotDuplicateAndDeletionRemovesAllSignalTables() = runBlocking<Unit> {
        val data = batch()
        db.writeSignals(listOf(data)); db.writeSignals(listOf(data))
        assertEquals(130L, db.rrCount("signals"))
        val snapshot = SessionSnapshot(data.record, emptyList(), emptyList())
        db.save(snapshot); db.save(snapshot); db.recoverInterrupted()
        assertFalse(db.detail("signals")!!.record.collectionIncomplete)
        assertEquals(1, db.page().size)
        db.delete("signals")
        for (table in listOf("sessions", "ecg_chunks", "ecg_segments", "rr_points")) {
            db.readableDatabase.rawQuery("SELECT COUNT(*) FROM $table", null).use { c -> c.moveToFirst(); assertEquals(0, c.getInt(0)) }
        }
    }

    @Test fun failedGroupRollsBackCheckpointAndSamplesThenRetrySucceeds() = runBlocking<Unit> {
        val valid = batch()
        val invalid = valid.copy(record = SessionRecord("invalid", 0, null))
        assertTrue(runCatching { db.writeSignals(listOf(valid, invalid)) }.isFailure)
        assertEquals(0L, db.rrCount("signals")); db.recoverInterrupted(); assertTrue(db.page().isEmpty())
        db.writeSignals(listOf(valid)); db.recoverInterrupted(); assertEquals(1, db.page().size)
    }

    @Test fun versionOneMigrationPreservesExistingActivityAndHasNoInventedSignals() = runBlocking<Unit> {
        val old = databaseFixture()
        db.save(old)
        val sql = db.writableDatabase
        for (table in listOf("rr_points", "ecg_chunks", "ecg_segments")) sql.execSQL("DROP TABLE $table")
        for (column in listOf("commitState", "collectionIncomplete", "receivedRr", "intensity", "cardioLoad", "cadenceCvPercent", "cadencePointCount", "metricsVersion", "sessionStrain", "sessionStrainScore")) sql.execSQL("ALTER TABLE sessions DROP COLUMN $column")
        sql.version = 1
        db.close(); db = SessionDatabase(context, name)
        db.recoverInterrupted()
        assertEquals(5, db.readableDatabase.version)
        assertEquals(old.copy(record = old.record.copy(summary = old.record.summary.copy(activityMetrics = ActivityMetrics(algorithmVersion = 2)))), db.detail(old.record.id))
        assertTrue(db.ecgWindow(old.record.id, 0, 5000).isEmpty())
        assertEquals(0L, db.rrCount(old.record.id))
    }

    @Test fun emptyStagingIsRemovedButOrdinarySavedIncompleteFlagDoesNotBecomeProcessArchive() = runBlocking<Unit> {
        val old = databaseFixture()
        db.save(old); db.recoverInterrupted()
        assertTrue(db.detail(old.record.id)!!.record.incomplete)
        assertFalse(db.detail(old.record.id)!!.record.collectionIncomplete)
        val empty = batch("empty").copy(ecg = emptyList(), rr = emptyList())
        db.writeSignals(listOf(empty)); db.recoverInterrupted()
        assertNull(db.detail("empty"))
    }

    @Test fun fourHourGeneratedDataIsStoredInChunksAndWindowQueryStaysBounded() = runBlocking<Unit> {
        val buffer = SignalBuffer()
        val started = SystemClock.elapsedRealtime()
        val runtime = Runtime.getRuntime()
        var peakHeap = 0L
        val pending = mutableListOf<SignalBatch>()
        for (second in 0 until 14_400) {
            val elapsed = (second + 1) * 1000L
            buffer.receiveEcg(List(130) { i -> RawEcg(1_000_000_000L + second * 1_000_000_000L + i * 1_000_000_000L/130, i-65) }, elapsed, 130)
            buffer.receiveRr(listOf(500, 500), elapsed, elapsed, elapsed)
            val record = SessionRecord("long", 0, null, startedAt = 1, endedAt = elapsed, durationMs = elapsed, receivedRr = true)
            pending.add(buffer.take(record, emptyList(), emptyList(), true))
            if (pending.size == 5) { db.writeSignals(pending.toList()); pending.clear() }
            peakHeap = maxOf(peakHeap, runtime.totalMemory() - runtime.freeMemory())
            assertTrue(buffer.bytes < 10_000)
        }
        if (pending.isNotEmpty()) db.writeSignals(pending)
        val writeMs = SystemClock.elapsedRealtime() - started
        db.recoverInterrupted()
        val queryStart = SystemClock.elapsedRealtime()
        val window = db.ecgWindow("long", 14_395_000, 14_400_000)
        val queryMs = SystemClock.elapsedRealtime() - queryStart
        assertTrue(window.size in 649..652)
        assertEquals(28_800L, db.rrCount("long"))
        db.readableDatabase.rawQuery("SELECT SUM(length(samples))/12, COUNT(*) FROM ecg_chunks", null).use {
            it.moveToFirst(); assertEquals(1_872_000L, it.getLong(0)); assertEquals(14_400L, it.getLong(1))
        }
        Log.i("Step90a", "fourHour: samples=1872000 writeMs=$writeMs queryMs=$queryMs peakProcessHeapBytes=$peakHeap dbBytes=${context.getDatabasePath(name).length()}")
    }
}
