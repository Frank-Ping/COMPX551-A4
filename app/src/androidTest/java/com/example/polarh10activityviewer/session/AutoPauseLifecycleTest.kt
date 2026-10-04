package com.example.polarh10activityviewer.session

import android.annotation.SuppressLint
import android.content.Intent
import android.os.SystemClock
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.ActivityViewerApplication
import com.example.polarh10activityviewer.MainActivity
import com.example.polarh10activityviewer.SensorActivity
import com.example.polarh10activityviewer.ble.ConnectionDevice
import com.example.polarh10activityviewer.ble.DataSubscriptions
import com.example.polarh10activityviewer.storage.SaveStatus
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.ACC
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

// Controlled subscription only: exercise real Activity callbacks without an H10 or real sensor readings.
class AutoPauseLifecycleTest {
    @get:Rule val compose = createEmptyComposeRule()
    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    private val context = instrumentation.targetContext
    private val manager get() = (context.applicationContext as ActivityViewerApplication).bleManager
    private val controller get() = field<SessionController>("session")
    private val subscriptions get() = field<DataSubscriptions>("dataSubscriptions")
    private var scenario: ActivityScenario<SensorActivity>? = null
    private var sessionId: String? = null

    @Suppress("UNCHECKED_CAST")
    private fun <T> field(name: String): T = manager.javaClass.getDeclaredField(name).apply {
        isAccessible = true
    }.get(manager) as T

    private fun launch() {
        scenario = ActivityScenario.launch(SensorActivity::class.java)
    }

    private fun startControlled(waiting: Boolean = false): SessionRecord {
        lateinit var record: SessionRecord
        instrumentation.runOnMainSync {
            assertTrue(controller.start(true, ConnectionDevice("Controlled lifecycle fixture", "LIFECYCLE")) {
                val generation = controller.state.value.generation
                subscriptions.start(ACC, { true }, { controller.accepts(generation) }, {
                    flow { if (!waiting) emit(Unit); awaitCancellation() }
                }, { controller.onValidData() })
            })
            record = controller.state.value.record!!
            sessionId = record.id
        }
        return record
    }

    private fun awaitPaused() = compose.waitUntil(5000) {
        manager.sessionState.value.status == SessionStatus.PAUSED
    }

    private fun assertPreserved(record: SessionRecord) {
        awaitPaused()
        val paused = manager.sessionState.value
        assertEquals(record.id, paused.record!!.id)
        assertEquals(record.startedAt, paused.record.startedAt)
        assertNull(paused.record.endedAt)
        assertNotEquals(record.id, manager.storage.saves.state.value.sessionId)
        instrumentation.runOnMainSync {
            assertFalse(subscriptions.isActive(ACC))
            assertFalse(controller.accepts(paused.generation))
        }
        val elapsed = paused.elapsedMs
        SystemClock.sleep(100)
        instrumentation.runOnMainSync { manager.refreshSessionTime(paused.generation) }
        assertEquals(elapsed, manager.sessionState.value.elapsedMs)
    }

    @After fun cleanup() {
        shell("input keyevent KEYCODE_WAKEUP")
        shell("wm dismiss-keyguard")
        scenario?.close()
        instrumentation.runOnMainSync { manager.stopSession() }
        compose.waitUntil(5000) { manager.sessionState.value.status == SessionStatus.IDLE &&
            manager.storage.saves.state.value.status != SaveStatus.SAVING }
        sessionId?.let { runBlocking { manager.storage.database.delete(it) } }
    }

    private fun shell(command: String) {
        instrumentation.uiAutomation.executeShellCommand(command).use { descriptor ->
            android.os.ParcelFileDescriptor.AutoCloseInputStream(descriptor).readBytes()
        }
    }

    @Test fun pauseAndStopCallbacksKeepSameSessionWithoutAutomaticResume() {
        launch()
        val record = startControlled()
        scenario!!.moveToState(Lifecycle.State.STARTED)
        assertPreserved(record)
        scenario!!.moveToState(Lifecycle.State.CREATED)
        scenario!!.moveToState(Lifecycle.State.RESUMED)
        assertPreserved(record)
        compose.onNodeWithContentDescription("Continue").assertIsDisplayed().assertIsNotEnabled()
    }

