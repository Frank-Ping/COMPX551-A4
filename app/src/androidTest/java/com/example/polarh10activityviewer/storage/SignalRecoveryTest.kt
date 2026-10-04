package com.example.polarh10activityviewer.storage

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Assume.assumeTrue
import org.junit.Test

// Explicitly invoked on an isolated emulator; this fixture never ships in the app.
class SignalRecoveryTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test fun seedUnfinishedRecordingForExternalProcessKill() = runBlocking<Unit> {
        assumeTrue(InstrumentationRegistry.getArguments().getString("recoveryPhase") == "seed")
        val db = SessionDatabase(context)
        try {
            db.delete("step90a-interrupted-demo")
            val buffer = SignalBuffer()
            for (second in 0..19) {
                buffer.receiveEcg(List(130) { i ->
                    val spike = if (i in 40..43) (900 - kotlin.math.abs(i-41)*400) else (30*kotlin.math.sin(i/8.0)).toInt()
                    RawEcg(1_000_000_000L + second*1_000_000_000L + i*1_000_000_000L/130, spike)
                }, (second+1)*1000L, 130)
            }
            buffer.receiveRr(List(180) { 780 + (it % 9)*10 }, 20_000, 21_000, 21_000)
            val fixture = databaseFixture("step90a-interrupted-demo", 1_791_073_800_000L).let { original ->
                original.copy(record = original.record.copy(durationMs = 20_000,
                    endedAt = original.record.startedAt!! + 20_000, receivedRr = true))
            }
            db.writeSignals(listOf(buffer.take(fixture.record, fixture.hrPoints, fixture.motionPoints, true)))
            assertNull(db.detail(fixture.record.id))
            assertEquals(180L, db.rrCount(fixture.record.id))
        } finally { db.close() }
    }

    @Test fun verifyApplicationArchivedAfterExternalRelaunch() = runBlocking<Unit> {
        assumeTrue(InstrumentationRegistry.getArguments().getString("recoveryPhase") == "verify")
        val db = SessionDatabase(context)
        try {
            val record = db.detail("step90a-interrupted-demo")!!.record
            assertTrue(record.collectionIncomplete)
            assertEquals(20_000L, record.durationMs)
            assertEquals(180L, db.rrCount(record.id))
            assertTrue(db.ecgWindow(record.id, 0, 5000).size in 649..652)
            assertEquals(1, db.page().count { it.id == record.id })
        } finally { db.close() }
    }
}
