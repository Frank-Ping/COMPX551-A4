package com.example.polarh10activityviewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel

// Retain the SDK across rotation without retaining an Activity.
class SensorViewModel(application: Application) : AndroidViewModel(application) {
    val bleManager = PolarBleManager(application)

    override fun onCleared() {
        bleManager.release()
    }
}
