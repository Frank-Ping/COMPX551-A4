package com.example.polarh10activityviewer.history

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.polarh10activityviewer.heartrate.HeartRateZoneRows
import com.example.polarh10activityviewer.session.SessionRecord
import com.example.polarh10activityviewer.session.SummaryCard

@Composable
internal fun HistoryZones(record: SessionRecord, modifier: Modifier = Modifier) {
    val summary = record.summary
    SummaryCard(modifier, title = "HR Zones") {
        if (!summary.receivedValidHr) Text("No valid heart rate data", style = MaterialTheme.typography.bodySmall)
        HeartRateZoneRows(summary.zoneDurationsMs, record.durationMs, summary.receivedValidHr)
    }
}
