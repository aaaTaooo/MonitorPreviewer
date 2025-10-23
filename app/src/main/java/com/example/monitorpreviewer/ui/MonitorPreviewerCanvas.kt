package com.example.monitorpreviewer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import com.example.monitorpreviewer.MonitorPreviewerViewModel

@Composable
fun MonitorPreviewerCanvas(viewModel: MonitorPreviewerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var draggedMonitorId by remember { mutableStateOf<String?>(null) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(uiState.monitors) {
                // Keep listening for pointer events
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()

                        val pointers = event.changes
                        if (pointers.isEmpty()) continue

                        // one finger operation (drag, select, deselect monitors)
                        if (pointers.size == 1) {
                            val pointer = pointers[0]
                            val pos = pointer.position

                            val pivotX = size.width / 2f
                            val pivotY = size.height / 2f

                            // Check if pressed to select monitor
                            if (pointer.pressed && draggedMonitorId == null) {
                                // Transform touch position to logical coordinates
                                val touchLogicalX = pivotX + (pos.x - pivotX) / uiState.scale
                                val touchLogicalY = pivotY + (pos.y - pivotY) / uiState.scale

                                // Calculate canvas center
                                val canvasCenterX = pivotX + uiState.offsetX
                                val canvasCenterY = pivotY + uiState.offsetY

                                // Select the top monitor in the touch point
                                val selectedMonitor = uiState.monitors.findLast { monitor ->
                                    isPointInMonitor(
                                        touchLogicalX,
                                        touchLogicalY,
                                        monitor,
                                        centerX = canvasCenterX,
                                        centerY = canvasCenterY
                                    )
                                }

                                // Select and drag the monitor
                                if (selectedMonitor != null) {
                                    viewModel.selectMonitor(selectedMonitor.id)
                                    draggedMonitorId = selectedMonitor.id
                                } else {
                                    viewModel.deselectAllMonitors()
                                }
                            }

                            // Drag the monitor if selected
                            if (pointer.pressed && draggedMonitorId != null) {
                                // Get the movement delta
                                val delta = pointer.positionChange()
                                // Transform movement
                                draggedMonitorId?.let { id ->
                                    viewModel.moveMonitorByID(
                                        id = id,
                                        dx = delta.x / uiState.scale,
                                        dy = delta.y / uiState.scale
                                    )
                                }
                                pointer.consume()
                            }

                            // Avoid misoperation
                            if (!pointer.pressed) {
                                draggedMonitorId = null
                            }
                        } else {
                            // Two fingers operations (Zoom, drag canvas)
                            val zoom = event.calculateZoom()
                            if (zoom != 1f) viewModel.updateScale(zoom)

                            val pan = event.calculatePan()
                            if (pan != Offset.Zero) {
                                // Convert screen pixel to logical units
                                viewModel.updateOffset(pan.x / uiState.scale, pan.y / uiState.scale)
                            }
                            draggedMonitorId = null
                        }
                    }
                }
            }
    ) {
        // Draw background grid
        drawGrid(uiState.gridSize, uiState.scale, uiState.offsetX, uiState.offsetY)

        // Apply canvas scale and offset
        scale(uiState.scale, pivot = Offset(size.width / 2, size.height / 2)) {
            // Calculate canvas center
            val centerX = size.width / 2 + uiState.offsetX
            val centerY = size.height / 2 + uiState.offsetY

            // Draw Desk Surface
            drawDesk(uiState.desk, centerX, centerY)

            // Draw monitors
            uiState.monitors.forEach { monitor ->
                drawMonitor(monitor, centerX, centerY)
            }

            // Draw Indicator
            drawIndicator(100f, size.height - 100f)
        }

    }
}