package com.example.polarh10activityviewer.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.ui.theme.PagePadding
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.chart.ChartPlot
import com.example.polarh10activityviewer.chart.ChartPoint
import com.example.polarh10activityviewer.chart.ChartSnapshot
import com.example.polarh10activityviewer.heartrate.HeartRateZone
import com.example.polarh10activityviewer.heartrate.HeartRateZonePanel
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.session.SessionSnapshot
import com.example.polarh10activityviewer.session.SessionSummaryPanel
import com.example.polarh10activityviewer.session.SessionDetails
import com.example.polarh10activityviewer.session.sessionBackground
import com.example.polarh10activityviewer.session.sessionBlue
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
    sessionStatus: @Composable () -> Unit = {}) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var reload by remember { mutableIntStateOf(0) }
    var detail by remember { mutableStateOf<SessionSnapshot?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var deleteError by remember { mutableStateOf<String?>(null) }
    var date by remember { mutableStateOf(historyDateFormatter()) }
    var confirmDelete by remember { mutableStateOf(false) }
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
    Column(Modifier.fillMaxSize().background(sessionBackground())
        .verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        val selectedRecord = detail?.record?.takeIf { it.id == selectedId }
        val accent = sessionBlue()
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { back() }, enabled = !confirmDelete,
                modifier = Modifier.semantics { contentDescription = "Back" }) {
                Canvas(Modifier.size(24.dp)) {
                    val stroke = 2.dp.toPx()
                    drawLine(accent, Offset(size.width * 0.85f, size.height * 0.5f),
                        Offset(size.width * 0.15f, size.height * 0.5f), stroke, StrokeCap.Round)
                    drawLine(accent, Offset(size.width * 0.45f, size.height * 0.2f),
                        Offset(size.width * 0.15f, size.height * 0.5f), stroke, StrokeCap.Round)
                    drawLine(accent, Offset(size.width * 0.45f, size.height * 0.8f),
                        Offset(size.width * 0.15f, size.height * 0.5f), stroke, StrokeCap.Round)
                }
            }
            Column(Modifier.weight(1f).padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Activity Summary", fontSize = 26.sp, lineHeight = 32.sp, fontWeight = FontWeight.SemiBold)
                selectedRecord?.let { record ->
                    val shortDate = DateTimeFormatter.ofPattern("dd MMM yyyy · HH:mm", Locale.ENGLISH)
                        .withZone(date.zone)
                    Text(record.startedAt?.let { shortDate.format(Instant.ofEpochMilli(it)) } ?: "--",
                        style = MaterialTheme.typography.bodyLarge)
                    if (record.incomplete) Text("Incomplete", style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        sessionStatus()
        if (selectedId != null && loading) Text("Loading…")
        error?.takeIf { selectedId != null }?.let {
            Text(it)
            Button(onClick = { loading = true; reload++ }, enabled = !loading) { Text("Retry query") }
        }
        if (!loading && error == null && detail == null) Text("Session not found")
        detail?.takeIf { it.record.id == selectedId }?.let { snapshot ->
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
            SessionDetails(snapshot.record, date)
            deleteError?.let { Text(it) }
            Button(onClick = { date = historyDateFormatter(); confirmDelete = true }, enabled = !loading) { Text("Delete session") }
        }
        Text("Stored on this device only. Uninstalling or clearing app data deletes history.",
            style = MaterialTheme.typography.bodySmall)
        Text("An unsaved session may be lost if the process ends before the database commit.",
            style = MaterialTheme.typography.bodySmall)
    }
    if (confirmDelete) AlertDialog(
        onDismissRequest = { if (!loading) confirmDelete = false },
        title = { Text("Delete this session?") },
        text = { Text("Running started: ${date.format(Instant.ofEpochMilli(detail!!.record.startedAt!!))}\n" +
            "Its summary and both history series will be permanently deleted.") },
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
