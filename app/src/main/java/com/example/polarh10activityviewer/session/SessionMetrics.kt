package com.example.polarh10activityviewer.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.ble.HeartRateReading
import com.example.polarh10activityviewer.ble.HeartRateStatistics
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.ui.theme.CardCornerRadius
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.MinimumTouchTarget
import com.example.polarh10activityviewer.ui.theme.PagePadding
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
private fun MetricCard(title: String?, content: @Composable ColumnScope.() -> Unit) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
            title?.let { Text(it, style = MaterialTheme.typography.titleMedium) }
            content()
        }
    }
}

@Composable
private fun MetricValue(value: String, unit: String? = null, heartRate: Boolean = false) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
        verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        Text(value, style = MaterialTheme.typography.titleLarge.copy(
            fontSize = if (heartRate) 56.sp else 28.sp,
            lineHeight = if (heartRate) 64.sp else 36.sp,
            fontWeight = FontWeight.Medium
        ))
        unit?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
    }
}

@Composable
private fun SecondaryText(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant)
}

private fun decimal(value: Double?): String =
    value?.let { String.format(Locale.ENGLISH, "%.1f", it) } ?: "--"

private fun cadence(value: Double?): String = value?.roundToInt()?.toString() ?: "--"

@Composable
internal fun HeartRateCard(
    reading: HeartRateReading?,
    statistics: HeartRateStatistics,
    zones: HeartRateZoneState,
    stopped: Boolean,
    message: String?,
    subscription: SubscriptionState,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    MetricCard(null) {
        val current = zones.current.takeUnless { stopped || reading == null || subscription.status == SubscriptionStatus.FAILED }
        Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            Row(horizontalArrangement = Arrangement.spacedBy(ContentSpacing), verticalAlignment = Alignment.CenterVertically) {
                current?.let { Box(Modifier.size(12.dp).background(HeartRateZoneColors[it.ordinal])) }
                Text(current?.let { "${it.label} · Zone ${it.ordinal + 1}" } ?: "--",
                    modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
            }
            if (current == null) SecondaryText(when {
                stopped -> "Stopped"
                subscription.status == SubscriptionStatus.FAILED -> "HR unavailable"
                subscription.status == SubscriptionStatus.STARTING -> "Waiting for HR data"
                else -> "No valid HR data"
            })
        }
        MetricColumns(
            current = { MetricValue(reading?.bpm?.toString() ?: "--", "bpm", heartRate = true) },
            statistics = {
                SecondaryText("Max HR: ${statistics.max ?: "--"} bpm")
                SecondaryText("Mean HR: ${cadence(statistics.average)} bpm")
            }
        )
        val receivedAt = reading?.let {
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.ENGLISH)
                .format(Date(it.receivedAt))
        } ?: "--"
        SecondaryText("Last received (phone): $receivedAt")
        message?.let { Text(it) }
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.HR, subscription, canRetry, onRetry)
    }
}

@Composable
internal fun MotionCard(steps: StepState, subscription: SubscriptionState, canRetry: Boolean, onRetry: () -> Unit,
    paused: Boolean = false) {
    MetricCard("Cadence") {
        MetricColumns(
            current = { MetricValue(if (paused) "--" else cadence(steps.cadence), "steps/min", heartRate = true) },
            statistics = {
                SecondaryText("Mean cadence: ${cadence(steps.meanCadence)} steps/min")
                SecondaryText("Max cadence: ${cadence(steps.maximumCadence)} steps/min")
            }
        )
        if (!steps.receivedAcc) SecondaryText("No ACC observations. Statistics are unavailable.")
        else {
            if (steps.durationMs == 0L) SecondaryText("Mean cadence needs a positive Running duration.")
        }
        if (paused) Text("Paused")
        else if (steps.message != "Detecting steps.") Text(steps.message)
        if (steps.incompleteAcc) SecondaryText(
            "Incomplete ACC data. Mean cadence may be lower."
        )
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.ACC, subscription, canRetry, onRetry)
    }
}

@Composable
private fun MetricColumns(current: @Composable () -> Unit, statistics: @Composable ColumnScope.() -> Unit) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        if (maxWidth < 320.dp * LocalDensity.current.fontScale) {
            Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                current()
                Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing), content = statistics)
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(ControlSpacing), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.weight(1f)) { current() }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(ContentSpacing), content = statistics)
            }
        }
    }
}

@Composable
internal fun ActivitySummaryCard(session: SessionState, steps: StepState) {
    MetricCard("Activity Summary") {
        FlowRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ControlSpacing),
            verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
            val minimumWidth = 140.dp * LocalDensity.current.fontScale
            SummaryMetric("Duration", formatZoneDuration(session.elapsedMs),
                Modifier.weight(1f).widthIn(min = minimumWidth))
            SummaryMetric("Total steps", steps.totalSteps?.toString() ?: "--",
                Modifier.weight(1f).widthIn(min = minimumWidth))
            SummaryMetric("Estimated distance", decimal(steps.distance),
                Modifier.weight(1f).widthIn(min = minimumWidth), "m")
        }
    }
}

@Composable
private fun SummaryMetric(label: String, value: String, modifier: Modifier, unit: String? = null) {
    OutlinedCard(modifier, shape = RoundedCornerShape(CardCornerRadius)) {
        Column(Modifier.padding(ControlSpacing), verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            Text(label)
            MetricValue(value, unit)
        }
    }
}

@Composable
internal fun StreamRetryButton(type: PolarDeviceDataType, subscription: SubscriptionState, canRetry: Boolean, onRetry: () -> Unit) {
    if (subscription.status in setOf(SubscriptionStatus.IDLE, SubscriptionStatus.FAILED, SubscriptionStatus.STOPPED)) {
        Button(onClick = onRetry, enabled = canRetry, modifier = Modifier.heightIn(min = MinimumTouchTarget)) {
            Text("Retry $type")
        }
    }
}
