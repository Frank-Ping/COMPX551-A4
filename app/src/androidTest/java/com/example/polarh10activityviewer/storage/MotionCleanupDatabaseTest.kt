package com.example.polarh10activityviewer.storage

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.session.withActivityMetrics
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

class MotionCleanupDatabaseTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "motion-cleanup-${UUID.randomUUID()}.db"
    private var db = SessionDatabase(context, name)
    private val retired = listOf("minimumCadence", "distanceMetres", "meanSpeedMetresPerSecond", "maximumSpeedMetresPerSecond")
    @After fun close() { db.close(); context.deleteDatabase(name) }
    private fun reopen() { db.close(); db = SessionDatabase(context, name) }

    private fun versionSix(sql: SQLiteDatabase) {
        retired.forEach { sql.execSQL("ALTER TABLE sessions ADD COLUMN $it REAL") }
        sql.execSQL("ALTER TABLE motion_points ADD COLUMN speedMetresPerSecond REAL")
        sql.execSQL("UPDATE sessions SET minimumCadence=12, distanceMetres=123.45, meanSpeedMetresPerSecond=1.25, maximumSpeedMetresPerSecond=3.5")
        sql.execSQL("UPDATE motion_points SET speedMetresPerSecond=1.25")
        sql.version = 6
    }

    private fun columns(sql: SQLiteDatabase, table: String) = sql.rawQuery("PRAGMA table_info($table)", null).use { c ->
        buildList { while (c.moveToNext()) add(c.getString(c.getColumnIndexOrThrow("name"))) }
    }

    private fun assertCurrentSchema(sql: SQLiteDatabase) {
        assertEquals(7, sql.version)
        assertTrue(columns(sql, "sessions").none { it in retired })
        assertFalse("speedMetresPerSecond" in columns(sql, "motion_points"))
        sql.rawQuery("PRAGMA foreign_key_check", null).use { assertFalse(it.moveToFirst()) }
        sql.rawQuery("PRAGMA foreign_keys", null).use { assertTrue(it.moveToFirst()); assertEquals(1, it.getInt(0)) }
    }

    @Test fun upgradePreservesActivitiesScoresAllSignalsAndCascade() = runBlocking<Unit> {
        val calculated = metricsFixture().withActivityMetrics()
        val snapshot = calculated.copy(record = calculated.record.copy(summary = calculated.record.summary.copy(
            activityMetrics = calculated.record.summary.activityMetrics.copy(sessionStrainScore = 12.3456789))))
        assertCurrentSchema(db.writableDatabase)
        val buffer = SignalBuffer()
        buffer.receiveEcg(List(130) { RawEcg(1_000_000_000L + it * 1_000_000_000L / 130, it - 65) }, 1000, 130)
        db.writeSignals(listOf(buffer.take(snapshot.record, snapshot.hrPoints, snapshot.motionPoints, true)))
        db.save(snapshot)
        val ecg = db.ecgWindow(snapshot.record.id, 0, 5000)
        assertTrue(ecg.isNotEmpty())
        versionSix(db.writableDatabase)
        reopen()
        assertCurrentSchema(db.readableDatabase)
        assertEquals(snapshot, db.detail(snapshot.record.id))
        assertEquals(ecg, db.ecgWindow(snapshot.record.id, 0, 5000))
        reopen()
        assertEquals(snapshot, db.detail(snapshot.record.id))
        db.delete(snapshot.record.id)
        for (table in listOf("sessions", "hr_points", "motion_points", "ecg_segments", "ecg_chunks")) {
            db.readableDatabase.rawQuery("SELECT COUNT(*) FROM $table", null).use {
                assertTrue(it.moveToFirst()); assertEquals(0, it.getInt(0))
            }
        }
    }

    @Test fun failedUpgradeRollsBackColumnsAndDataBeforeRetry() = runBlocking<Unit> {
        val snapshot = metricsFixture().withActivityMetrics()
        db.save(snapshot)
        versionSix(db.writableDatabase)
        db.writableDatabase.apply {
            setForeignKeyConstraintsEnabled(false)
            execSQL("INSERT INTO motion_points (sessionId, secondBucket, elapsedMs, cadence, breakBefore, speedMetresPerSecond) VALUES ('orphan',0,0,120,1,1.25)")
        }
        reopen()
        assertTrue(runCatching { db.readableDatabase }.isFailure)
        SQLiteDatabase.openDatabase(context.getDatabasePath(name).path, null, SQLiteDatabase.OPEN_READWRITE).use { sql ->
            assertEquals(6, sql.version)
            assertTrue(columns(sql, "sessions").containsAll(retired))
            sql.rawQuery("SELECT distanceMetres FROM sessions WHERE id=?", arrayOf(snapshot.record.id)).use {
                assertTrue(it.moveToFirst()); assertEquals(123.45, it.getDouble(0), 0.0)
            }
            sql.rawQuery("SELECT speedMetresPerSecond FROM motion_points WHERE sessionId='orphan'", null).use {
                assertTrue(it.moveToFirst()); assertEquals(1.25, it.getDouble(0), 0.0)
            }
            sql.execSQL("DELETE FROM motion_points WHERE sessionId='orphan'")
        }
        reopen()
        assertCurrentSchema(db.readableDatabase)
        assertEquals(snapshot, db.detail(snapshot.record.id))
    }
}
