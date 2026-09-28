package com.example.polarh10activityviewer

import androidx.annotation.MainThread
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarAccelerometerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Raw mG values and the sensor timestamp in nanoseconds. A gap begins a new segment.
data class AccSample(
    val timeStamp: Long,
    val x: Int,
    val y: Int,
    val z: Int,
    val gapBeforeNs: Long? = null
)

@MainThread
internal class AccBuffer {
    private val buffer = ArrayDeque<AccSample>()
    private var previousTimeStamp: Long? = null
    private val mutableSamples = MutableStateFlow<List<AccSample>>(emptyList())
    val samples = mutableSamples.asStateFlow()

    fun onSubscriptionState(type: PolarDeviceDataType, status: SubscriptionStatus) {
        if (type == PolarDeviceDataType.ACC && status == SubscriptionStatus.STARTING) {
            clear()
        }
    }

    fun clear() {
        buffer.clear()
        previousTimeStamp = null
        mutableSamples.value = emptyList()
    }

    fun receive(batch: PolarAccelerometerData) {
        batch.samples.forEach { sample ->
            val gap = previousTimeStamp?.let { sample.timeStamp - it }?.takeIf { it > 30_000_000L }
            buffer.addLast(AccSample(sample.timeStamp, sample.x, sample.y, sample.z, gap))
            previousTimeStamp = sample.timeStamp
            val cutoff = sample.timeStamp - 10_000_000_000L
            while (buffer.isNotEmpty() && (buffer.first().timeStamp <= cutoff || buffer.size > 1_000)) {
                buffer.removeFirst()
            }
        }
        // Publish once per SDK batch; every sample has already been processed above.
        if (batch.samples.isNotEmpty()) mutableSamples.value = buffer.toList()
    }
}
