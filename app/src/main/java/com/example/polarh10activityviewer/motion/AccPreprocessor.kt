package com.example.polarh10activityviewer.motion

import com.example.polarh10activityviewer.sensor.AccSample

import kotlin.math.sqrt

internal data class PreparedAcc(
    val timeStamp: Long,
    val magnitude: Double,
    val smoothed: Double?,
    val previousMean: Double?,
    val previousStdDev: Double?
)

// Values are in m/s²; timestamps remain sensor nanoseconds.
internal class AccPreprocessor {
    private val magnitudes = ArrayDeque<Double>()
    private val smoothedWindow = ArrayDeque<Double>()
    var warmupEndedAt: Long? = null
        private set
    var latest: PreparedAcc? = null
        private set

    fun clear() {
        magnitudes.clear()
        smoothedWindow.clear()
        warmupEndedAt = null
        latest = null
    }

    fun receive(sample: AccSample): PreparedAcc {
        if (sample.gapBeforeNs != null) clear()
        val ax = sample.x * 0.0098
        val ay = sample.y * 0.0098
        val az = sample.z * 0.0098
        val magnitude = sqrt(ax * ax + ay * ay + az * az)
        magnitudes.addLast(magnitude)
        if (magnitudes.size > 5) magnitudes.removeFirst()
        val smoothed = if (magnitudes.size == 5) magnitudes.average() else null
        val mean = if (smoothedWindow.size == 100) smoothedWindow.average() else null
        val stdDev = mean?.let { average ->
            sqrt(smoothedWindow.sumOf { value -> (value - average) * (value - average) } / 100)
        }
        // Capture the preceding window before inserting this point.
        val result = PreparedAcc(sample.timeStamp, magnitude, smoothed, mean, stdDev)
        if (smoothed != null) {
            smoothedWindow.addLast(smoothed)
            if (smoothedWindow.size > 100) smoothedWindow.removeFirst()
            if (smoothedWindow.size == 100 && warmupEndedAt == null) warmupEndedAt = sample.timeStamp
        }
        latest = result
        return result
    }
}
