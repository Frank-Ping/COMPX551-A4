package com.example.polarh10activityviewer.motion

import kotlin.math.pow

// A segment expires two seconds after its accepted peak; retain both endpoints.
internal class StrideLengthEstimator {
    private data class Point(val time: Long, val value: Double)
    private val samples = ArrayDeque<Point>()
    private var previousPeak: Long? = null

    fun clear() {
        samples.clear()
        previousPeak = null
    }

    fun receive(sample: PreparedAcc) {
        val value = sample.smoothed ?: return
        samples.addLast(Point(sample.timeStamp, value))
        while (samples.isNotEmpty() &&
            (samples.first().time < sample.timeStamp - 2_000_000_000L || samples.size > 201)) {
            samples.removeFirst()
        }
    }

    fun accept(peakTime: Long): Double? {
        val length = previousPeak?.let { start ->
            val interval = samples.filter { it.time in start..peakTime }
            0.5 * (interval.maxOf { it.value } - interval.minOf { it.value }).pow(0.25)
        }
        previousPeak = peakTime
        // Keep the later peak and samples after it for the following interval.
        while (samples.isNotEmpty() && samples.first().time < peakTime) samples.removeFirst()
        return length
    }
}
