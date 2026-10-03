package com.example.polarh10activityviewer

import android.app.Application
import com.example.polarh10activityviewer.ble.PolarBleManager

// Keep the paused session across Activity navigation while this process is alive.
class ActivityViewerApplication : Application() {
    val bleManager by lazy { PolarBleManager(this) }
}
