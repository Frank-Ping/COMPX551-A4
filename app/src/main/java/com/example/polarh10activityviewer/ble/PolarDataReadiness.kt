package com.example.polarh10activityviewer.ble

import androidx.annotation.MainThread
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.PolarBleApi.PolarBleSdkFeature
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType
import com.polar.sdk.api.model.PolarSensorSetting
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@MainThread
internal class PolarDataReadiness(
    private val readinessScope: CoroutineScope,
    private val readinessMatches: (PolarBleApi, String) -> Boolean,
    private val bluetoothAvailableForData: () -> Boolean,
    private val onlineStreamActive: () -> Boolean
) {
    private var readinessJob: Job? = null
    private var readinessGeneration = 0
    private val readyFeatures = mutableSetOf<PolarBleSdkFeature>()
    private val unavailableFeatures = mutableSetOf<PolarBleSdkFeature>()
    private val mutableDataReadiness = MutableStateFlow(checkedDataTypes.associateWith { DataReadiness() })
    val state = mutableDataReadiness.asStateFlow()
    val checking: Boolean get() = readinessJob != null
    private val streamSettingsMutex = Mutex()

    fun checkFeature(source: PolarBleApi, identifier: String, type: PolarDeviceDataType) {
        val feature = if (type == PolarDeviceDataType.HR) PolarBleSdkFeature.FEATURE_HR
            else PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING
        if (!readyFeatures.confirmReadiness(feature) { source.isFeatureReady(identifier, feature) }) {
            setDataReadiness(type, DataReadiness(DataReadinessStatus.WAITING))
            error("$type feature is not ready. Retry when available.")
        }
        if (type == PolarDeviceDataType.HR) {
            setDataReadiness(type, DataReadiness(DataReadinessStatus.READY, configurationComplete = true))
        }
    }

    // Serialize fresh ACC/ECG settings queries, not the lifetime of their data streams.
    suspend fun currentStreamSettings(
        source: PolarBleApi, identifier: String, type: PolarDeviceDataType
    ): PolarSensorSetting = streamSettingsMutex.withLock {
        ensureCurrentConnection(source, identifier, type)
        checkFeature(source, identifier, type)
        val supported = source.getAvailableOnlineStreamDataTypes(identifier)
        ensureCurrentConnection(source, identifier, type)
        if (type !in supported) {
            setDataReadiness(type, DataReadiness(DataReadinessStatus.UNSUPPORTED))
            error("$type is unavailable for online streaming.")
        }
        val settings = source.requestStreamSettings(identifier, type)
        ensureCurrentConnection(source, identifier, type)
        val checked = checkedSettings(type, settings.settings)
        setDataReadiness(type, checked)
        check(checked.configurationComplete) {
            checked.error ?: "$type settings need confirmation. Confirm the displayed options before starting."
        }
        PolarSensorSetting(checked.selected)
    }

    private suspend fun ensureCurrentConnection(
        source: PolarBleApi, identifier: String, type: PolarDeviceDataType
    ) {
        currentCoroutineContext().ensureActive()
        if (!readinessMatches(source, identifier) || !bluetoothAvailableForData()) {
            throw CancellationException("$type connection is no longer current.")
        }
    }

    private fun setDataReadiness(type: PolarDeviceDataType, state: DataReadiness) {
        mutableDataReadiness.value = mutableDataReadiness.value + (type to state)
    }

    fun acceptReadiness(
        source: PolarBleApi, identifier: String,
        ready: List<PolarBleSdkFeature>, unavailable: List<PolarBleSdkFeature>
    ) {
        if (!readinessMatches(source, identifier)) return
        val online = PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING
        val onlineWasReady = online in readyFeatures
        readyFeatures.addAll(ready)
        unavailableFeatures.addAll(unavailable)
        unavailableFeatures.removeAll(readyFeatures)
        val hr = PolarBleSdkFeature.FEATURE_HR
        if (hr in readyFeatures) {
            setDataReadiness(PolarDeviceDataType.HR, DataReadiness(DataReadinessStatus.READY, configurationComplete = true))
        } else if (hr in unavailableFeatures) {
            setDataReadiness(PolarDeviceDataType.HR, DataReadiness(DataReadinessStatus.UNSUPPORTED))
        }
        if (online in readyFeatures && !onlineWasReady) queryStreamSettings(source, identifier)
        else if (online in unavailableFeatures) {
            listOf(PolarDeviceDataType.ACC, PolarDeviceDataType.ECG).forEach {
                setDataReadiness(it, DataReadiness(DataReadinessStatus.UNSUPPORTED))
            }
        }
        // Features absent from both callback lists remain unresolved, not unsupported.
    }

    fun recheckDataReadiness(source: PolarBleApi, identifier: String) {
        if (readinessJob != null || onlineStreamActive()) return
        if (!readinessMatches(source, identifier)) return
        for (feature in listOf(PolarBleSdkFeature.FEATURE_HR, PolarBleSdkFeature.FEATURE_POLAR_ONLINE_STREAMING)) {
            val types = if (feature == PolarBleSdkFeature.FEATURE_HR) listOf(PolarDeviceDataType.HR)
                else listOf(PolarDeviceDataType.ACC, PolarDeviceDataType.ECG)
            try {
                if (readyFeatures.confirmReadiness(feature) { source.isFeatureReady(identifier, feature) }) {
                    unavailableFeatures.remove(feature)
                    if (feature == PolarBleSdkFeature.FEATURE_HR) {
                        setDataReadiness(PolarDeviceDataType.HR, DataReadiness(DataReadinessStatus.READY, configurationComplete = true))
                    } else queryStreamSettings(source, identifier)
                } else {
                    readyFeatures.remove(feature)
                    types.forEach { setDataReadiness(it, DataReadiness(
                        if (feature in unavailableFeatures) DataReadinessStatus.UNSUPPORTED else DataReadinessStatus.WAITING
                    )) }
                }
            } catch (error: Exception) {
                readyFeatures.remove(feature)
                types.forEach { setDataReadiness(it, DataReadiness(DataReadinessStatus.FAILED,
                    error = "Readiness check failed (${error.javaClass.simpleName}). Recheck to retry.")) }
            }
        }
    }

    private fun queryStreamSettings(source: PolarBleApi, identifier: String) {
        if (readinessJob != null || onlineStreamActive() ||
            !readinessMatches(source, identifier)) return
        val generation = readinessGeneration
        val types = listOf(PolarDeviceDataType.ACC, PolarDeviceDataType.ECG)
        types.forEach { setDataReadiness(it, DataReadiness(DataReadinessStatus.CHECKING)) }
        fun current() = generation == readinessGeneration && readinessMatches(source, identifier)
        readinessJob = readinessScope.launch(start = CoroutineStart.LAZY) {
            try {
                val supported = source.getAvailableOnlineStreamDataTypes(identifier)
                ensureActive()
                if (!current()) return@launch
                for (type in types) {
                    if (type !in supported) {
                        setDataReadiness(type, DataReadiness(DataReadinessStatus.UNSUPPORTED))
                        continue
                    }
                    try {
                        val settings = source.requestStreamSettings(identifier, type)
                        ensureActive()
                        if (!current()) return@launch
                        setDataReadiness(type, checkedSettings(type, settings.settings))
                    } catch (cancelled: CancellationException) {
                        throw cancelled
                    } catch (error: Exception) {
                        if (current()) setDataReadiness(type, DataReadiness(DataReadinessStatus.FAILED,
                            error = "Settings check failed (${error.javaClass.simpleName}). Recheck to retry."))
                    }
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                if (current()) types.forEach { setDataReadiness(it, DataReadiness(DataReadinessStatus.FAILED,
                    error = "Data type query failed (${error.javaClass.simpleName}). Recheck to retry.")) }
            } finally {
                if (generation == readinessGeneration) {
                    readinessJob = null
                    // Cancellation while still connected must not leave the recheck button blocked.
                    if (current()) types.filter { mutableDataReadiness.value[it]?.status == DataReadinessStatus.CHECKING }
                        .forEach { setDataReadiness(it, DataReadiness(DataReadinessStatus.WAITING)) }
                }
            }
        }
        readinessJob?.start()
    }

    fun clear(status: DataReadinessStatus = DataReadinessStatus.DISCONNECTED) {
        readinessGeneration++
        readinessJob?.cancel()
        readinessJob = null
        readyFeatures.clear()
        unavailableFeatures.clear()
        mutableDataReadiness.value = checkedDataTypes.associateWith { DataReadiness(status) }
    }
}
