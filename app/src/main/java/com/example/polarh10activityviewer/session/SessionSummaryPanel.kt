package com.example.polarh10activityviewer.session

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.polarh10activityviewer.heartrate.HeartRateZone
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType.ACC
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun SessionSummaryPanel(record: SessionRecord?, dateFormat: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS XXX", Locale.ENGLISH).withZone(ZoneId.systemDefault())) {
    Text("Session record (development check)", style = MaterialTheme.typography.titleMedium)
    Text("Session ID: ${record?.id ?: "--"}")
    if (record == null) return
    fun date(value: Long?) = value?.let { dateFormat.format(Instant.ofEpochMilli(it)) } ?: "--"
    fun number(value: Double?) = value?.let { String.format(Locale.ENGLISH, "%.2f", it) } ?: "--"
    fun speed(value: Double?) = "${number(value?.times(3.6))} km/h"
    Text("Start requested: ${date(record.startRequestedAt)}")
    Text("Running started: ${date(record.startedAt)}")
    Text("Ended: ${date(record.endedAt)}")
    Text("Running duration: ${record.durationMs} ms")
    Text("Session device: ${record.device?.name ?: "--"} (${record.device?.deviceId ?: "--"})")
    Text("Eligible for saving: ${if (record.eligibleForSaving) "Yes" else "No"}")
    Text("Summary: ${if (record.endedAt != null) "Frozen" else "Live"}")
    Text("End reason: ${record.endReason ?: "--"}")
    val summary = record.summary
    Text("HR min / max / mean: ${summary.minimumHr ?: "--"} / ${summary.maximumHr ?: "--"} / ${number(summary.meanHr)} bpm")
    Text("Valid HR samples: ${summary.validHrCount}; valid HR received: ${summary.receivedValidHr}")
    HeartRateZone.entries.forEachIndexed { index, zone ->
        Text("${zone.label}: ${summary.zoneDurationsMs[index]} ms")
    }
    Text("Unclassified: ${summary.unclassifiedMs} ms")
    Text("Total steps: ${summary.totalSteps ?: "--"}")
    Text("Mean cadence: ${number(summary.meanCadence)} steps/min")
    Text("Min cadence: ${number(summary.minimumCadence)} steps/min")
    Text("Max cadence: ${number(summary.maximumCadence)} steps/min")
    val acc = record.streams.getValue(ACC)
    if (!acc.received) Text("No ACC observations. Cadence statistics are unavailable.")
    else {
        if (record.durationMs == 0L) Text("Mean cadence needs a positive Running duration.")
        if (summary.minimumCadence == null) Text("No qualifying five-second ACC window yet.")
        if (acc.missing || acc.failed) Text("Incomplete ACC data. Mean cadence includes missing time and may be lower.")
    }
    Text("Estimated distance: ${number(summary.distanceMetres)} m")
    Text("Mean / maximum estimated speed: ${speed(summary.meanSpeedMetresPerSecond)} / ${speed(summary.maximumSpeedMetresPerSecond)}")
    record.streams.forEach { (type, observation) ->
        Text("$type received: ${observation.received}; known missing: ${observation.missing}; failed: ${observation.failed}")
    }
    Text("Incomplete: ${record.incomplete}")
}
