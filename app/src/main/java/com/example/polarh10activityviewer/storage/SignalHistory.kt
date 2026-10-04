package com.example.polarh10activityviewer.storage

import com.example.polarh10activityviewer.session.HrHistoryPoint
import com.example.polarh10activityviewer.session.MotionHistoryPoint
import com.example.polarh10activityviewer.session.SessionRecord
import java.nio.ByteBuffer
import java.nio.ByteOrder

internal data class RawEcg(val timestamp: Long, val voltage: Int)
internal object EcgCodec {
    const val VERSION = 1
    fun encode(points: List<RawEcg>): ByteArray = ByteBuffer.allocate(points.size * 12)
        .order(ByteOrder.LITTLE_ENDIAN).apply { points.forEach { putLong(it.timestamp); putInt(it.voltage) } }.array()
    fun decode(bytes: ByteArray): List<RawEcg> {
        require(bytes.size % 12 == 0)
        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        return List(bytes.size / 12) { RawEcg(buffer.long, buffer.int) }
    }
}

internal data class EcgSegment(val index: Long, val anchorSensor: Long, val anchorElapsed: Long,
    val validStart: Long, val validEnd: Long, val rate: Int) {
    fun elapsed(timestamp: Long) = anchorElapsed + (timestamp - anchorSensor) / 1_000_000.0
}
internal data class EcgChunk(val index: Long, val segment: Long, val firstMs: Double, val lastMs: Double,
    val bytes: ByteArray)
internal data class SignalBatch(val record: SessionRecord, val ecg: List<EcgChunk>, val segments: List<EcgSegment>,
    val hr: List<HrHistoryPoint>, val motion: List<MotionHistoryPoint>) {
    val size: Int get() = 512 + ecg.sumOf { it.bytes.size + 64 } + segments.size * 64 +
        hr.size * 48 + motion.size * 56
}

// A short acquisition buffer; whole-session samples never live in Compose state.
internal class SignalBuffer {
    private val open = mutableListOf<RawEcg>()
    private val chunks = mutableListOf<EcgChunk>()
    private val segments = linkedMapOf<Long, EcgSegment>()
    private var current: EcgSegment? = null
    private var previousSensor: Long? = null
    private var bucket = 0L
    private var segmentIndex = 0L
    private var chunkIndex = 0L
    private var runningStart = 0L
    val bytes get() = open.size * 12 + chunks.sumOf { it.bytes.size + 64 } + segments.size * 64

    fun receiveEcg(samples: List<RawEcg>, elapsed: Long, rate: Int) {
        require(rate > 0)
        if (samples.isEmpty()) return
        samples.forEach { sample ->
            val broken = previousSensor?.let { sample.timestamp <= it ||
                (sample.timestamp - it).toDouble() * rate > 3_000_000_000.0 } ?: false
            if (broken) endEcg(elapsed)
            if (current == null) current = EcgSegment(++segmentIndex,
                if (broken) sample.timestamp else samples.last().timestamp, elapsed, runningStart, elapsed, rate)
            val segment = current!!.copy(validEnd = elapsed)
            current = segment
            segments[segment.index] = segment
            val nextBucket = Math.floorDiv(sample.timestamp, 1_000_000_000L)
            if (open.isNotEmpty() && (bucket != nextBucket || open.size >= rate)) closeChunk()
            bucket = nextBucket
            open.add(sample)
            previousSensor = sample.timestamp
        }
    }

    fun endEcg(elapsed: Long) {
        current?.let { segments[it.index] = it.copy(validEnd = elapsed) }
        closeChunk(); current = null; previousSensor = null
    }
    fun boundary(elapsed: Long) { endEcg(elapsed); runningStart = elapsed }
    private fun closeChunk() {
        if (open.isEmpty()) return
        val segment = checkNotNull(current)
        chunks.add(EcgChunk(++chunkIndex, segment.index, segment.elapsed(open.first().timestamp),
            segment.elapsed(open.last().timestamp), EcgCodec.encode(open)))
        open.clear()
    }
    fun take(record: SessionRecord, hr: List<HrHistoryPoint>, motion: List<MotionHistoryPoint>, flush: Boolean): SignalBatch {
        if (flush) closeChunk()
        current?.let { segments[it.index] = it.copy(validEnd = record.durationMs) }
        return SignalBatch(record, chunks.toList(), segments.values.toList(), hr, motion).also {
            chunks.clear(); segments.clear()
        }
    }
}
