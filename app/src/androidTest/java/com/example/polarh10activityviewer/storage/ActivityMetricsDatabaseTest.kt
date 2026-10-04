package com.example.polarh10activityviewer.storage

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.session.*
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.UUID

internal fun metricsFixture(id: String = "metrics"): SessionSnapshot {
    val s = databaseFixture(id)
    return s.copy(record = s.record.copy(durationMs = 1_800_000, endedAt = s.record.startedAt!! + 1_800_000,
        streams = s.record.streams + (PolarDeviceDataType.ACC to StreamObservation(true)),
        summary = s.record.summary.copy(zoneDurationsMs = listOf(0,600_000,900_000,300_000,0),
            meanCadence = 120.0, totalSteps = 3600)),
        motionPoints = List(30) { i -> MotionHistoryPoint(id, i.toLong(), i * 1000L,
            listOf(100.0,110.0,120.0)[i%3], i%5==0) })
}

class ActivityMetricsDatabaseTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "metrics-${UUID.randomUUID()}.db"
    private lateinit var db: SessionDatabase
    @Before fun open() { db = SessionDatabase(context, name) }
    @After fun close() { db.close(); context.deleteDatabase(name) }
    private fun reopen() { db.close(); db = SessionDatabase(context, name) }
    private fun emulateV3() {
        db.writableDatabase.execSQL("ALTER TABLE sessions DROP COLUMN sessionStrainScore")
        db.writableDatabase.execSQL("ALTER TABLE sessions ADD COLUMN sessionRpe INTEGER")
        db.writableDatabase.execSQL("ALTER TABLE sessions ADD COLUMN ratedAt INTEGER")
        db.writableDatabase.execSQL("UPDATE sessions SET sessionRpe=6,ratedAt=12345,sessionStrain=180,metricsVersion=1")
        db.writableDatabase.addLegacyRrSchema()
        db.writableDatabase.version = 3
    }
    private fun columns(sql: SQLiteDatabase) = sql.rawQuery("PRAGMA table_info(sessions)", null).use { c ->
        buildList { while(c.moveToNext()) add(c.getString(c.getColumnIndexOrThrow("name"))) }
    }

    @Test fun automaticResultsRoundTripAndDuplicateSaveDoesNotOverwriteThem() = runBlocking<Unit> {
        val s = metricsFixture().withActivityMetrics()
        db.save(s); reopen()
        assertEquals(s, db.detail("metrics"))
        assertEquals(278.5, db.detail("metrics")!!.record.summary.activityMetrics.sessionStrain!!, 1e-10)
        db.readableDatabase.rawQuery("SELECT sessionStrainScore FROM sessions WHERE id='metrics'",null).use {
            assertTrue(it.moveToFirst())
            assertEquals(73.57992073976222,it.getDouble(0),1e-10)
        }
        db.save(metricsFixture())
        assertEquals(s, db.detail("metrics"))
        assertFalse(columns(db.readableDatabase).contains("sessionRpe"))
        assertFalse(columns(db.readableDatabase).contains("ratedAt"))
        db.delete("metrics"); assertNull(db.detail("metrics"))
    }

    @Test fun versionThreeReplacesRpeAndPreservesAllHistorySignalDataAndCascade() = runBlocking<Unit> {
        val s = metricsFixture().withActivityMetrics()
        val buffer = SignalBuffer()
        buffer.receiveEcg(List(130) { RawEcg(1_000_000_000L + it * 1_000_000_000L / 130, it - 65) }, 1000, 130)
        buffer.boundary(1000)
        db.writeSignals(listOf(buffer.take(s.record, s.hrPoints, s.motionPoints, true)))
        db.save(s)
        val ecg = db.ecgWindow("metrics",0,5000)
        assertTrue(ecg.isNotEmpty())
        emulateV3(); reopen()
        assertEquals(s, db.detail("metrics"))
        assertEquals(7, db.readableDatabase.version)
        assertEquals(ecg,db.ecgWindow("metrics",0,5000))
        assertFalse(columns(db.readableDatabase).contains("sessionRpe"))
        db.readableDatabase.rawQuery("PRAGMA foreign_keys",null).use { assertTrue(it.moveToFirst()); assertEquals(1,it.getInt(0)) }
        db.readableDatabase.rawQuery("PRAGMA foreign_key_check",null).use { assertFalse(it.moveToFirst()) }
        db.delete("metrics")
        for (table in listOf("hr_points","motion_points","ecg_chunks","ecg_segments")) {
            db.readableDatabase.rawQuery("SELECT COUNT(*) FROM $table",null).use {
                assertTrue(it.moveToFirst()); assertEquals(0,it.getInt(0))
            }
        }
    }

    @Test fun versionTwoOnlyBackfillsStrainAndLeavesOtherUncomputedMetricsNull() = runBlocking<Unit> {
        val old = metricsFixture()
        db.save(old)
        for (column in listOf("intensity","cardioLoad","cadenceCvPercent","cadencePointCount","metricsVersion","sessionStrain","sessionStrainScore"))
            db.writableDatabase.execSQL("ALTER TABLE sessions DROP COLUMN $column")
        db.writableDatabase.addLegacyRrSchema()
        db.writableDatabase.version = 2
        reopen()
        val expected = old.copy(record=old.record.copy(summary=old.record.summary.copy(
            activityMetrics=ActivityMetrics(algorithmVersion=2,sessionStrain=278.5,sessionStrainScore=ActivityMetricsCalculator.strainScore(278.5)))))
        assertEquals(expected,db.detail("metrics"))
        assertEquals(7, db.readableDatabase.version)
    }

    @Test fun migrationFailureRollsBackSchemaDataAndVersionThenCanRetry() = runBlocking<Unit> {
        db.save(metricsFixture().withActivityMetrics())
        emulateV3()
        db.writableDatabase.setForeignKeyConstraintsEnabled(false)
        db.writableDatabase.execSQL("INSERT INTO hr_points VALUES ('orphan',0,0,100,0)")
        reopen()
        assertTrue(runCatching { db.readableDatabase }.isFailure)
        SQLiteDatabase.openDatabase(context.getDatabasePath(name).path,null,SQLiteDatabase.OPEN_READWRITE).use { sql ->
            assertEquals(3,sql.version)
            assertTrue(columns(sql).contains("sessionRpe"))
            sql.rawQuery("SELECT sessionStrain FROM sessions WHERE id='metrics'",null).use {
                assertTrue(it.moveToFirst()); assertEquals(180.0,it.getDouble(0),0.0)
            }
            sql.execSQL("DELETE FROM hr_points WHERE sessionId='orphan'")
        }
        reopen()
        assertEquals(278.5,db.detail("metrics")!!.record.summary.activityMetrics.sessionStrain!!,1e-10)
    }

    @Test fun migrationClearsOldStrainForIncompleteAccArchivesAndStagingRecords() = runBlocking<Unit> {
        val s = metricsFixture()
        val incomplete = s.copy(record=s.record.copy(streams=s.record.streams +
            (PolarDeviceDataType.ACC to StreamObservation(true,missing=true))))
        db.save(incomplete)
        val archived = metricsFixture("archive")
        db.save(archived.copy(record=archived.record.copy(collectionIncomplete=true)))
        val staging = metricsFixture("staging")
        db.writeSignals(listOf(SignalBatch(staging.record,emptyList(),emptyList(),staging.hrPoints,staging.motionPoints)))
        emulateV3(); reopen()
        assertNull(db.detail("metrics")!!.record.summary.activityMetrics.sessionStrain)
        assertNull(db.detail("archive")!!.record.summary.activityMetrics.sessionStrain)
        assertNull(db.detail("staging"))
        db.recoverInterrupted()
        assertEquals(ActivityMetrics(),db.detail("staging")!!.record.summary.activityMetrics)
    }

    @Test fun finalSaveFailureRollsBackMetricsAndRetryPreservesCalculatedValue() = runBlocking<Unit> {
        val s = metricsFixture().withActivityMetrics()
        assertTrue(runCatching { db.save(s.copy(motionPoints=s.motionPoints+s.motionPoints.last())) }.isFailure)
        assertNull(db.detail("metrics"))
        db.save(s); reopen()
        assertEquals(s,db.detail("metrics"))
    }

    @Test fun versionFourBackfillsFullPrecisionScoreAndPreservesRawNullZeroAndArchives() = runBlocking<Unit> {
        val old = listOf(null, 0.0, 1.8, 100.0, 900.0).mapIndexed { i, raw ->
            val s = metricsFixture("old-$i").withActivityMetrics()
            s.copy(record=s.record.copy(summary=s.record.summary.copy(
                activityMetrics=s.record.summary.activityMetrics.copy(sessionStrain=raw,sessionStrainScore=null))))
        }
        old.forEach { db.save(it) }
        val archived = metricsFixture("archived").let { it.copy(record=it.record.copy(collectionIncomplete=true)) }.withActivityMetrics()
        db.save(archived)
        db.writableDatabase.execSQL("ALTER TABLE sessions DROP COLUMN sessionStrainScore")
        db.writableDatabase.addLegacyRrSchema()
        db.writableDatabase.version=4
        reopen()
        assertEquals(7, db.readableDatabase.version)
        for (s in old) {
            val m=s.record.summary.activityMetrics
            val expected=s.copy(record=s.record.copy(summary=s.record.summary.copy(
                activityMetrics=m.copy(sessionStrainScore=ActivityMetricsCalculator.strainScore(m.sessionStrain)))))
            assertEquals(expected,db.detail(s.record.id))
        }
        assertEquals(archived,db.detail("archived"))
        val saved=db.detail("old-2")!!
        reopen()
        assertEquals(saved,db.detail("old-2"))
    }

    @Test fun scoreBackfillFailureRollsBackColumnAndVersionThenRetries() = runBlocking<Unit> {
        val s=metricsFixture().withActivityMetrics()
        db.save(s)
        db.writableDatabase.execSQL("ALTER TABLE sessions DROP COLUMN sessionStrainScore")
        db.writableDatabase.execSQL("CREATE TRIGGER fail_score BEFORE UPDATE ON sessions BEGIN SELECT RAISE(ABORT,'injected'); END")
        db.writableDatabase.addLegacyRrSchema()
        db.writableDatabase.version=4
        reopen()
        assertTrue(runCatching { db.readableDatabase }.isFailure)
        SQLiteDatabase.openDatabase(context.getDatabasePath(name).path,null,SQLiteDatabase.OPEN_READWRITE).use { sql ->
            assertEquals(4,sql.version)
            assertFalse(columns(sql).contains("sessionStrainScore"))
            sql.rawQuery("SELECT sessionStrain FROM sessions WHERE id='metrics'",null).use {
                assertTrue(it.moveToFirst()); assertEquals(278.5,it.getDouble(0),0.0)
            }
            sql.execSQL("DROP TRIGGER fail_score")
        }
        reopen()
        assertEquals(s,db.detail("metrics"))
    }
}
