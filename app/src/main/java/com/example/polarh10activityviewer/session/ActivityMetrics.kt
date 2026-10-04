package com.example.polarh10activityviewer.session

import kotlin.math.sqrt
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType

internal data class ActivityMetrics(
    val intensity: Double? = null,
    val cardioLoad: Double? = null,
    val cadenceCvPercent: Double? = null,
    val cadencePointCount: Int? = null,
    val algorithmVersion: Int? = null,
    val sessionStrain: Double? = null,
    val sessionStrainScore: Double? = null
)

internal object ActivityMetricsCalculator {
    const val VERSION = 2
    const val MIN_CADENCE_POINTS = 30

    fun calculate(snapshot: SessionSnapshot): ActivityMetrics {
        val record = snapshot.record
        if (record.collectionIncomplete) return ActivityMetrics()
        val zones = record.summary.zoneDurationsMs
        val classified = zones.sum().toDouble()
        val weighted = zones.mapIndexed { index, ms -> ms.toDouble() * (index + 1) }.sum()
        // Keep the final record of each second, including a final missing/zero value.
        val values = snapshot.motionPoints.asSequence()
            .filter { it.sessionId == record.id && it.elapsedMs in 0..record.durationMs }
            .associateBy { it.secondBucket }.values.mapNotNull { it.cadence }
            .filter { it.isFinite() && it > 0.0 }
        val rawStrain = strain(record)
        return ActivityMetrics(
            intensity = if (classified > 0) weighted / classified else null,
            cardioLoad = if (classified > 0) weighted / 60_000.0 else null,
            cadenceCvPercent = if (values.size >= MIN_CADENCE_POINTS) populationCv(values) else null,
            cadencePointCount = values.size,
            algorithmVersion = VERSION,
            sessionStrain = rawStrain,
            sessionStrainScore = strainScore(rawStrain)
        )
    }

    // Welford's recurrence describes all selected observations, using population variance.
    internal fun populationCv(values: List<Double>): Double? {
        if (values.isEmpty()) return null
        var count = 0
        var mean = 0.0
        var m2 = 0.0
        values.forEach { value ->
            require(value.isFinite() && value > 0)
            count++
            val delta = value - mean
            mean += delta / count
            m2 += delta * (value - mean)
        }
        return sqrt((m2 / count).coerceAtLeast(0.0)) / mean * 100.0
    }

    fun strainScore(rawStrain: Double?): Double? =
        rawStrain?.let { 100.0 * (it / (it + 100.0)) }

    fun strain(record: SessionRecord): Double? {
        if (record.collectionIncomplete || record.durationMs <= 0) return null
        val acc = record.streams[PolarDeviceDataType.ACC] ?: return null
        if (!acc.received || acc.missing || acc.failed) return null
        val cadence = record.summary.meanCadence ?: return null
        if (!cadence.isFinite() || cadence < 0) return null
        val zones = record.summary.zoneDurationsMs
        if (zones.sum() <= 0) return null
        // Fixed app scoring parameters, not physiological thresholds or target cadence.
        val heartLoad = zones.mapIndexed { index, ms ->
            ms / 60_000.0 * (index + 1) * (index + 1)
        }.sum()
        val cadenceLevel = 5.0 * (cadence / 180.0).coerceIn(0.0, 1.0)
        val motionLoad = record.durationMs / 60_000.0 * cadenceLevel * cadenceLevel
        return 0.7 * heartLoad + 0.3 * motionLoad
    }
}

internal fun SessionSnapshot.withActivityMetrics(): SessionSnapshot = copy(record = record.copy(
    summary = record.summary.copy(activityMetrics = ActivityMetricsCalculator.calculate(this))
))
