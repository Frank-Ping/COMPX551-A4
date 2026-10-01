package com.example.polarh10activityviewer.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.ui.theme.CardCornerRadius
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.MinimumTouchTarget
import com.example.polarh10activityviewer.ui.theme.PagePadding
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
private fun MetricCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(Modifier.fillMaxWidth(), shape = RoundedCornerShape(CardCornerRadius)) {
        Column(Modifier.padding(PagePadding), verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
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
private fun speed(value: Double?): String = decimal(value?.times(3.6))

@Composable
internal fun HeartRateCard(
    reading: HeartRateReading?,
    statistics: HeartRateStatistics,
    message: String?,
    subscription: SubscriptionState,
    canRetry: Boolean,
    onRetry: () -> Unit
) {
    MetricCard("Heart rate") {
        MetricValue(reading?.bpm?.toString() ?: "--", "bpm", heartRate = true)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(ControlSpacing),
            verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
            SecondaryText("Min HR: ${statistics.min ?: "--"} bpm")
            SecondaryText("Max HR: ${statistics.max ?: "--"} bpm")
            SecondaryText("Mean HR: ${decimal(statistics.average)} bpm")
        }
        SecondaryText("Mean of valid HR samples")
        val receivedAt = reading?.let {
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.ENGLISH)
                .format(Date(it.receivedAt))
        } ?: "--"
        SecondaryText("Last received (phone): $receivedAt")
        SecondaryText("Phone reception time, not sensor sampling time.")
        SecondaryText("HR stream: ${subscription.status.name.lowercase().replaceFirstChar { it.uppercase() }}")
        message?.let { Text(it) }
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.HR, subscription, canRetry, onRetry)
    }
}

@Composable
internal fun MotionCard(steps: StepState, subscription: SubscriptionState, canRetry: Boolean, onRetry: () -> Unit) {
    MetricCard("Motion") {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            if (maxWidth < 320.dp * LocalDensity.current.fontScale) {
                Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                    CadenceMetric(steps, Modifier.fillMaxWidth())
                    SpeedMetric(steps, Modifier.fillMaxWidth())
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                    CadenceMetric(steps, Modifier.weight(1f))
                    SpeedMetric(steps, Modifier.weight(1f))
                }
            }
        }
        SecondaryText("Averages include all Running time. Extrema use qualifying five-second ACC windows.")
        if (!steps.receivedAcc) SecondaryText("No ACC observations. Statistics are unavailable.")
        else {
            if (steps.durationMs == 0L) SecondaryText("Mean cadence and speed need a positive Running duration.")
            if (steps.minimumCadence == null) SecondaryText("No qualifying five-second ACC window yet.")
        }
        Text(steps.message)
        if (steps.incompleteAcc) SecondaryText(
            "Incomplete ACC data. Missing data may lower recorded distance and mean speed/cadence. Extrema describe recorded windows."
        )
        subscription.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        StreamRetryButton(PolarDeviceDataType.ACC, subscription, canRetry, onRetry)
    }
}

@Composable
private fun CadenceMetric(steps: StepState, modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        Text("Cadence")
        MetricValue(cadence(steps.cadence), "steps/min")
        SecondaryText("Mean cadence: ${cadence(steps.meanCadence)} steps/min")
        SecondaryText("Min cadence: ${cadence(steps.minimumCadence)} steps/min")
        SecondaryText("Max cadence: ${cadence(steps.maximumCadence)} steps/min")
    }
}

@Composable
private fun SpeedMetric(steps: StepState, modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        Text("Estimated speed")
        MetricValue(speed(steps.speed), "km/h")
        SecondaryText("Mean speed: ${speed(steps.averageSpeed)} km/h")
        SecondaryText("Max speed: ${speed(steps.maximumSpeed)} km/h")
    }
}

@Composable
internal fun ActivitySummaryCard(session: SessionState, steps: StepState) {
    MetricCard("Activity summary") {
        SummaryRow("Running duration (mm:ss)", formatZoneDuration(session.elapsedMs))
        SummaryRow("Total steps", steps.totalSteps?.toString() ?: "--")
        SummaryRow("Estimated distance", decimal(steps.distance), "m")
        SecondaryText("Running time includes stationary/rest and missing-data periods. Distance and speed are estimates.")
    }
}

@Composable
private fun SummaryRow(label: String, value: String, unit: String? = null) {
    FlowRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        Text(label)
        MetricValue(value, unit)
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
