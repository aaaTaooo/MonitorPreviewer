package com.example.monitorpreviewer

import java.util.UUID

data class Monitor(
    val id: String = UUID.randomUUID().toString(),  //Create unique id for each monitor
    val name: String = "Monitor",
    val width: Float = 531.5f,      // Default 24" 16:9 in cm
    val height: Float = 299.0f,
    val x: Float = 0f,                  // Canvas position X
    val y: Float = 0f,                  // Canvas position Y
    val rotation: Float = 0f,           // Rotation in degrees
    val isSelected: Boolean = false     // Selection state
)

data class MonitorSize(
    val name: String,
    val width: Float,
    val height: Float
)

object MonitorPresets {
    val presets = listOf(
        MonitorSize("24\" (16:9)", 531.5f, 299.0f),
        MonitorSize("27\" (16:9)", 617.5f, 371.2f),
        MonitorSize("32\" (16:9)", 697.7f, 392.3f),
        MonitorSize("34\" Ultrawide (21:9)", 810.0f, 347.1f),
        MonitorSize("49\" Super Ultrawide (32:9)", 1194.0f, 369.0f),
    )
}