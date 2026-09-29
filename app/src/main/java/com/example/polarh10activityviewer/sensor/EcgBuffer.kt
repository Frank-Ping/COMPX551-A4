package com.example.polarh10activityviewer.sensor

import com.example.polarh10activityviewer.ble.SubscriptionStatus

import androidx.annotation.MainThread
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.EcgSample
import com.polar.sdk.api.model.PolarEcgData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

// Other SDK sample types are not H10 voltage samples; empty batches are not reception.
internal fun Flow<PolarEcgData>.h10EcgSamples(): Flow<List<EcgSample>> =
    map { it.samples.filterIsInstance<EcgSample>() }.filter { it.isNotEmpty() }

@MainThread
internal class EcgBuffer {
    private val buffer = ArrayDeque<EcgSample>()
    private val mutableSamples = MutableStateFlow<List<EcgSample>>(emptyList())
    val samples = mutableSamples.asStateFlow()

    fun onSubscriptionState(type: PolarDeviceDataType, status: SubscriptionStatus) {
        if (type == PolarDeviceDataType.ECG && status == SubscriptionStatus.STARTING) {
            clear()
        }
    }

    fun clear() {
        buffer.clear()
        mutableSamples.value = emptyList()
    }

    fun receive(samples: List<EcgSample>) {
        samples.forEach { sample ->
            // Retain the SDK's signed microvolt value and original nanosecond timestamp.
            buffer.addLast(sample)
            val cutoff = sample.timeStamp - 10_000_000_000L
            while (buffer.isNotEmpty() && (buffer.first().timeStamp <= cutoff || buffer.size > 1_300)) {
                buffer.removeFirst()
            }
        }
        if (samples.isNotEmpty()) mutableSamples.value = buffer.toList()
    }
}
