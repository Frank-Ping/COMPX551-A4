package com.example.polarh10activityviewer.storage

import android.graphics.Bitmap
import android.os.SystemClock
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.history.HistoryPanel
import com.example.polarh10activityviewer.session.withActivityMetrics
import com.example.polarh10activityviewer.session.StreamObservation
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.util.UUID

class ActivityMetricsUiTest {
    @get:Rule val compose = createComposeRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "metrics-ui-${UUID.randomUUID()}.db"
    private lateinit var db: SessionDatabase
    @Before fun open() { db = SessionDatabase(context,name) }
    @After fun close() { db.close(); context.deleteDatabase(name) }
    private fun await(text: String) { compose.waitUntil(10000) {
        compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
    } }
    private fun mount(archived: Boolean = false, missingAcc: Boolean = false, storedScore: Double? = null) {
        val s = metricsFixture().let { it.copy(record=it.record.copy(collectionIncomplete=archived, streams=it.record.streams + (PolarDeviceDataType.ACC to StreamObservation(true,missing=missingAcc)))) }.withActivityMetrics()
        runBlocking { db.save(s) }
        if (storedScore != null) db.writableDatabase.execSQL(
            "UPDATE sessions SET sessionStrainScore=? WHERE id='metrics'", arrayOf(storedScore))
        compose.setContent { PolarH10ActivityViewerTheme {
            Surface(Modifier.fillMaxSize()) {
                Box(Modifier.safeDrawingPadding()) { HistoryPanel(db,null,{}) }
            }
        } }
        compose.waitUntil(10000) { compose.onAllNodes(hasTestTag("history-row-metrics")).fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithTag("history-row-metrics").performClick()
        await("Cadence Stability")
    }
    private fun capture(name: String) {
        compose.waitForIdle()
        // System dialog transitions are outside Compose's test clock.
        SystemClock.sleep(400)
        val font = context.resources.configuration.fontScale
        val night = context.resources.configuration.uiMode and 0x30
        File(context.getExternalFilesDir(null),"stored-score-$name-$font-$night.png").outputStream().use {
            checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot()).compress(Bitmap.CompressFormat.PNG,100,it)
        }
    }
    @Test fun metricsVisibleWithoutDetailClickActions() {
        mount()
        for (text in listOf("2.8 / 5","85.0 AU","CV 7.4%","73.6 / 100")) compose.onNodeWithText(text).performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("HR Recovery").assertDoesNotExist()
        capture("summary")
        for (title in listOf("Intensity", "Cardio Load", "Cadence Stability", "Session Strain")) {
            compose.onNodeWithText(title).performScrollTo().assertHasNoClickAction()
            compose.onAllNodes(hasClickAction() and hasAnyDescendant(hasText(title))).assertCountEquals(0)
        }
        compose.onNodeWithText("Delete session").performScrollTo().assertIsDisplayed()
    }
    @Test fun strainRemainsReadOnlyWithoutDetailsOrInput() {
        mount()
        val before = runBlocking { db.detail("metrics") }
        compose.onNodeWithText("Session Strain").performScrollTo().assertHasNoClickAction()
        compose.onNodeWithText("Session Strain").performTouchInput { click() }
        compose.onNodeWithText("Automatically estimated",substring=true).assertDoesNotExist()
        compose.onNodeWithText("Tap to rate").assertDoesNotExist()
        compose.onNodeWithText("Save rating").assertDoesNotExist()
        assertEquals(278.5,before!!.record.summary.activityMetrics.sessionStrain!!,1e-10)
        compose.onNodeWithText("Close").assertDoesNotExist()
        assertEquals(before,runBlocking { db.detail("metrics") })
        compose.onNodeWithText("73.6 / 100").performScrollTo().assertIsDisplayed()
    }
    @Test fun incompleteAccHidesOnlyStrainWhileOtherMetricsRemainAvailable() {
        mount(missingAcc=true)
        compose.onAllNodesWithText("--").assertCountEquals(1)
        compose.onNodeWithText("85.0 AU").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("CV 7.4%").performScrollTo().assertIsDisplayed()
        assertNull(runBlocking { db.detail("metrics") }!!.record.summary.activityMetrics.sessionStrain)
    }
    @Test fun historyDisplaysStoredScoreWithoutRecalculatingFromRawLoad() {
        mount(storedScore=12.5)
        compose.onNodeWithText("12.5 / 100").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("73.6 / 100").assertDoesNotExist()
        val metrics=runBlocking { db.detail("metrics") }!!.record.summary.activityMetrics
        assertEquals(278.5,metrics.sessionStrain!!,1e-10)
        assertEquals(12.5,metrics.sessionStrainScore!!,0.0)
    }
    @Test fun interruptedActivityKeepsMissingMetricsAndHasNoRatingInput() {
        mount(archived=true)
        compose.onAllNodesWithText("--").assertCountEquals(4)
        compose.onNodeWithText("Session Strain").performScrollTo().assertHasNoClickAction()
        compose.onNodeWithText("Save rating").assertDoesNotExist()
        compose.onNodeWithText("Close").assertDoesNotExist()
        compose.onNodeWithText("Data collection incomplete").performScrollTo().assertIsDisplayed()
    }
}
