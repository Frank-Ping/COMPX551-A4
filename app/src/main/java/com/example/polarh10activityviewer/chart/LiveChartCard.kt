package com.example.polarh10activityviewer.chart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.selection.selectable
import androidx.compose.ui.semantics.Role
import com.example.polarh10activityviewer.session.SessionCard
import com.example.polarh10activityviewer.session.SessionStatistic
import com.example.polarh10activityviewer.session.sessionBlue
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.ui.theme.*
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting.SettingType

@Composable
internal fun LiveChartCard(
    kind: ChartKind, snapshot: ChartSnapshot,
    sessionMean: Double?, sessionMaximum: Double?,
    readiness: Map<PolarDeviceDataType, DataReadiness>,
    onSelect: (ChartKind) -> Unit, paused: Boolean
) {
    SessionCard(modifier = Modifier.testTag("live-chart-card")) {
            Row(Modifier.fillMaxWidth(if (LocalDensity.current.fontScale > 1.2f) 1f else 0.76f)
                .align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("HR" to ChartKind.HEART_RATE, "Cadence" to ChartKind.CADENCE,
                    "ECG" to ChartKind.ELECTROCARDIOGRAM).forEach { (label, choice) ->
                    ChartChoice(label, kind.type == choice.type, Modifier.weight(1f)) { onSelect(choice) }
                }
            }
            if (kind == ChartKind.ELECTROCARDIOGRAM) {
                val rate = readiness[PolarDeviceDataType.ECG]?.selected
                    ?.get(SettingType.SAMPLE_RATE)
                val count = snapshot.points.count { it.value != null }
                val countLabel = if (count == 0 && snapshot.status in
                    listOf(SubscriptionStatus.IDLE, SubscriptionStatus.STARTING)) "--" else "$count"
                ChartStatistics("Sampling Rate", rate?.let { "$it Hz" } ?: "--", "Samples", countLabel)
            } else {
                ChartStatistics(if (kind == ChartKind.HEART_RATE) "Average HR" else "Mean", "${sessionMean?.let {
                        chartScaleLabel(it, kind)
                    } ?: "--"} ${kind.unit}", if (kind == ChartKind.HEART_RATE) "Max HR" else "Max",
                    "${sessionMaximum?.let { chartScaleLabel(it, kind) } ?: "--"} ${kind.unit}")
            }
            val valid = snapshot.points.any { it.value != null }
            val statusLabel = if (kind == ChartKind.HEART_RATE) {
                if (paused) "Paused · chart frozen"
                else when (snapshot.status) {
                    SubscriptionStatus.IDLE -> null
                    SubscriptionStatus.STARTING -> "Waiting for data"
                    SubscriptionStatus.RECEIVING -> if (valid) null else "Waiting for valid data"
                    SubscriptionStatus.STOPPING -> "Stopping · chart frozen"
                    SubscriptionStatus.STOPPED -> "Stopped · chart frozen"
                    SubscriptionStatus.FAILED -> "Failed · chart frozen"
                }
            } else if (!paused && snapshot.detectingSteps) "Detecting steps" else null
            LivePlot(snapshot, kind, sessionMean, statusLabel, smoothLine = kind == ChartKind.CADENCE)
    }
}

@Composable
private fun ChartStatistics(left: String, leftValue: String, right: String, rightValue: String) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        if (maxWidth < 280.dp * LocalDensity.current.fontScale) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                SessionStatistic(left, leftValue); SessionStatistic(right, rightValue)
            }
        } else Row(Modifier.fillMaxWidth()) {
            SessionStatistic(left, leftValue, Modifier.weight(1f))
            SessionStatistic(right, rightValue, Modifier.weight(1f), TextAlign.End)
        }
    }
}

@Composable
private fun ChartChoice(label: String, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(modifier.heightIn(min = MinimumTouchTarget).clip(RoundedCornerShape(50))
        .selectable(selected, role = Role.Tab, onClick = onClick),
        contentAlignment = Alignment.Center) {
        Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(50),
            color = if (selected) sessionBlue() else sessionBlue().copy(alpha = 0.07f),
            contentColor = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface) {
            Text(label, Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
