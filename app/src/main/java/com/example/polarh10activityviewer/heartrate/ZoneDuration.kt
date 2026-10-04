package com.example.polarh10activityviewer.heartrate

internal fun formatZoneDuration(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    return "${(seconds / 60).toString().padStart(2, '0')}:${(seconds % 60).toString().padStart(2, '0')}"
}
