package com.example.monitorpreviewer

data class DeskSurface(
    val width: Float = 1200f,      // Default Desk surface 120cm width
    val height: Float = 50f,       // Default Desk surface 5cm height
)

data class DeskSurfaceSize(
    val name: String,
    val width: Float,
    val height: Float
)

object DeskSurfacePresets {
    val presets = listOf(
        DeskSurfaceSize("Small table surface (100 * 5) cm", 1000f, 50f),
        DeskSurfaceSize("Medium table surface (120 * 5) cm", 1200f, 50f),
        DeskSurfaceSize("Large table surface (160 * 5) cm", 1600f, 50f),
        DeskSurfaceSize("Extra large table surface (200 * 5) cm", 2000f, 50f),
    )
}