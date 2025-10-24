 package com.example.monitorpreviewer.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import com.example.monitorpreviewer.DeskSurface
import com.example.monitorpreviewer.Monitor


// Draw grid background with lines
fun DrawScope.drawGrid(gridSize: Float, scale: Float, offsetX: Float, offsetY: Float) {
    val width = size.width
    val height = size.height

    // Apply canvas scale and offset
    val scaledGrid = gridSize * scale
    val startX = (-offsetX * scale) % scaledGrid
    val startY = (-offsetY * scale) % scaledGrid

    // Vertical line
    var x = startX
    while (x < width) {
        drawLine(
            color = Color.LightGray.copy(alpha = 0.5f),
            start = Offset(x, 0f),
            end = Offset(x, height),
            strokeWidth = 1f
        )
        x += scaledGrid
    }

    // Horizontal line
    var y = startY
    while (y < height) {
        drawLine(
            color = Color.LightGray.copy(alpha = 0.5f),
            start = Offset(0f, y),
            end = Offset(width, y),
            strokeWidth = 1f
        )
        y += scaledGrid
    }
}

// Draw table surface on canvas
fun DrawScope.drawDesk(
    desk: DeskSurface,
    centerX: Float,
    centerY: Float,
) {
    val deskWidth = desk.width
    val deskHeight = desk.height

    // Calculate center position
    val left = centerX - deskWidth / 2
    val top = centerY + 250

    // Draw desk as a filled rectangle with border
    drawRect(
        color = Color(0xFFD4A574),
        topLeft = Offset(left, top),
        size = Size(deskWidth, deskHeight)
    )

    // Draw desk border
    drawRect(
        color = Color(0xFF8B6F47), // Darker wood for border
        topLeft = Offset(left, top),
        size = Size(deskWidth, deskHeight),
        style = Stroke(width = 10f)
    )
}

// Draw a monitor on canvas
fun DrawScope.drawMonitor(
    monitor: Monitor,
    centerX: Float,
    centerY: Float,
) {
    val monitorWidth = monitor.width
    val monitorHeight = monitor.height

    val monitorCenterX = centerX + monitor.x
    val monitorCenterY = centerY + monitor.y

    rotate(degrees = monitor.rotation, pivot = Offset(monitorCenterX, monitorCenterY)) {
        val left = monitorCenterX - monitorWidth / 2
        val top = monitorCenterY - monitorHeight / 2

        // Draw monitor Screen
        drawRect(
            color = Color.Black.copy(alpha = 0.8f),
            topLeft = Offset(left, top),
            size = Size(monitorWidth, monitorHeight)
        )

        // Draw monitor border
        drawRect(
            color = Color.DarkGray,
            topLeft = Offset(left - 5, top - 5),
            size = Size(monitorWidth + 10, monitorHeight + 10),
            style = Stroke(width = 8f)
        )

        // Draw selection highlight if selected
        if (monitor.isSelected) {
            drawRect(
                color = Color.Cyan,
                topLeft = Offset(left - 8, top - 8),
                size = Size(monitorWidth + 16, monitorHeight + 16),
                style = Stroke(width = 10f)
            )
        }
    }
}

// Draw scale indicator
fun DrawScope.drawIndicator(x: Float, y: Float) {
    val indicatorLength = 1000f

    drawLine(
        color = Color.Black,
        start = Offset(x, y),
        end = Offset(x + indicatorLength, y),
        strokeWidth = 1f
    )

    for (i in 0 .. indicatorLength.toInt()) {
        val dotHeight = if (i % 10 == 0) 20f else 10f

        drawLine(
            color = Color.Black,
            start = Offset(x + i, y),
            end = Offset(x + i, y - dotHeight),
            strokeWidth = 1f
        )
    }

    // Add a 100cm comment to indicator
    drawContext.canvas.nativeCanvas.apply {
        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 30f
        }
        drawText("100cm", x + indicatorLength + 10, y + 5, paint)
    }
}

// Check if a touch point is inside a monitor
fun isPointInMonitor(
    touchX: Float,
    touchY: Float,
    monitor: Monitor,
    centerX: Float,
    centerY: Float
): Boolean {
    // Calculate rotated size
    val monitorWidth = if (monitor.rotation % 180f == 0f) monitor.width else monitor.height
    val monitorHeight = if (monitor.rotation % 180f == 0f) monitor.height else monitor.width

    // Calculate monitor center
    val monitorCenterX = centerX + monitor.x
    val monitorCenterY = centerY + monitor.y

    // Monitor border
    val left = monitorCenterX - monitorWidth / 2
    val top = monitorCenterY - monitorHeight / 2
    val right = monitorCenterX + monitorWidth / 2
    val bottom = monitorCenterY + monitorHeight / 2

    return touchX in left..right && touchY in top..bottom
}




















