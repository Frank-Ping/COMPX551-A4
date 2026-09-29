package com.example.polarh10activityviewer

// Evict using actual sensor time only; display-time estimates never alter stored peaks.
internal class CadenceWindow {
    private val peaks = ArrayDeque<StepCandidate>()
    private var lastPeak: Long? = null

    fun clear() {
        peaks.clear()
        lastPeak = null
    }

    fun receive(committed: List<StepCandidate>, sensorTime: Long) {
        committed.forEach { peaks.addLast(it); lastPeak = it.timeStamp }
        while (peaks.isNotEmpty() && peaks.first().timeStamp <= sensorTime - 5_000_000_000L) peaks.removeFirst()
    }

    fun value(time: Long, warmupEndedAt: Long): Double {
        val last = lastPeak ?: return 0.0
        if (time - last >= 2_000_000_000L) return 0.0
        val duration = minOf(time - warmupEndedAt, 5_000_000_000L)
        if (duration <= 0) return 0.0
        val count = peaks.count { it.timeStamp > time - duration && it.timeStamp <= time }
        return 60_000_000_000.0 * count / duration
    }

    fun speed(time: Long, warmupEndedAt: Long): Double {
        val last = lastPeak ?: return 0.0
        if (time - last >= 2_000_000_000L) return 0.0
        val duration = minOf(time - warmupEndedAt, 5_000_000_000L)
        if (duration <= 0) return 0.0
        return distance(time, duration) * 1_000_000_000.0 / duration
    }

    // Raw full-window statistics deliberately exclude display-time zeroing.
    fun rawCadence(time: Long): Double =
        12.0 * peaks.count { it.timeStamp > time - 5_000_000_000L && it.timeStamp <= time }

    fun rawSpeed(time: Long): Double = distance(time, 5_000_000_000L) / 5.0

    private fun distance(time: Long, duration: Long): Double = peaks.sumOf {
        if (it.timeStamp > time - duration && it.timeStamp <= time) it.length ?: 0.0 else 0.0
    }
}
