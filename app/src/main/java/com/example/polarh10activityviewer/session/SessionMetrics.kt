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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.VerticalDivider
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import com.example.polarh10activityviewer.ble.HeartRateReading
import com.example.polarh10activityviewer.ble.HeartRateStatistics
import com.example.polarh10activityviewer.ble.SubscriptionState
import com.example.polarh10activityviewer.ble.SubscriptionStatus
import com.example.polarh10activityviewer.heartrate.formatZoneDuration
import com.example.polarh10activityviewer.heartrate.HeartRateZoneState
import com.example.polarh10activityviewer.motion.StepState
import com.example.polarh10activityviewer.ui.theme.ContentSpacing
import com.example.polarh10activityviewer.ui.theme.ControlSpacing
import com.example.polarh10activityviewer.ui.theme.HeartRateZoneColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
private fun MetricCard(title: String?, content: @Composable ColumnScope.() -> Unit) {
    SessionCard(title = title, content = content)
}

@Composable
private fun MetricValue(value: String, unit: String? = null, heartRate: Boolean = false) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
        verticalArrangement = Arrangement.spacedBy(ContentSpacing)) {
        Text(value, modifier = Modifier.alignByBaseline(), style = MaterialTheme.typography.titleLarge.copy(
            fontSize = if (heartRate) 38.sp else 24.sp,
            lineHeight = if (heartRate) 42.sp else 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-1).sp
        ))
        unit?.let { Text(it, Modifier.alignByBaseline(), style = MaterialTheme.typography.bodySmall.copy(
            fontSize = if (!heartRate) 11.sp else if (it == "bpm") 18.sp else 14.sp,
            lineHeight = if (heartRate) 22.sp else 16.sp)) }
    }
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
    subscription: SubscriptionState
) {
    MetricCard(null) {
        val current = zones.current.takeUnless { stopped || reading == null || subscription.status == SubscriptionStatus.FAILED }
        MetricColumns(
            current = {
              Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
                val color = current?.let { HeartRateZoneColors[it.ordinal] } ?: MaterialTheme.colorScheme.outline
                Surface(shape = RoundedCornerShape(6.dp), border = BorderStroke(1.dp, color), color = color.copy(alpha = 0.10f)) {
                    Row(Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                        current?.let { Box(Modifier.size(9.dp).background(color, CircleShape)) }
                        Text(current?.let { "${it.label} · Zone ${it.ordinal + 1}" } ?: when {
                            stopped -> "Stopped"
                            subscription.status == SubscriptionStatus.FAILED -> "HR unavailable"
                            subscription.status == SubscriptionStatus.STARTING -> "Waiting for HR"
                            else -> "No valid HR"
                        },
                            style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    }
                }
                MetricValue(reading?.bpm?.toString() ?: "--", "bpm", heartRate = true)
              }
            },
            statistics = {
                SessionStatistic("Max HR", "${statistics.max ?: "--"} bpm", Modifier.fillMaxWidth(), TextAlign.Center)
                SessionStatistic("Mean HR", "${cadence(statistics.average)} bpm", Modifier.fillMaxWidth(), TextAlign.Center)
            }
        )
        val receivedAt = reading?.let {
            SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.ENGLISH)
                .format(Date(it.receivedAt))
        } ?: "--"
        Text("Last Received: $receivedAt", Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
    }
}

@Composable
internal fun MotionCard(steps: StepState, paused: Boolean = false) {
    MetricCard(null) {
        MetricColumns(
            current = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Cadence", Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium)
                    MetricValue(if (paused) "--" else cadence(steps.cadence), "steps/min", heartRate = true)
                }
            },
            statistics = {
                SessionStatistic("Mean", "${cadence(steps.meanCadence)} steps/min", Modifier.fillMaxWidth(), TextAlign.Center)
                SessionStatistic("Max", "${cadence(steps.maximumCadence)} steps/min", Modifier.fillMaxWidth(), TextAlign.Center)
            }
        )
    }
}

@Composable
private fun MetricColumns(current: @Composable () -> Unit, statistics: @Composable ColumnScope.() -> Unit) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        if (maxWidth < 290.dp * LocalDensity.current.fontScale) {
            Column(verticalArrangement = Arrangement.spacedBy(ControlSpacing)) {
                current()
                Column(verticalArrangement = Arrangement.spacedBy(ContentSpacing), content = statistics)
            }
        } else {
            Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.weight(1f)) { current() }
                VerticalDivider(Modifier.fillMaxHeight().padding(vertical = 8.dp), color = sessionBorder())
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(ContentSpacing), content = statistics)
            }
        }
    }
}

@Composable
internal fun ActivitySummaryCard(session: SessionState, steps: StepState) {
    MetricCard(null) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            if (maxWidth >= 280.dp * LocalDensity.current.fontScale) {
                Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SummaryMetric("Duration", formatZoneDuration(session.elapsedMs), Modifier.weight(1f).fillMaxHeight())
                    SummaryMetric("Total Steps", steps.totalSteps?.toString() ?: "--", Modifier.weight(1f).fillMaxHeight())
                    SummaryMetric("Estimated Distance", decimal(steps.distance), Modifier.weight(1f).fillMaxHeight(), "m")
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    SummaryMetric("Duration", formatZoneDuration(session.elapsedMs), Modifier.fillMaxWidth())
                    SummaryMetric("Total Steps", steps.totalSteps?.toString() ?: "--", Modifier.fillMaxWidth())
                    SummaryMetric("Estimated Distance", decimal(steps.distance), Modifier.fillMaxWidth(), "m")
                }
            }
        }
    }
}

@Composable
private fun SummaryMetric(label: String, value: String, modifier: Modifier, unit: String? = null) {
    Surface(modifier, shape = RoundedCornerShape(SessionCorner), border = BorderStroke(1.dp, sessionBorder())) {
        Column(Modifier.padding(horizontal = 4.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(label, Modifier.fillMaxWidth(), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
            MetricValue(value, unit)
        }
    }
}
