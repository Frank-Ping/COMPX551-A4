package com.example.polarh10activityviewer.storage

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryPanel
import com.example.polarh10activityviewer.R
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.xmlpull.v1.XmlPullParser
import java.util.UUID

class HistoryPanelTest {
    @get:Rule val compose = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "history-ui-test-${UUID.randomUUID()}.db"
    private lateinit var db: SessionDatabase
    @Before fun setup() { db = SessionDatabase(context, name) }
    @After fun cleanup() { db.close(); context.deleteDatabase(name) }

    @Test fun databaseListDetailCancelAndConfirmedDeleteUseRealRows() {
        val snapshot = databaseFixture()
        runBlocking { db.save(snapshot) }
        compose.setContent { MaterialTheme { HistoryPanel(db, null, {}) } }
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Estimated distance:", substring = true).fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Estimated distance:", substring = true).performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Delete session").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Delete session").performScrollTo().performClick()
        compose.onNodeWithText("Cancel").performClick()
        assertEquals(snapshot, runBlocking { db.detail(snapshot.record.id) })
        compose.onNodeWithText("Delete session").performScrollTo().performClick()
        compose.onNodeWithText("Delete", substring = false).performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("No saved sessions").fetchSemanticsNodes().isNotEmpty() }
        assertNull(runBlocking { db.detail(snapshot.record.id) })
        compose.onNodeWithText("No saved sessions").assertIsDisplayed()
    }

    @Test fun manifestLinkedBackupRulesExcludeOnlySessionDatabaseAndSidecars() {
        for (resource in listOf(R.xml.backup_rules, R.xml.data_extraction_rules)) {
            val exclusions = mutableMapOf<String, MutableSet<String>>()
            var section = "legacy"
            context.resources.getXml(resource).use { xml ->
                while (xml.eventType != XmlPullParser.END_DOCUMENT) {
                    if (xml.eventType == XmlPullParser.START_TAG) {
                        if (xml.name in listOf("cloud-backup", "device-transfer")) section = xml.name
                        if (xml.name == "exclude") {
                            assertEquals("database", xml.getAttributeValue(null, "domain"))
                            exclusions.getOrPut(section) { mutableSetOf() }.add(xml.getAttributeValue(null, "path"))
                        }
                        assertNotEquals("include", xml.name)
                    }
                    xml.next()
                }
            }
            val expectedSections = if (resource == R.xml.backup_rules) setOf("legacy") else setOf("cloud-backup", "device-transfer")
            assertEquals(expectedSections, exclusions.keys)
            exclusions.values.forEach { assertEquals(setOf("sessions.db", "sessions.db-journal", "sessions.db-wal", "sessions.db-shm"), it) }
        }
    }
}
