package com.example.polarh10activityviewer.history

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.storage.SessionDatabase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun HistoryPanel(database: SessionDatabase, savedId: String?, onBack: () -> Unit,
    sessionStatus: @Composable () -> Unit = {}, allowCompact: Boolean = true) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var reload by remember { mutableIntStateOf(0) }
    // A new ID starts in its loading state before the query effect is launched.
    var detail by remember(selectedId) { mutableStateOf<SessionSnapshot?>(null) }
    var loading by remember(selectedId) { mutableStateOf(true) }
    var error by remember(selectedId) { mutableStateOf<String?>(null) }
    var deleteError by remember(selectedId) { mutableStateOf<String?>(null) }
    var date by remember(selectedId) { mutableStateOf(historyDateFormatter()) }
    var confirmDelete by remember(selectedId) { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    fun back() {
        if (confirmDelete) return
        if (selectedId == null) onBack() else selectedId = null
    }
    BackHandler { back() }
    LaunchedEffect(selectedId, reload) {
        if (selectedId == null) return@LaunchedEffect
        loading = true; error = null; deleteError = null; detail = null; confirmDelete = false
        date = historyDateFormatter()
        try {
            val result = database.detail(selectedId!!)
            ensureActive()
            detail = result
        } catch (cancelled: CancellationException) { throw cancelled }
        catch (_: Exception) { error = "History query failed. Please retry." }
        finally { if (isActive) loading = false }
    }
    if (selectedId == null) {
        // Re-entry or a new committed save starts a fresh list and cancels the old query.
        key(savedId) { HistoryList(database, sessionStatus) { selectedId = it } }
        return
    }
    HistoryDetailLayout(
        snapshot = detail?.takeIf { it.record.id == selectedId }, loading = loading,
        error = error, deleteError = deleteError, date = date, canGoBack = !confirmDelete,
        onBack = { back() }, onRetry = { loading = true; reload++ },
        onDelete = { date = historyDateFormatter(); confirmDelete = true }, sessionStatus = sessionStatus,
        allowCompact = allowCompact, database = database
    )
    if (confirmDelete) AlertDialog(
        onDismissRequest = { if (!loading) confirmDelete = false },
        title = { Text("Delete this session?") },
        text = { Text("Running started: ${date.format(Instant.ofEpochMilli(detail!!.record.startedAt!!))}\n" +
            "Its summary and all recorded chart data will be permanently deleted.") },
        confirmButton = { TextButton(enabled = !loading, onClick = {
            val id = selectedId!!
            loading = true; deleteError = null
            scope.launch {
                try {
                    database.delete(id)
                    ensureActive()
                    confirmDelete = false; selectedId = null; reload++
                } catch (cancelled: CancellationException) { throw cancelled }
                catch (_: Exception) { confirmDelete = false; deleteError = "Delete failed. Please retry Delete session." }
                finally { if (isActive) loading = false }
            }
        }) { Text("Delete") } },
        dismissButton = { TextButton(enabled = !loading, onClick = { confirmDelete = false }) { Text("Cancel") } }
    )
}

private fun historyDateFormatter() = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX", Locale.ENGLISH)
    .withZone(ZoneId.systemDefault())
