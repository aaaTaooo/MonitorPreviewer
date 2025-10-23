package com.example.monitorpreviewer

data class MonitorPreviewerUiState(
    val desk: DeskSurface = DeskSurface(),            // Current desk configuration
    val monitors: List<Monitor> = emptyList(),        // List of monitors on desk
    val scale: Float = 1f,                            // Canvas zoom scale
    val offsetX: Float = 0f,                          // Canvas pan offset X
    val offsetY: Float = 0f,                          // Canvas pan offset Y
    val gridSize: Float = 50f,                        // Grid cell size in pixels
)
