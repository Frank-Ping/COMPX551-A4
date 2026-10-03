package com.example.polarh10activityviewer.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.polarh10activityviewer.ble.ConnectionDevice
import com.example.polarh10activityviewer.ble.checkedDataTypes
import com.example.polarh10activityviewer.session.HrHistoryPoint
import com.example.polarh10activityviewer.session.MotionHistoryPoint
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SessionSummary
import com.example.polarh10activityviewer.session.StreamObservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal const val HISTORY_PAGE_SIZE = 10

internal class SessionDatabase(context: Context, name: String = NAME) :
    SQLiteOpenHelper(context.applicationContext, name, null, 1) {
    override fun onConfigure(db: SQLiteDatabase) {
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        val observations = checkedDataTypes.joinToString(",") { type ->
            "${type}_received INTEGER NOT NULL, ${type}_missing INTEGER NOT NULL, ${type}_failed INTEGER NOT NULL"
        }
        db.execSQL("""CREATE TABLE sessions (
            id TEXT PRIMARY KEY NOT NULL, startRequestedAt INTEGER NOT NULL,
            startedAt INTEGER NOT NULL, endedAt INTEGER NOT NULL, durationMs INTEGER NOT NULL,
            deviceName TEXT, deviceId TEXT, endReason TEXT, interrupted INTEGER NOT NULL,
            incomplete INTEGER NOT NULL, receivedValidHr INTEGER NOT NULL,
            minimumHr INTEGER, maximumHr INTEGER, meanHr REAL, validHrCount INTEGER NOT NULL,
            zone0Ms INTEGER NOT NULL, zone1Ms INTEGER NOT NULL, zone2Ms INTEGER NOT NULL,
            zone3Ms INTEGER NOT NULL, zone4Ms INTEGER NOT NULL, unclassifiedMs INTEGER NOT NULL,
            totalSteps INTEGER, meanCadence REAL, maximumCadence REAL, minimumCadence REAL,
            distanceMetres REAL, meanSpeedMetresPerSecond REAL, maximumSpeedMetresPerSecond REAL,
            $observations)""")
        db.execSQL("CREATE INDEX sessions_started ON sessions(startedAt DESC, id DESC)")
        db.execSQL("""CREATE TABLE hr_points (
            sessionId TEXT NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
            secondBucket INTEGER NOT NULL, elapsedMs INTEGER NOT NULL, bpm INTEGER,
            breakBefore INTEGER NOT NULL, PRIMARY KEY(sessionId, secondBucket))""")
        db.execSQL("""CREATE TABLE motion_points (
            sessionId TEXT NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
            secondBucket INTEGER NOT NULL, elapsedMs INTEGER NOT NULL, cadence REAL,
            speedMetresPerSecond REAL, breakBefore INTEGER NOT NULL,
            PRIMARY KEY(sessionId, secondBucket))""")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        error("No database migration is defined from $oldVersion to $newVersion.")
    }

    suspend fun save(snapshot: SessionSnapshot) = withContext(Dispatchers.IO) {
        val record = snapshot.record
        require(record.eligibleForSaving && record.endedAt != null)
        val db = writableDatabase
        db.beginTransaction()
        try {
            // A committed UUID is already complete because all three tables share this transaction.
            val exists = db.rawQuery("SELECT id FROM sessions WHERE id = ?", arrayOf(record.id)).use { it.moveToFirst() }
            if (!exists) {
                db.insertOrThrow("sessions", null, record.values())
                snapshot.hrPoints.forEach { point ->
                    require(point.sessionId == record.id)
                    db.insertOrThrow("hr_points", null, ContentValues().apply {
                        put("sessionId", point.sessionId); put("secondBucket", point.secondBucket)
                        put("elapsedMs", point.elapsedMs); put("bpm", point.bpm); put("breakBefore", point.breakBefore)
                    })
                }
                snapshot.motionPoints.forEach { point ->
                    require(point.sessionId == record.id)
                    db.insertOrThrow("motion_points", null, ContentValues().apply {
                        put("sessionId", point.sessionId); put("secondBucket", point.secondBucket)
                        put("elapsedMs", point.elapsedMs); put("cadence", point.cadence)
                        put("speedMetresPerSecond", point.speedMetresPerSecond); put("breakBefore", point.breakBefore)
                    })
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    // Keyset pagination stays stable when a newer session is saved between page requests.
    suspend fun page(before: SessionRecord? = null): List<SessionRecord> = withContext(Dispatchers.IO) {
        val where = if (before == null) "" else "WHERE startedAt < ? OR (startedAt = ? AND id < ?)"
        val args = before?.let { arrayOf(it.startedAt.toString(), it.startedAt.toString(), it.id) }
        readableDatabase.rawQuery("SELECT * FROM sessions $where ORDER BY startedAt DESC, id DESC LIMIT $HISTORY_PAGE_SIZE", args).use { cursor ->
            buildList { while (cursor.moveToNext()) add(cursor.record()) }
        }
    }

    suspend fun detail(id: String): SessionSnapshot? = withContext(Dispatchers.IO) {
        val db = readableDatabase
        db.beginTransaction()
        try {
            val record = db.rawQuery("SELECT * FROM sessions WHERE id = ?", arrayOf(id)).use {
                if (it.moveToFirst()) it.record() else null
            }
            val result = record?.let {
                val hr = db.rawQuery("SELECT * FROM hr_points WHERE sessionId = ? ORDER BY elapsedMs", arrayOf(id)).use { c ->
                    buildList { while (c.moveToNext()) add(HrHistoryPoint(id, c.long("secondBucket"), c.long("elapsedMs"),
                        c.nullableLong("bpm")?.toInt(), c.bool("breakBefore"))) }
                }
                val motion = db.rawQuery("SELECT * FROM motion_points WHERE sessionId = ? ORDER BY elapsedMs", arrayOf(id)).use { c ->
                    buildList { while (c.moveToNext()) add(MotionHistoryPoint(id, c.long("secondBucket"), c.long("elapsedMs"),
                        c.number("cadence"), c.number("speedMetresPerSecond"), c.bool("breakBefore"))) }
                }
                SessionSnapshot(record, hr, motion)
            }
            db.setTransactionSuccessful()
            result
        } finally {
            db.endTransaction()
        }
    }

    suspend fun delete(id: String) = withContext(Dispatchers.IO) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            db.delete("hr_points", "sessionId = ?", arrayOf(id))
            db.delete("motion_points", "sessionId = ?", arrayOf(id))
            db.delete("sessions", "id = ?", arrayOf(id))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    private fun SessionRecord.values() = ContentValues().apply {
        put("id", id); put("startRequestedAt", startRequestedAt); put("startedAt", startedAt)
        put("endedAt", endedAt); put("durationMs", durationMs); put("deviceName", device?.name)
        put("deviceId", device?.deviceId); put("endReason", endReason); put("interrupted", interrupted)
        put("incomplete", incomplete); put("receivedValidHr", summary.receivedValidHr)
        with(summary) {
            put("minimumHr", minimumHr); put("maximumHr", maximumHr); put("meanHr", meanHr)
            put("validHrCount", validHrCount)
            zoneDurationsMs.forEachIndexed { index, ms -> put("zone${index}Ms", ms) }
            put("unclassifiedMs", unclassifiedMs); put("totalSteps", totalSteps)
            put("meanCadence", meanCadence); put("maximumCadence", maximumCadence); put("minimumCadence", minimumCadence)
            put("distanceMetres", distanceMetres); put("meanSpeedMetresPerSecond", meanSpeedMetresPerSecond)
            put("maximumSpeedMetresPerSecond", maximumSpeedMetresPerSecond)
        }
        streams.forEach { (type, observation) ->
            put("${type}_received", observation.received); put("${type}_missing", observation.missing)
            put("${type}_failed", observation.failed)
        }
    }

    private fun Cursor.record(): SessionRecord = SessionRecord(
        id = text("id")!!, startRequestedAt = long("startRequestedAt"), startedAt = long("startedAt"),
        endedAt = long("endedAt"), durationMs = long("durationMs"), endReason = text("endReason"),
        device = text("deviceId")?.let { ConnectionDevice(text("deviceName")!!, it) },
        interrupted = bool("interrupted"),
        summary = SessionSummary(minimumHr = nullableLong("minimumHr")?.toInt(), maximumHr = nullableLong("maximumHr")?.toInt(),
            meanHr = number("meanHr"), validHrCount = long("validHrCount"),
            zoneDurationsMs = List(5) { long("zone${it}Ms") }, unclassifiedMs = long("unclassifiedMs"),
            totalSteps = nullableLong("totalSteps"), meanCadence = number("meanCadence"), maximumCadence = number("maximumCadence"),
            minimumCadence = number("minimumCadence"), distanceMetres = number("distanceMetres"),
            meanSpeedMetresPerSecond = number("meanSpeedMetresPerSecond"), maximumSpeedMetresPerSecond = number("maximumSpeedMetresPerSecond")),
        streams = checkedDataTypes.associateWith { type ->
            StreamObservation(bool("${type}_received"), bool("${type}_missing"), bool("${type}_failed"))
        }
    )

    private fun Cursor.long(name: String) = getLong(getColumnIndexOrThrow(name))
    private fun Cursor.bool(name: String) = long(name) != 0L
    private fun Cursor.text(name: String): String? = getString(getColumnIndexOrThrow(name))
    private fun Cursor.nullableLong(name: String): Long? = getColumnIndexOrThrow(name).let { if (isNull(it)) null else getLong(it) }
    private fun Cursor.number(name: String): Double? = getColumnIndexOrThrow(name).let { if (isNull(it)) null else getDouble(it) }

    companion object { const val NAME = "sessions.db" }
}
