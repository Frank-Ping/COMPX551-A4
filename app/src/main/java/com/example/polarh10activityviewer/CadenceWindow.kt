package com.example.polarh10activityviewer

// Evict using actual sensor time only; display-time estimates never alter stored peaks.
internal class CadenceWindow {
    private val peaks = ArrayDeque<Long>()
    private var lastPeak: Long? = null

    fun clear() {
        peaks.clear()
        lastPeak = null
    }

    fun receive(committed: List<StepCandidate>, sensorTime: Long) {
        committed.forEach { peaks.addLast(it.timeStamp); lastPeak = it.timeStamp }
        while (peaks.isNotEmpty() && peaks.first() <= sensorTime - 5_000_000_000L) peaks.removeFirst()
    }

    fun value(time: Long, warmupEndedAt: Long): Double {
        val last = lastPeak ?: return 0.0
        if (time - last >= 2_000_000_000L) return 0.0
        val duration = minOf(time - warmupEndedAt, 5_000_000_000L)
        if (duration <= 0) return 0.0
        val count = peaks.count { it > time - duration && it <= time }
        return 60_000_000_000.0 * count / duration
    }
}
