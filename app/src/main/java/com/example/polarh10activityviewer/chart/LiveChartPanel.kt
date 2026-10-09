package com.example.polarh10activityviewer.chart

import com.example.polarh10activityviewer.ble.PolarBleManager
import com.example.polarh10activityviewer.ble.DataReadiness
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.example.polarh10activityviewer.session.SessionStatus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
internal fun LiveChartPanel(manager: PolarBleManager,
    readiness: Map<PolarDeviceDataType, DataReadiness>
) {
    val kind by manager.liveCharts.selection.collectAsState()
    val session by manager.sessionState.collectAsState()
    val paused = session.status in listOf(SessionStatus.PAUSING, SessionStatus.PAUSED)
    var snapshot by remember(manager, kind, session.generation) { mutableStateOf(manager.chartSnapshot(kind)) }
    // Only the selected chart takes display snapshots. Sampling remains independent.
    LaunchedEffect(manager, kind, session.generation) {
        while (true) {
            snapshot = manager.chartSnapshot(kind)
            delay(when (kind) {
                ChartKind.ELECTROCARDIOGRAM -> 100L
                else -> 250L
            })
        }
    }
    val statistics = chartStatistics(snapshot, kind)
    LiveChartCard(kind, snapshot, statistics.mean, statistics.maximum, readiness,
        manager.liveCharts::select,
        paused = paused)
}
