package com.example.polarh10activityviewer.storage

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
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
    private fun click(text: String) {
        when (text) {
            "Back" -> compose.onNodeWithContentDescription("Back").performClick()
            "Close" -> compose.onNodeWithText(text).performClick()
            else -> compose.onNodeWithText(text).performScrollTo().performClick()
        }
    }
    private fun select(index: Int = 0) {
        compose.onNodeWithTag(rows()[index]).performScrollTo().performClick()
    }
    private fun save(vararg snapshots: SessionSnapshot) = runBlocking { snapshots.forEach { db.save(it) } }
    private fun sql(statement: String) = runBlocking { withContext(Dispatchers.IO) { db.writableDatabase.execSQL(statement) } }
    private fun assertText(text: String) { compose.onNodeWithText(text).performScrollTo().assertIsDisplayed() }
    private fun assertDetail(id: String) { compose.onNodeWithTag("history-detail-$id").assertExists() }
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
        assertDetail("two")
        assertText("80–200")
        assertText("288")
        compose.onNodeWithText("Estimated Distance").assertDoesNotExist()
        compose.onNodeWithContentDescription("Heart rate, whole session. Dashed line: saved mean 110.25 bpm. Gaps are not interpolated.").assertExists()
        compose.onNode(hasText("Cadence") and hasClickAction()).performScrollTo().performClick()
        compose.onNodeWithContentDescription("Cadence, whole session. Dashed line: saved mean 288.0 steps/min. Gaps are not interpolated.").assertExists()
        assertText("4%")
        assertEquals(two, runBlocking { db.detail("two") })
        click("Back"); awaitList(2); select(1); awaitText("Delete session")
        assertDetail("one")
        assertText("80–140")
        assertEquals(one, runBlocking { db.detail("one") })
    }

    @Test fun listAndDetailUseShortDateWhileConfirmationRetainsPreciseTimeAndCancelWritesNothing() {
        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Auckland"))
        val snapshot = databaseFixture("date", 1234)
        save(snapshot)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(1)
        compose.onNodeWithText("01 Jan 1970").assertIsDisplayed()
        compose.onNodeWithText("12:00").assertIsDisplayed()
        val date = "1970-01-01 12:00:01 +12:00"
        select(); awaitText("Delete session")
        assertText("01 Jan 1970")
        compose.onNodeWithText("12:00").assertHasNoClickAction()
        compose.onNodeWithContentDescription("Session details").assertDoesNotExist()
        click("Delete session")
        compose.onNodeWithText("Running started: $date\nIts summary and all recorded chart data will be permanently deleted.").assertIsDisplayed()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals(snapshot, runBlocking { db.detail("date") })
        assertDetail("date")
    }

    @Test fun zeroDurationNoValidHrAndNullableStatisticsDoNotBecomeMeasuredZero() {
        val snapshot = databaseFixture("zero").let { it.copy(record = it.record.copy(durationMs = 0,
            endedAt = it.record.startedAt, summary = SessionSummary(totalSteps = 0, distanceMetres = 0.0)),
            hrPoints = emptyList(), motionPoints = listOf(MotionHistoryPoint("zero", 0, 0, 0.0, 0.0, true))) }
        save(snapshot)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(1); select(); awaitText("Delete session")
        assertText("--–--")
        assertText("0")
        assertText("No valid heart rate data")
        assertText("No recorded heart rate data")
        compose.onNodeWithContentDescription("Heart rate line chart: no valid data").assertExists()
        compose.onNodeWithText("ECG").assertIsEnabled()
        compose.onNodeWithText("RR").assertDoesNotExist()
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
        assertDetail("retry")
        assertText("01 Jan 1970")
        assertText("12:00")
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
        assertDetail("selected")
        val another = databaseFixture("new", 2000)
        save(another)
        compose.runOnIdle { savedId.value = "new" }
        compose.waitForIdle()
        assertDetail("selected")
        compose.onNodeWithTag("history-detail-new").assertDoesNotExist()
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
        assertDetail("selected")
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
            select(); compose.waitForIdle()
            compose.onNodeWithText("Loading…").assertDoesNotExist()
            click("Back")
        }
        awaitList(2); select(1); awaitText("Delete session")
        assertDetail("current")
        compose.onNodeWithTag("history-detail-old").assertDoesNotExist()
    }

    @Test fun blockedDeleteRejectsDuplicateDeleteCancelAndBackUntilCompletion() {
        save(databaseFixture("selected", 2000), databaseFixture("other", 1000))
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitList(2); select(); awaitText("Delete session")
        click("Delete session")
        withBlockedDatabase {
            compose.onNodeWithText("Delete").performClick()
            compose.onNodeWithText("Delete").assertIsNotEnabled()
            compose.onNodeWithText("Cancel").assertIsNotEnabled()
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
