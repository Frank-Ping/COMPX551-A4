package com.example.polarh10activityviewer.storage

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryPanel
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

class HistoryListTest {
    @get:Rule val compose = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "history-list-test-${UUID.randomUUID()}.db"
    private lateinit var db: SessionDatabase
    private val originalZone = TimeZone.getDefault()
    @Before fun open() { db = SessionDatabase(context, name) }
    @After fun close() { TimeZone.setDefault(originalZone); db.close(); context.deleteDatabase(name) }

    private fun seed(count: Int) = runBlocking {
        repeat(count) { index ->
            val snapshot = databaseFixture("session-${index.toString().padStart(2, '0')}", (index / 3).toLong())
            db.save(snapshot.copy(record = snapshot.record.copy(summary = snapshot.record.summary.copy(totalSteps = index.toLong()))))
        }
    }
    private fun rows() = compose.onAllNodesWithText("Estimated distance:", substring = true)
        .fetchSemanticsNodes().map { node -> node.config[SemanticsProperties.Text].joinToString { it.text } }
    private fun awaitRows(count: Int) {
        compose.waitUntil(10_000) { rows().size == count &&
            compose.onAllNodesWithText("Loading…").fetchSemanticsNodes().isEmpty() }
    }
    private fun click(text: String) { compose.onNodeWithText(text).performScrollTo().performClick() }
    private fun rename(unavailable: Boolean) = runBlocking {
        withContext(Dispatchers.IO) {
            db.writableDatabase.execSQL(if (unavailable) "ALTER TABLE sessions RENAME TO unavailable_sessions"
                else "ALTER TABLE unavailable_sessions RENAME TO sessions")
        }
    }

