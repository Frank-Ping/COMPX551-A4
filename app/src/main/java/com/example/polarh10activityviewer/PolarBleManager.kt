package com.example.polarh10activityviewer

import android.Manifest
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import com.polar.androidcommunications.api.ble.model.DisInfo
import com.polar.sdk.api.PolarBleApi
import com.polar.sdk.api.PolarBleApiCallback
import com.polar.sdk.api.PolarBleApiDefaultImpl
import com.polar.sdk.api.model.PolarHealthThermometerData

class PolarBleManager(context: Context, private val onBluetoothStateChanged: () -> Unit) {
    private val appContext = context.applicationContext
    private val mainHandler = Handler(Looper.getMainLooper())
    private var api: PolarBleApi? = null

    var initializationError: String? = null
        private set

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    fun initialize(retry: Boolean = false): Boolean {
        if (api != null) return true
        if (initializationError != null && !retry) return false
        initializationError = null

        return try {
            val created = PolarBleApiDefaultImpl.defaultImplementation(
                appContext,
                setOf(PolarBleApi.PolarBleSdkFeature.FEATURE_HR)
            )
            api = created
            created.setApiCallback(object : PolarBleApiCallback() {
                override fun blePowerStateChanged(powered: Boolean) {
                    // Re-read current system state on the main thread; ignore old SDK instances.
                    mainHandler.post {
                        if (api === created) onBluetoothStateChanged()
                    }
                }

                // Required by SDK 8.3.0; these features are not enabled in this step.
                override fun disInformationReceived(identifier: String, disInfo: DisInfo) = Unit
                override fun htsNotificationReceived(identifier: String, data: PolarHealthThermometerData) = Unit
            })
            true
        } catch (error: Exception) {
            release()
            initializationError = "SDK initialization failed (${error.javaClass.simpleName}). Please retry."
            Log.e("PolarBleManager", "SDK initialization failed", error)
            false
        }
    }

    fun release() {
        val previous = api
        api = null
        mainHandler.removeCallbacksAndMessages(null)
        try {
            previous?.shutDown()
        } catch (error: Exception) {
            Log.e("PolarBleManager", "SDK cleanup failed", error)
        }
        initializationError = null
    }
}
