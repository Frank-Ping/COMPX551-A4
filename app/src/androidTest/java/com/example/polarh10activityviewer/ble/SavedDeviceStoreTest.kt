package com.example.polarh10activityviewer.ble

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import com.example.polarh10activityviewer.storage.SessionDatabase
import com.example.polarh10activityviewer.storage.databaseFixture
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.*
import org.junit.Test
import java.util.UUID

class SavedDeviceStoreTest {
    private val base = InstrumentationRegistry.getInstrumentation().targetContext
    private fun isolatedContext(preferenceName: String) = object : ContextWrapper(base) {
        override fun getApplicationContext(): Context = this
        override fun getSharedPreferences(name: String, mode: Int): SharedPreferences =
            base.getSharedPreferences(preferenceName, mode)
    }

    @Test fun clearPersistsWithoutDeletingOtherPreferencesOrActivityHistoryAndAllowsSavingAgain() = runBlocking {
        val name = "saved-devices-test-${UUID.randomUUID()}"
        val context = isolatedContext(name)
        val prefs = context.getSharedPreferences("saved_devices", Context.MODE_PRIVATE)
        val db = SessionDatabase(base, "$name.db")
        try {
            prefs.edit().putString("unrelated", "keep").commit()
            db.save(databaseFixture("keep-activity"))
            val store = SavedDeviceStore(context)
            withTimeout(5000) { store.state.first { !it.loading } }
            store.save(SavedDevice("Polar H10 A", "A", 1000))
            withTimeout(5000) { store.state.first { it.devices.size == 1 } }
            store.save(SavedDevice("Polar H10 B", "B", 2000))
            withTimeout(5000) { store.state.first { it.devices.size == 2 } }
            store.clear()
            val cleared = withTimeout(5000) { store.state.first { it.devices.isEmpty() && !it.loading } }
            assertNull(cleared.error)
            assertFalse(prefs.contains("devices"))
            assertEquals("keep", prefs.getString("unrelated", null))
            assertEquals(1, db.page().size)
            val reopened = SavedDeviceStore(context)
            assertTrue(withTimeout(5000) { reopened.state.first { !it.loading } }.devices.isEmpty())
            reopened.save(SavedDevice("Polar H10 A", "A", 3000))
            assertEquals("A", withTimeout(5000) { reopened.state.first { it.devices.size == 1 } }.devices.single().deviceId)
        } finally {
            db.close()
            base.deleteDatabase("$name.db")
            base.deleteSharedPreferences(name)
        }
    }

    @Test fun clearCanRemoveUnreadableSavedDeviceData() = runBlocking {
        val name = "saved-devices-test-${UUID.randomUUID()}"
        val context = isolatedContext(name)
        try {
            context.getSharedPreferences("saved_devices", Context.MODE_PRIVATE).edit().putString("devices", "invalid json").commit()
            val store = SavedDeviceStore(context)
            assertNotNull(withTimeout(5000) { store.state.first { !it.loading } }.error)
            store.clear()
            val result = withTimeout(5000) { store.state.first { it.error == null && !it.loading } }
            assertTrue(result.devices.isEmpty())
            assertFalse(context.getSharedPreferences("saved_devices", Context.MODE_PRIVATE).contains("devices"))
        } finally { base.deleteSharedPreferences(name) }
    }
}
