package com.example.polarh10activityviewer.chart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.polarh10activityviewer.ble.*
import com.example.polarh10activityviewer.session.StreamRetryButton
import com.example.polarh10activityviewer.ui.theme.*
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting.SettingType

@Composable
internal fun LiveChartCard(
    kind: ChartKind, snapshot: ChartSnapshot,
    sessionMean: Double?, sessionMaximum: Double?,
    subscriptions: Map<PolarDeviceDataType, SubscriptionState>,
    readiness: Map<PolarDeviceDataType, DataReadiness>,
    canRetryEcg: Boolean, onSelect: (ChartKind) -> Unit, onRetryEcg: () -> Unit, paused: Boolean
) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius),
        color = MaterialTheme.colorScheme.surface) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
                verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                listOf("HR" to ChartKind.HEART_RATE, "Motion" to ChartKind.CADENCE,
                    "ECG" to ChartKind.ELECTROCARDIOGRAM).forEach { (label, choice) ->
                    ChartChoice(label, kind.type == choice.type) { onSelect(choice) }
                }
            }
            if (kind == ChartKind.ELECTROCARDIOGRAM) {
                val rate = readiness[PolarDeviceDataType.ECG]?.selected
                    ?.get(SettingType.SAMPLE_RATE)
                val count = snapshot.points.count { it.value != null }
                val countLabel = if (count == 0 && snapshot.status in
                    listOf(SubscriptionStatus.IDLE, SubscriptionStatus.STARTING)) "--" else "$count"
                FlowRow(horizontalArrangement = Arrangement.spacedBy(ControlSpacing),
                    verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Text("Sampling Rate: ${rate?.let { "$it Hz" } ?: "--"}")
                    Text("Samples: $countLabel")
                }
            } else {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(ControlSpacing),
                    verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
                    Text("${if (kind == ChartKind.HEART_RATE) "Average HR" else "Mean"}: ${sessionMean?.let {
                        chartScaleLabel(it, kind)
                    } ?: "--"} ${kind.unit}")
                    Text("${if (kind == ChartKind.HEART_RATE) "Max HR" else "Max"}: ${sessionMaximum?.let { chartScaleLabel(it, kind) } ?: "--"} ${kind.unit}")
                }
            }
            val valid = snapshot.points.any { it.value != null }
            if (paused) Text("Paused · chart frozen")
            else if (snapshot.status != SubscriptionStatus.RECEIVING || !valid) Text(when (snapshot.status) {
                SubscriptionStatus.IDLE -> "Not started"
                SubscriptionStatus.STARTING -> "Waiting for data"
                SubscriptionStatus.RECEIVING -> if (valid) "Live" else "Waiting for valid data"
                SubscriptionStatus.STOPPING -> "Stopping · chart frozen"
                SubscriptionStatus.STOPPED -> "Stopped · chart frozen"
                SubscriptionStatus.FAILED -> "Failed · chart frozen"
            })
            // Keep configuration failures reachable even after removing raw sample panels.
            listOf(PolarDeviceDataType.ACC, PolarDeviceDataType.ECG).forEach { type ->
                val state = readiness[type] ?: DataReadiness()
                state.error?.let { Text("$type: $it", color = MaterialTheme.colorScheme.error) }
                if (state.status == DataReadinessStatus.READY && !state.configurationComplete && state.error == null) {
                    Text("Open Devices to confirm $type configuration before starting.")
                }
            }
            val ecg = subscriptions[PolarDeviceDataType.ECG] ?: SubscriptionState()
            // ECG recovery stays visible while viewing HR or Motion, with the original guards.
            if (kind == ChartKind.ELECTROCARDIOGRAM || ecg.error != null || ecg.status == SubscriptionStatus.FAILED) {
                ecg.error?.let { Text("ECG: $it", color = MaterialTheme.colorScheme.error) }
                StreamRetryButton(PolarDeviceDataType.ECG, ecg, canRetryEcg, onRetryEcg)
            }
            LivePlot(snapshot, kind, sessionMean)
        }
    }
}

@Composable
private fun ChartChoice(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(label) },
        modifier = Modifier.heightIn(min = MinimumTouchTarget), shape = RoundedCornerShape(50), border = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f),
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary))
}
