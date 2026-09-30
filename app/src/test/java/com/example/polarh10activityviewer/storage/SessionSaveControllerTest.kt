package com.example.polarh10activityviewer.storage

import com.example.polarh10activityviewer.ble.DataSubscriptions
import com.example.polarh10activityviewer.session.SessionController
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SessionSummary
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionSaveControllerTest {
    private fun snapshot(id: String = "one") = SessionSnapshot(
        SessionRecord(id, 10, null, startedAt = 20, endedAt = 20,
            summary = SessionSummary(minimumHr = 120, maximumHr = 120, meanHr = 120.0, validHrCount = 1)), emptyList(), emptyList())

    @Test fun savedOnlyAfterCommitAndRepeatedSubmissionsCannotStartAnotherWrite() = runTest {
        val committed = CompletableDeferred<Unit>()
        var writes = 0
        val owner = SessionSaveController(this) { writes++; committed.await() }
        assertTrue(owner.submit(snapshot()))
        assertEquals(SaveStatus.SAVING, owner.state.value.status)
        assertTrue(owner.state.value.blocksStart)
        assertFalse(owner.submit(snapshot())); assertFalse(owner.submit(snapshot("two")))
        assertFalse(owner.retry()); assertFalse(owner.discard())
        runCurrent(); assertEquals(1, writes)
        committed.complete(Unit); runCurrent()
        assertEquals(SaveStatus.SAVED, owner.state.value.status)
        assertFalse(owner.state.value.blocksStart)
        assertFalse(owner.submit(snapshot()))
        assertEquals(1, writes)
    }

    @Test fun failureKeepsExactSnapshotAndRetryUsesSameIdWithoutAutomaticRetry() = runTest {
        var writes = 0
        val original = snapshot()
        val seen = mutableListOf<SessionSnapshot>()
        val owner = SessionSaveController(this) {
            seen.add(it); writes++
            if (writes == 1) error("Disk full")
        }
        owner.submit(original); runCurrent()
        assertEquals(SaveStatus.FAILED, owner.state.value.status)
        assertEquals("Disk full", owner.state.value.error)
        assertTrue(owner.state.value.blocksStart)
        assertFalse(owner.submit(snapshot("next")))
        runCurrent(); assertEquals(1, writes)
        // Returning to a page observes the same application-owned state.
        assertSame(owner.state.value, owner.state.first())
        assertTrue(owner.retry()); assertFalse(owner.retry()); runCurrent()
        assertEquals(SaveStatus.SAVED, owner.state.value.status)
        assertSame(original, seen[0]); assertSame(original, seen[1])
        assertEquals("one", owner.state.value.sessionId)
    }

    @Test fun explicitDiscardReleasesFailureAndAllowsNextSessionButNotRetry() = runTest {
        val owner = SessionSaveController(this) { error("Disk full") }
        owner.submit(snapshot()); runCurrent()
        assertTrue(owner.discard())
        assertEquals(SaveStatus.DISCARDED, owner.state.value.status)
        assertFalse(owner.state.value.blocksStart)
        assertFalse(owner.retry()); assertFalse(owner.discard())
        assertTrue(owner.submit(snapshot("two"))); runCurrent()
        assertEquals("two", owner.state.value.sessionId)
    }

    @Test fun noDataAndInvalidOnlyAreNotWrittenButZeroDurationValidSessionIsWritten() = runTest {
        var writes = 0
        val owner = SessionSaveController(this) { writes++ }
        val empty = snapshot().copy(record = SessionRecord("empty", 1, null, endedAt = 2))
        assertFalse(owner.submit(empty)); runCurrent()
        assertEquals(SaveStatus.INELIGIBLE, owner.state.value.status)
        assertFalse(owner.state.value.blocksStart)
        assertEquals(0, writes)
        assertTrue(owner.submit(snapshot())); runCurrent()
        assertEquals(1, writes)
    }

    @Test fun cancellingPageScopeDoesNotCancelApplicationWrite() = runTest {
        val uiScope = CoroutineScope(coroutineContext + Job())
        val commit = CompletableDeferred<Unit>()
        val owner = SessionSaveController(this) { commit.await() }
        owner.submit(snapshot()); runCurrent()
        uiScope.cancel()
        assertEquals(SaveStatus.SAVING, owner.state.value.status)
        commit.complete(Unit); runCurrent()
        assertEquals(SaveStatus.SAVED, owner.state.value.status)
    }

    @Test fun sessionStartGateAlsoAppliesToNewControllerAfterPageOwnerWasCleared() = runTest {
        val commit = CompletableDeferred<Unit>()
        val owner = SessionSaveController(this) { commit.await(); error("Write failed") }
        fun controller() = SessionController(DataSubscriptions(this), { 0 }, {}, {}, { SessionSummary() },
            canStart = { !owner.state.value.blocksStart })
        owner.submit(snapshot()); runCurrent()
        assertFalse(controller().start(true) { error("Must not start") })
        commit.complete(Unit); runCurrent()
        assertFalse(controller().start(true) { error("Must not start") })
        owner.discard()
        assertTrue(controller().start(true) {})
    }
}