    @Test fun historyNavigationPausesAndReturningDoesNotResume() {
        launch()
        val record = startControlled()
        compose.onNodeWithText("History").performClick()
        assertPreserved(record)
        compose.onNodeWithText("Session").performClick()
        assertPreserved(record)
        compose.onNodeWithContentDescription("Continue").assertIsDisplayed()
    }

    @Test fun destroyedSensorActivityAndWelcomeNavigationRetainApplicationSession() {
        launch()
        val record = startControlled()
        val owner = manager
        scenario!!.close()
        assertPreserved(record)
        ActivityScenario.launch<MainActivity>(Intent(context, MainActivity::class.java)).use { welcome ->
            welcome.onActivity { assertSame(owner, (it.application as ActivityViewerApplication).bleManager) }
        }
        launch()
        assertSame(owner, manager)
        assertPreserved(record)
    }

    @Test fun recreationDuringStartingKeepsPausedAttemptWithoutSaving() {
        launch()
        val record = startControlled(waiting = true)
        assertNull(record.startedAt)
        scenario!!.recreate()
        assertPreserved(record)
        assertEquals(0L, manager.sessionState.value.elapsedMs)
    }

    @Test fun anotherActivityPausesAndExplicitStopSavesOriginalSession() {
        launch()
        val record = startControlled()
        val monitor = instrumentation.addMonitor(MainActivity::class.java.name, null, false)
        try {
            scenario!!.onActivity { it.startActivity(Intent(it, MainActivity::class.java)) }
            val welcome = checkNotNull(instrumentation.waitForMonitorWithTimeout(monitor, 5000))
            assertPreserved(record)
            instrumentation.runOnMainSync { welcome.finish() }
        } finally {
            instrumentation.removeMonitor(monitor)
        }
        compose.waitUntil(5000) { scenario!!.state == Lifecycle.State.RESUMED }
        assertPreserved(record)
        compose.onNodeWithContentDescription("Stop").performClick()
        compose.waitUntil(5000) { manager.storage.saves.state.value.status == SaveStatus.SAVED }
        assertEquals(record.id, manager.storage.saves.state.value.sessionId)
        assertEquals(SessionStatus.IDLE, manager.sessionState.value.status)
        assertEquals(record.id, manager.lastSnapshot.value!!.record.id)
        assertFalse(manager.lastSnapshot.value!!.record.interrupted)
        val saved = runBlocking { manager.storage.database.detail(record.id) }!!
        assertEquals(2, saved.record.summary.activityMetrics.algorithmVersion)
        assertEquals(0, saved.record.summary.activityMetrics.cadencePointCount)
        assertNull(saved.record.summary.activityMetrics.intensity)
        assertNull(saved.record.summary.activityMetrics.sessionStrain)
        assertEquals(manager.lastSnapshot.value, saved.copy(record = saved.record.copy(
            summary = saved.record.summary.copy(activityMetrics = ActivityMetrics())
        )))
    }

    @SuppressLint("MissingPermission")
    @Test fun pausedBluetoothCleanupAndWrongDeviceCannotFinalizeOrReplaceSession() {
        launch()
        val record = startControlled()
        instrumentation.runOnMainSync {
            manager.pauseForNavigation()
            manager.bluetoothUnavailable()
            manager.releaseBluetooth()
        }
        assertPreserved(record)
        instrumentation.runOnMainSync {
            // Rejected before any SDK or permission access.
            manager.connect("OTHER")
            assertTrue(manager.connectionState.value.error!!.contains("original H10"))
            assertFalse(manager.resumeSession())
        }
        assertPreserved(record)
    }

    @Test fun homeAndScreenOffPauseTheRealActivity() {
        launch()
        val record = startControlled()
        shell("input keyevent KEYCODE_HOME")
        assertPreserved(record)
        returnToSession()
        assertPreserved(record)
        instrumentation.runOnMainSync { manager.stopSession() }
        compose.waitUntil(5000) { !manager.storage.saves.state.value.blocksStart }
        runBlocking { manager.storage.database.delete(record.id) }
        val second = startControlled()
        shell("input keyevent KEYCODE_SLEEP")
        assertPreserved(second)
        shell("input keyevent KEYCODE_WAKEUP")
        shell("wm dismiss-keyguard")
        returnToSession()
        assertPreserved(second)
    }

    private fun returnToSession() {
        context.startActivity(Intent(context, SensorActivity::class.java).addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        compose.waitUntil(5000) { scenario!!.state == Lifecycle.State.RESUMED }
    }
}
