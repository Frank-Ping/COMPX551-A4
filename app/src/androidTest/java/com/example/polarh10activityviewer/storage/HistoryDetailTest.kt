package com.example.polarh10activityviewer.storage

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryPanel
import com.example.polarh10activityviewer.session.HrHistoryPoint
import com.example.polarh10activityviewer.session.MotionHistoryPoint
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SessionSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.TimeZone
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class HistoryDetailTest {
    @get:Rule val compose = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "history-detail-test-${UUID.randomUUID()}.db"
    private lateinit var db: SessionDatabase
    private val originalZone = TimeZone.getDefault()
    @Before fun open() { db = SessionDatabase(context, name) }
    @After fun close() { TimeZone.setDefault(originalZone); db.close(); context.deleteDatabase(name) }

    private fun rows() = compose.onAllNodes(SemanticsMatcher("History card") {
        it.config.getOrElse(SemanticsProperties.TestTag) { "" }.startsWith("history-row-")
    }).fetchSemanticsNodes().map { it.config[SemanticsProperties.TestTag] }
    private fun awaitText(text: String) {
        compose.waitUntil(10_000) { compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty() }
    }
    private fun awaitList(count: Int) {
        compose.waitUntil(10_000) { rows().size == count &&
            compose.onAllNodesWithText("Loading…").fetchSemanticsNodes().isEmpty() }
    }
    private fun click(text: String) { compose.onNodeWithText(text).performScrollTo().performClick() }
    private fun select(index: Int = 0) {
        compose.onNodeWithTag(rows()[index]).performScrollTo().performClick()
    }
    private fun save(vararg snapshots: SessionSnapshot) = runBlocking { snapshots.forEach { db.save(it) } }
    private fun sql(statement: String) = runBlocking { withContext(Dispatchers.IO) { db.writableDatabase.execSQL(statement) } }
    private fun assertText(text: String) { compose.onNodeWithText(text).performScrollTo().assertIsDisplayed() }
    private fun withBlockedDatabase(action: () -> Unit) {
        val acquired = CountDownLatch(1)
        val release = CountDownLatch(1)
        val executor = Executors.newSingleThreadExecutor()
        val blocker = executor.submit {
            db.writableDatabase.beginTransaction()
            try { acquired.countDown(); check(release.await(15, TimeUnit.SECONDS)) }
            finally { db.writableDatabase.endTransaction() }
        }
        try { assertTrue(acquired.await(5, TimeUnit.SECONDS)); action() }
        finally { release.countDown(); blocker.get(5, TimeUnit.SECONDS); executor.shutdownNow() }
    }

    @Test fun differentIdsRenderStoredSummaryAndBothHistorySeriesAfterReopen() {
        val one = databaseFixture("one", 1000)
        val two = databaseFixture("two", 2000).let { snapshot -> snapshot.copy(
            record = snapshot.record.copy(summary = snapshot.record.summary.copy(
                maximumHr = 200, meanHr = 110.25, totalSteps = 12, meanCadence = 288.0,
                minimumCadence = 0.0, maximumCadence = 150.0, distanceMetres = 8.25)),
            hrPoints = listOf(HrHistoryPoint("two", 0, 100, 80, true), HrHistoryPoint("two", 1, 1100, null, true),
                HrHistoryPoint("two", 2, 2200, 90, true)),
            motionPoints = listOf(MotionHistoryPoint("two", 0, 100, 0.0, 0.0, true),
                MotionHistoryPoint("two", 1, 1100, null, null, true), MotionHistoryPoint("two", 2, 2200, 96.0, 1.25, true))) }
        save(one, two); db.close(); db = SessionDatabase(context, name)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(2); select(); awaitText("Delete session")
        assertText("Session ID: two")
        assertText("HR min / max / mean: 80 / 200 / 110.25 bpm")
        assertText("Mean cadence: 288.00 steps/min")
        assertText("Estimated distance: 8.25 m")
        assertText("Max 90.0"); assertText("Max 96.0")
        assertText("Unclassified time: 00:01")
        assertText("Very light: 4.00% of Running time")
        assertText("Elapsed since Running (mm:ss). Gaps are not interpolated.")
        assertEquals(two, runBlocking { db.detail("two") })
        click("Back"); awaitList(2); select(1); awaitText("Delete session")
        assertText("Session ID: one")
        assertText("HR min / max / mean: 80 / 140 / 110.12 bpm")
        assertEquals(one, runBlocking { db.detail("one") })
    }

    @Test fun listUsesShortDateWhileDetailAndConfirmationRetainPreciseTimeAndCancelWritesNothing() {
        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Auckland"))
        val snapshot = databaseFixture("date", 1234)
        save(snapshot)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(1)
        compose.onNodeWithText("01 Jan 1970").assertIsDisplayed()
        compose.onNodeWithText("12:00").assertIsDisplayed()
        val date = "1970-01-01 12:00:01 +12:00"
        select(); awaitText("Delete session")
        assertText("Running started: $date")
        assertText("Ended: 1970-01-01 12:00:03 +12:00")
        click("Delete session")
        compose.onNodeWithText("Running started: $date\nIts summary and both history series will be permanently deleted.").assertIsDisplayed()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals(snapshot, runBlocking { db.detail("date") })
        assertText("Session ID: date")
    }

    @Test fun zeroDurationNoValidHrAndNullableStatisticsDoNotBecomeMeasuredZero() {
        val snapshot = databaseFixture("zero").let { it.copy(record = it.record.copy(durationMs = 0,
            endedAt = it.record.startedAt, summary = SessionSummary(totalSteps = 0, distanceMetres = 0.0)),
            hrPoints = emptyList(), motionPoints = listOf(MotionHistoryPoint("zero", 0, 0, 0.0, 0.0, true))) }
        save(snapshot)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(1); select(); awaitText("Delete session")
        assertText("HR min / max / mean: -- / -- / -- bpm")
        assertText("Total steps: 0")
        assertText("Mean cadence: -- steps/min")
        assertText("Min cadence: -- steps/min")
        assertText("Estimated distance: 0.00 m")
        assertText("Running duration: 0 ms")
        assertText("No valid HR data")
        assertEquals(5, compose.onAllNodesWithText(": -- of Running time", substring = true).fetchSemanticsNodes().size)
        assertText("No valid chart data in this window")
        assertEquals(snapshot, runBlocking { db.detail("zero") })
    }

    @Test fun failedDetailQueryRetriesOriginalIdAndReadsCurrentTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        val snapshot = databaseFixture("retry", 1234)
        save(snapshot)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(1)
        sql("ALTER TABLE hr_points RENAME TO unavailable_hr")
        select(); awaitText("Retry query")
        compose.onNodeWithText("Delete session").assertDoesNotExist()
        assertText("History query failed. Please retry.")
        sql("ALTER TABLE unavailable_hr RENAME TO hr_points")
        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Auckland"))
        click("Retry query"); awaitText("Delete session")
        assertText("Session ID: retry")
        assertText("Running started: 1970-01-01 12:00:01 +12:00")
        assertEquals(snapshot, runBlocking { db.detail("retry") })
    }

    @Test fun recordDeletedAfterListLoadShowsNotFoundAndBackReloadsEmptyList() {
        save(databaseFixture("missing"))
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(1)
        runBlocking { db.delete("missing") }
        select(); awaitText("Session not found")
        compose.onNodeWithText("Delete session").assertDoesNotExist()
        click("Back"); awaitText("No saved sessions")
    }

    @Test fun savedStateRestoresSelectedIdAndNewSaveRetainsThatDetail() {
        val one = databaseFixture("selected", 1000)
        save(one)
        val savedId = mutableStateOf<String?>(null)
        val restoration = StateRestorationTester(compose)
        restoration.setContent { MaterialTheme { HistoryPanel(db, savedId.value, {}) } }
        awaitList(1); select(); awaitText("Delete session")
        restoration.emulateSavedInstanceStateRestore(); awaitText("Delete session")
        assertText("Session ID: selected")
        val another = databaseFixture("new", 2000)
        save(another)
        compose.runOnIdle { savedId.value = "new" }
        compose.waitForIdle()
        assertText("Session ID: selected")
        compose.onNodeWithText("Session ID: new").assertDoesNotExist()
        click("Back"); awaitList(2)
        assertEquals(another.record, runBlocking { db.page().first() })
    }

    @Test fun deleteFailureRollsBackRetainsDetailAndRetryDeletesOnlySelectedId() {
        val selected = databaseFixture("selected", 2000)
        val other = databaseFixture("other", 1000)
        save(selected, other)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(2); select(); awaitText("Delete session")
        sql("CREATE TRIGGER fail_delete BEFORE DELETE ON motion_points BEGIN SELECT RAISE(ABORT, 'test deletion failure'); END")
        click("Delete session"); compose.onNodeWithText("Delete").performClick()
        awaitText("Delete failed. Please retry Delete session.")
        assertText("Session ID: selected")
        compose.onNodeWithText("Retry query").assertDoesNotExist()
        assertEquals(selected, runBlocking { db.detail("selected") })
        assertEquals(other, runBlocking { db.detail("other") })
        sql("DROP TRIGGER fail_delete")
        click("Delete session"); compose.onNodeWithText("Delete").performClick()
        awaitList(1)
        assertNull(runBlocking { db.detail("selected") })
        assertEquals(other, runBlocking { db.detail("other") })
    }

    @Test fun cancelledBlockedDetailCannotReplaceNewSelection() {
        save(databaseFixture("old", 2000), databaseFixture("current", 1000))
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(2)
        withBlockedDatabase {
            select(); awaitText("Loading…")
            click("Back")
        }
        awaitList(2); select(1); awaitText("Delete session")
        assertText("Session ID: current")
        compose.onNodeWithText("Session ID: old").assertDoesNotExist()
    }

    @Test fun blockedDeleteRejectsDuplicateDeleteCancelAndBackUntilCompletion() {
        save(databaseFixture("selected", 2000), databaseFixture("other", 1000))
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(2); select(); awaitText("Delete session")
        click("Delete session")
        withBlockedDatabase {
            compose.onNodeWithText("Delete").performClick()
            awaitText("Loading…")
            compose.onNodeWithText("Delete").performClick()
            compose.onNodeWithText("Cancel").performClick()
            Espresso.pressBack()
            compose.onNodeWithText("Delete this session?").assertIsDisplayed()
        }
        awaitList(1)
        assertNull(runBlocking { db.detail("selected") })
        assertNotNull(runBlocking { db.detail("other") })
    }
}