    @Test fun emptyDatabaseShowsEmptyStateWithoutLoadMore() {
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        compose.waitUntil(10_000) { compose.onAllNodesWithText("No saved sessions").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("No saved sessions").assertIsDisplayed()
        compose.onNodeWithText("Load more").assertDoesNotExist()
    }

    @Test fun reopenedDatabaseLoadsFortyFiveTiedTimeRowsInStablePages() {
        seed(45)
        db.close(); db = SessionDatabase(context, name)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitRows(20)
        assertEquals((44 downTo 25).toList(), rows().map { it.substringAfter("Steps: ").substringBefore(" ·").toInt() })
        click("Load more"); awaitRows(40)
        click("Load more"); awaitRows(45)
        assertEquals((44 downTo 0).toList(), rows().map { it.substringAfter("Steps: ").substringBefore(" ·").toInt() })
        compose.onNodeWithText("Load more").assertDoesNotExist()
    }

    @Test fun exactlyFortyRowsNeedsOneEmptyPageToHideLoadMore() {
        seed(40)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitRows(20); click("Load more"); awaitRows(40)
        click("Load more"); awaitRows(40)
        compose.onNodeWithText("Load more").assertDoesNotExist()
        assertEquals(40, rows().distinct().size)
    }

    @Test fun paginationSqliteFailureRetainsRowsAndRetriesSameCursor() {
        seed(45)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        awaitRows(20)
        val first = rows()
        rename(true)
        click("Load more")
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Retry query").fetchSemanticsNodes().isNotEmpty() }
        assertEquals(first, rows())
        compose.onNodeWithText("Load more").assertDoesNotExist()
        rename(false)
        click("Retry query"); awaitRows(40)
        assertEquals(first, rows().take(20))
        assertEquals(40, rows().distinct().size)
        click("Load more"); awaitRows(45)
    }

    @Test fun initialSqliteFailureRetryUsesCurrentTimezoneAndPreservesNullAndZero() {
        val zero = databaseFixture("zero", 0)
        val unknown = databaseFixture("unknown", 1000).let { snapshot ->
            snapshot.copy(record = snapshot.record.copy(summary = snapshot.record.summary.copy(totalSteps = null, distanceMetres = null)))
        }
        runBlocking { db.save(zero); db.save(unknown) }
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        rename(true)
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Retry query").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("No saved sessions").assertDoesNotExist()
        assertTrue(rows().isEmpty())
        rename(false)
        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Auckland"))
        click("Retry query"); awaitRows(2)
        assertTrue(rows().all { it.contains("+12:00") && it.contains("Incomplete") })
        assertTrue(rows().any { it.contains("Steps: -- · Estimated distance: -- m") })
        assertTrue(rows().any { it.contains("Steps: 0 · Estimated distance: 0.00 m") })
    }

    @Test fun committedSaveRefreshesFirstPageButDoesNotReloadVisibleDetail() {
        seed(45)
        val savedId = mutableStateOf<String?>(null)
        compose.setContent { MaterialTheme { HistoryPanel(db, savedId.value, {}) } }
        awaitRows(20); click("Load more"); awaitRows(40)
        val newest = databaseFixture("newest", 9999)
        runBlocking { db.save(newest) }
        compose.runOnIdle { savedId.value = newest.record.id }
        awaitRows(20)
        assertTrue(rows().first().contains("Steps: 0 ·"))
        compose.onNodeWithText(rows().first()).performScrollTo().performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Delete session").fetchSemanticsNodes().isNotEmpty() }
        // If a save incorrectly restarts the detail query, the missing table exposes it.
        val another = databaseFixture("another", 10000)
        runBlocking { db.save(another) }
        rename(true)
        compose.runOnIdle { savedId.value = another.record.id }
        compose.waitForIdle()
        compose.onNodeWithText("Delete session").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Retry query").assertDoesNotExist()
        rename(false)
        click("Back"); awaitRows(20)
    }

    @Test fun detailReturnReentryAndStateRestorationReloadOnlyFirstPage() {
        seed(45)
        val visible = mutableStateOf(true)
        val restoration = StateRestorationTester(compose)
        restoration.setContent { MaterialTheme {
            if (visible.value) HistoryPanel(db, null, { visible.value = false })
            else Button(onClick = { visible.value = true }) { Text("Open History") }
        } }
        awaitRows(20); click("Load more"); awaitRows(40)
        compose.onNodeWithText(rows().first()).performScrollTo().performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Delete session").fetchSemanticsNodes().isNotEmpty() }
        click("Back"); awaitRows(20)
        click("Load more"); awaitRows(40)
        restoration.emulateSavedInstanceStateRestore(); awaitRows(20)
        click("Load more"); awaitRows(40)
        click("Back"); compose.onNodeWithText("Open History").performClick(); awaitRows(20)
    }

    @Test fun leavingDuringBlockedPageQueryRejectsLateRowsAndDuplicateLoads() {
        seed(45)
        val visible = mutableStateOf(true)
        compose.setContent { MaterialTheme {
            if (visible.value) HistoryPanel(db, null, { visible.value = false })
            else Button(onClick = { visible.value = true }) { Text("Open History") }
        } }
        awaitRows(20)
        val acquired = CountDownLatch(1)
        val release = CountDownLatch(1)
        val executor = Executors.newSingleThreadExecutor()
        val blocker = executor.submit {
            db.writableDatabase.beginTransaction()
            try { acquired.countDown(); check(release.await(15, TimeUnit.SECONDS)) }
            finally { db.writableDatabase.endTransaction() }
        }
        try {
            assertTrue(acquired.await(5, TimeUnit.SECONDS))
            click("Load more")
            compose.waitUntil(5000) { compose.onAllNodesWithText("Loading…").fetchSemanticsNodes().isNotEmpty() }
            click("Load more") // Disabled: must not enqueue another page.
            click("Back"); compose.onNodeWithText("Open History").performClick()
            release.countDown(); blocker.get(5, TimeUnit.SECONDS)
            awaitRows(20)
            assertEquals(20, rows().distinct().size)
            click("Load more"); awaitRows(40)
            assertEquals(40, rows().distinct().size)
        } finally { release.countDown(); blocker.get(5, TimeUnit.SECONDS); executor.shutdownNow() }
    }
}
