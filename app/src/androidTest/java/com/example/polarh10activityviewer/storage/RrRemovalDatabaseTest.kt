package com.example.polarh10activityviewer.storage

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.session.withActivityMetrics
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

// Historical schema exists only in migration fixtures, never in new app databases.
internal fun SQLiteDatabase.addLegacyRrSchema() {
    execSQL("ALTER TABLE sessions ADD COLUMN receivedRr INTEGER NOT NULL DEFAULT 0")
    execSQL("""CREATE TABLE rr_points (sessionId TEXT NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
        recordIndex INTEGER NOT NULL, segmentId INTEGER NOT NULL, rrMs INTEGER NOT NULL, receivedAt INTEGER NOT NULL,
        elapsedMs INTEGER NOT NULL, batchIndex INTEGER NOT NULL, withinBatch INTEGER NOT NULL,
        PRIMARY KEY(sessionId, recordIndex))""")
}

class RrRemovalDatabaseTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "remove-rr-${UUID.randomUUID()}.db"
    private var db = SessionDatabase(context, name)
    @After fun close() { db.close(); context.deleteDatabase(name) }
    private fun reopen() { db.close(); db = SessionDatabase(context, name) }
    private fun assertNoRr(sql: SQLiteDatabase) {
        assertEquals(6, sql.version)
        sql.rawQuery("SELECT name FROM sqlite_master WHERE name='rr_points'", null).use { assertFalse(it.moveToFirst()) }
        sql.rawQuery("PRAGMA table_info(sessions)", null).use { c ->
            while (c.moveToNext()) assertNotEquals("receivedRr", c.getString(c.getColumnIndexOrThrow("name")))
        }
        sql.rawQuery("PRAGMA foreign_key_check", null).use { assertFalse(it.moveToFirst()) }
        sql.rawQuery("PRAGMA foreign_keys", null).use { assertTrue(it.moveToFirst()); assertEquals(1, it.getInt(0)) }
    }

    @Test fun freshDatabaseHasNoRrAndStillSavesAndReopensActivities() = runBlocking<Unit> {
        val snapshot = metricsFixture().withActivityMetrics()
        assertNoRr(db.writableDatabase)
        db.save(snapshot); reopen()
        assertNoRr(db.readableDatabase)
        assertEquals(snapshot, db.detail(snapshot.record.id))
    }

    @Test fun versionFiveDropsOnlyRrAndPreservesSavedScoreSignalsRecoveryAndCascade() = runBlocking<Unit> {
        val calculated = metricsFixture().withActivityMetrics()
        // A distinct stored score proves that the v5 migration does not recalculate it.
        val snapshot = calculated.copy(record = calculated.record.copy(summary = calculated.record.summary.copy(
            activityMetrics = calculated.record.summary.activityMetrics.copy(sessionStrainScore = 12.5))))
        val buffer = SignalBuffer()
        buffer.receiveEcg(List(130) { RawEcg(1_000_000_000L + it * 1_000_000_000L / 130, it - 65) }, 1000, 130)
        val batch = buffer.take(snapshot.record, snapshot.hrPoints, snapshot.motionPoints, true)
        db.writeSignals(listOf(batch)); db.save(snapshot)
        db.writeSignals(listOf(batch.copy(record = snapshot.record.copy(id = "staging"), hr = emptyList(), motion = emptyList())))
        val ecg = db.ecgWindow("metrics", 0, 5000)
        assertTrue(ecg.isNotEmpty())
        db.writableDatabase.apply {
            addLegacyRrSchema()
            execSQL("UPDATE sessions SET receivedRr=1")
            execSQL("INSERT INTO rr_points VALUES ('metrics',1,1,800,1000,1000,1,0)")
            version = 5
        }
        reopen()
        assertNoRr(db.readableDatabase)
        assertEquals(snapshot, db.detail("metrics"))
        assertEquals(ecg, db.ecgWindow("metrics", 0, 5000))
        assertNull(db.detail("staging"))
        db.recoverInterrupted()
        assertTrue(db.detail("staging")!!.record.collectionIncomplete)
        assertEquals(ecg, db.ecgWindow("staging", 0, 5000))
        reopen()
        assertNoRr(db.readableDatabase)
        assertEquals(snapshot, db.detail("metrics"))
        db.delete("metrics"); db.delete("staging")
        for (table in listOf("sessions", "hr_points", "motion_points", "ecg_chunks", "ecg_segments")) {
            db.readableDatabase.rawQuery("SELECT COUNT(*) FROM $table", null).use { assertTrue(it.moveToFirst()); assertEquals(0, it.getInt(0)) }
        }
    }

    @Test fun failedVersionFiveUpgradeRestoresRrSchemaAndRowsThenRetries() = runBlocking<Unit> {
        val snapshot = metricsFixture().withActivityMetrics()
        db.save(snapshot)
        db.writableDatabase.apply {
            addLegacyRrSchema()
            execSQL("INSERT INTO rr_points VALUES ('metrics',1,1,800,1000,1000,1,0)")
            setForeignKeyConstraintsEnabled(false)
            execSQL("INSERT INTO hr_points VALUES ('orphan',0,0,100,0)")
            version = 5
        }
        reopen()
        assertTrue(runCatching { db.readableDatabase }.isFailure)
        SQLiteDatabase.openDatabase(context.getDatabasePath(name).path, null, SQLiteDatabase.OPEN_READWRITE).use { sql ->
            assertEquals(5, sql.version)
            sql.rawQuery("SELECT receivedRr FROM sessions WHERE id='metrics'", null).use { assertTrue(it.moveToFirst()) }
            sql.rawQuery("SELECT rrMs FROM rr_points", null).use { assertTrue(it.moveToFirst()); assertEquals(800, it.getInt(0)) }
            sql.execSQL("DELETE FROM hr_points WHERE sessionId='orphan'")
        }
        reopen()
        assertNoRr(db.readableDatabase)
        assertEquals(snapshot, db.detail("metrics"))
    }
}
