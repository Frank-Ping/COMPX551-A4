package com.example.polarh10activityviewer.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.sessionBackground
import com.example.polarh10activityviewer.session.sessionBlue
import com.example.polarh10activityviewer.session.sessionBorder
import com.example.polarh10activityviewer.storage.HISTORY_PAGE_SIZE
import com.example.polarh10activityviewer.storage.SessionDatabase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.isActive
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun HistoryList(database: SessionDatabase, sessionStatus: @Composable () -> Unit,
    onSelect: (String) -> Unit) {
    var records by remember { mutableStateOf<List<SessionRecord>>(emptyList()) }
    var cursor by remember { mutableStateOf<SessionRecord?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var loading by remember { mutableStateOf(true) }
    var more by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var zone by remember { mutableStateOf(ZoneId.systemDefault()) }
    // Pages are reloaded on re-entry, so a restored offset must not outlive their rows.
    val list = remember { LazyListState() }
    LaunchedEffect(cursor, retry) {
        loading = true; error = null
        zone = ZoneId.systemDefault()
        try {
            val page = database.page(cursor)
            ensureActive()
            records = if (cursor == null) page else records + page
            more = page.size == HISTORY_PAGE_SIZE
        } catch (cancelled: CancellationException) { throw cancelled }
        catch (_: Exception) { error = "History query failed. Please retry." }
        finally { if (isActive) loading = false }
    }
    LaunchedEffect(list) {
        snapshotFlow {
            more && !loading && error == null && records.isNotEmpty() &&
                list.layoutInfo.visibleItemsInfo.any { it.index >= records.size - 1 }
        }.filter { it }.collect {
            // Lock the next request before changing the cursor, including during a fling.
            loading = true
            cursor = records.last()
        }
    }
    val date = remember(zone) { DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH).withZone(zone) }
    val time = remember(zone) { DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH).withZone(zone) }
    val accent = sessionBlue()
    val track = sessionBorder()
    LazyColumn(state = list, modifier = Modifier.fillMaxSize().background(sessionBackground())
        .testTag("history-list").drawWithContent {
            drawContent()
            val indicator = list.scrollIndicatorState ?: return@drawWithContent
            if (!list.canScrollForward && !list.canScrollBackward) return@drawWithContent
            val content = indicator.contentSize
            val viewport = indicator.viewportSize
            if (content <= viewport || content == Int.MAX_VALUE || viewport <= 0) return@drawWithContent
            val inset = 8.dp.toPx()
            val height = (size.height - inset * 2).coerceAtLeast(0f)
            val thumb = (height * viewport / content).coerceIn(minOf(24.dp.toPx(), height), height)
            val fraction = (indicator.scrollOffset.toFloat() / (content - viewport)).coerceIn(0f, 1f)
            val x = size.width - 6.dp.toPx()
            val width = 3.dp.toPx()
            drawRoundRect(track, Offset(x, inset), Size(width, height), CornerRadius(width))
            drawRoundRect(accent.copy(alpha = 0.65f), Offset(x, inset + fraction * (height - thumb)),
                Size(width, thumb), CornerRadius(width))
        }, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item(key = "heading") {
            Text("History Activities", Modifier.fillMaxWidth().padding(bottom = 8.dp),
                textAlign = TextAlign.Center, fontSize = 26.sp, lineHeight = 32.sp, fontWeight = FontWeight.SemiBold)
        }
        items(records, key = { it.id }, contentType = { "activity" }) { record ->
            HistoryListCard(record, date, time) { onSelect(record.id) }
        }
        item(key = "status") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                    Button(onClick = { loading = true; retry++ }, enabled = !loading) { Text("Retry query") }
                }
                if (!loading && error == null && records.isEmpty()) Text("No saved sessions")
                sessionStatus()
            }
        }
    }
}

@Composable
private fun HistoryListCard(record: SessionRecord, date: DateTimeFormatter, time: DateTimeFormatter,
    onClick: () -> Unit) {
    val started = Instant.ofEpochMilli(record.startedAt!!)
    val accent = sessionBlue()
    Surface(onClick = onClick, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
        .testTag("history-row-${record.id}"), shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface, border = BorderStroke(1.dp, sessionBorder())) {
        BoxWithConstraints(Modifier.padding(16.dp)) {
            val stacked = maxWidth / LocalDensity.current.fontScale < 260.dp
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                val dateContent: @Composable () -> Unit = {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(date.format(started), fontSize = 20.sp, lineHeight = 26.sp, fontWeight = FontWeight.SemiBold)
                        Text(time.format(started), style = MaterialTheme.typography.bodyLarge)
                        if (record.incomplete) Text("Incomplete", style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                val durationContent: @Composable () -> Unit = {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Duration", style = MaterialTheme.typography.bodyMedium)
                        Text(formatZoneDuration(record.durationMs), fontSize = 26.sp, lineHeight = 32.sp,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
                if (stacked) Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    dateContent(); durationContent()
                } else {
                    Column(Modifier.weight(1.4f)) { dateContent() }
                    Column(Modifier.weight(1f)) { durationContent() }
                }
                Canvas(Modifier.size(16.dp)) {
                    val stroke = 2.dp.toPx()
                    drawLine(accent, Offset(size.width * 0.3f, size.height * 0.15f),
                        Offset(size.width * 0.7f, size.height * 0.5f), stroke, StrokeCap.Round)
                    drawLine(accent, Offset(size.width * 0.7f, size.height * 0.5f),
                        Offset(size.width * 0.3f, size.height * 0.85f), stroke, StrokeCap.Round)
                }
            }
        }
    }
}
