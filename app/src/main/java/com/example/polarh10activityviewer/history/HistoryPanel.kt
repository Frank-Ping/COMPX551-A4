package com.example.polarh10activityviewer.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.chart.ChartPlot
import com.example.polarh10activityviewer.chart.ChartPoint
import com.example.polarh10activityviewer.chart.ChartSnapshot
import com.example.polarh10activityviewer.heartrate.HeartRateZone
import com.example.polarh10activityviewer.heartrate.HeartRateZonePanel
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SessionSummaryPanel
import com.example.polarh10activityviewer.storage.SessionDatabase
import com.example.polarh10activityviewer.storage.SessionSaveController
import com.example.polarh10activityviewer.storage.SaveState
import com.example.polarh10activityviewer.storage.SaveStatus
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun SavePanel(state: SaveState, controller: SessionSaveController) {
    Text("Save: ${state.status.label}")
    state.sessionId?.let { Text("Save session ID: $it", style = MaterialTheme.typography.bodySmall) }
    if (state.status == SaveStatus.FAILED) {
        Text(state.error ?: "Unable to save session.")
        Row {
            Button(onClick = { controller.retry() }) { Text("Retry save") }
            TextButton(onClick = { controller.discard() }) { Text("Discard session") }
        }
    }
    if (state.blocksStart) Text("New Start is blocked until saving succeeds or the failed session is discarded.")
}

@Composable
internal fun HistoryPanel(database: SessionDatabase, savedId: String?, onBack: () -> Unit) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var reload by remember { mutableStateOf(0) }
    var records by remember { mutableStateOf<List<SessionRecord>>(emptyList()) }
    var detail by remember { mutableStateOf<SessionSnapshot?>(null) }
    var loading by remember { mutableStateOf(true) }
    var more by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var confirmDelete by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var pageJob by remember { mutableStateOf<Job?>(null) }
    fun back() { if (selectedId == null) onBack() else selectedId = null }
    BackHandler { back() }
    LaunchedEffect(selectedId, savedId, reload) {
        pageJob?.cancelAndJoin()
        loading = true; error = null; detail = null; confirmDelete = false
        try {
            val id = selectedId
            if (id == null) {
                records = database.page()
                more = records.size == 20
            } else detail = database.detail(id)
        } catch (cancelled: CancellationException) { throw cancelled }
        catch (failure: Exception) { error = failure.message ?: "History query failed." }
        finally { loading = false }
    }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("History (development check)", style = MaterialTheme.typography.titleLarge)
        TextButton(onClick = { back() }, enabled = !confirmDelete) { Text("Back") }
        Text("Stored on this device only. Uninstalling or clearing app data deletes history.")
        Text("An unsaved session may be lost if the process ends before the database commit.")
        if (loading) Text("Loading…")
        error?.let {
            Text(it)
            Button(onClick = { reload++ }, enabled = !loading) { Text("Retry query") }
        }
        if (selectedId == null) {
            if (!loading && error == null && records.isEmpty()) Text("No saved sessions")
            val date = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX", Locale.ENGLISH).withZone(ZoneId.systemDefault()) }
            records.forEach { record ->
                TextButton(onClick = { selectedId = record.id }, enabled = !loading) {
                    Text("${date.format(Instant.ofEpochMilli(record.startedAt!!))}\n" +
                        "${formatZoneDuration(record.durationMs)} · Steps: ${record.summary.totalSteps ?: "--"} · " +
                        "Estimated distance: ${record.summary.distanceMetres?.let { String.format(Locale.ENGLISH, "%.2f", it) } ?: "--"} m" +
                        if (record.incomplete) " · Incomplete" else "")
                }
            }
            if (more) Button(onClick = {
                loading = true; error = null
                pageJob = scope.launch {
                    try {
                        val next = database.page(records.last())
                        records = records + next; more = next.size == 20
                    } catch (cancelled: CancellationException) { throw cancelled }
                    catch (failure: Exception) { error = failure.message ?: "History query failed." }
                    finally { loading = false }
                }
            }, enabled = !loading) { Text("Load more") }
        } else if (!loading && error == null && detail == null) Text("Session not found")
        detail?.let { snapshot ->
            SessionSummaryPanel(snapshot.record)
            val summary = snapshot.record.summary
            HeartRateZonePanel(HeartRateZoneState(summary.zoneDurationsMs, unclassifiedMs = summary.unclassifiedMs,
                receivedValidHr = summary.receivedValidHr), stopped = true)
            HeartRateZone.entries.forEachIndexed { index, zone ->
                val percent = if (snapshot.record.durationMs > 0) String.format(Locale.ENGLISH, "%.2f%%",
                    summary.zoneDurationsMs[index] * 100.0 / snapshot.record.durationMs) else "--"
                Text("${zone.label}: $percent of Running time")
            }
            val hr = remember(snapshot) { snapshot.hrPoints.map { ChartPoint(it.elapsedMs.toDouble(), it.bpm?.toDouble(), it.breakBefore) } }
            val motion = remember(snapshot) { snapshot.motionPoints.map { ChartPoint(it.elapsedMs.toDouble(), it.cadence, it.breakBefore) } }
            Text("Heart rate (bpm)")
            ChartPlot(ChartSnapshot(hr, snapshot.record.durationMs.toDouble(), snapshot.record.durationMs.toDouble(), SubscriptionStatus.STOPPED))
            Text("Cadence (steps/min)")
            ChartPlot(ChartSnapshot(motion, snapshot.record.durationMs.toDouble(), snapshot.record.durationMs.toDouble(), SubscriptionStatus.STOPPED))
            Text("Elapsed since Running (mm:ss). Gaps are not interpolated.")
            Button(onClick = { confirmDelete = true }, enabled = !loading) { Text("Delete session") }
        }
    }
    if (confirmDelete) AlertDialog(
        onDismissRequest = { if (!loading) confirmDelete = false },
        title = { Text("Delete this session?") },
        text = { Text("Its summary and both history series will be permanently deleted.") },
        confirmButton = { TextButton(enabled = !loading, onClick = {
            val id = selectedId!!
            loading = true
            scope.launch {
                try {
                    database.delete(id)
                    confirmDelete = false; selectedId = null; reload++
                } catch (cancelled: CancellationException) { throw cancelled }
                catch (failure: Exception) { confirmDelete = false; error = failure.message ?: "Delete failed." }
                finally { loading = false }
            }
        }) { Text("Delete") } },
        dismissButton = { TextButton(enabled = !loading, onClick = { confirmDelete = false }) { Text("Cancel") } }
    )
}
