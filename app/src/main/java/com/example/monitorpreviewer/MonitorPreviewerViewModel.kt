package com.example.monitorpreviewer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MonitorPreviewerViewModel : ViewModel() {
    // Private mutable state for internal updates
    private val _uiState = MutableStateFlow(MonitorPreviewerUiState())
    // Public immutable state for UI observation
    val uiState: StateFlow<MonitorPreviewerUiState> = _uiState.asStateFlow()

    // ============== Surface Operations ==============
    // Update the size of DeskSurface
    fun updateDeskSurface(width: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                desk = currentState.desk.copy(
                    width = width,
                )
            )
        }
    }

    // ============== Monitor Operations ==============
    // Add a new monitor at the center of Canvas
    fun addMonitor(width: Float, height: Float, name: String) {
        val newMonitor = Monitor(
            name = name,
            width = width,
            height = height,
            x = 0f,
            y = 0f,
            rotation = 0f,
            isSelected = false
        )

        _uiState.update { currentState ->
            currentState.copy(
                monitors = currentState.monitors + newMonitor
            )
        }
    }

    // Select a monitor by id
    fun selectMonitor(id: String) {
        _uiState.update { currentState ->
            currentState.copy(
                monitors = currentState.monitors.map { monitor ->
                    monitor.copy(isSelected = monitor.id == id)
                }
            )
        }
    }

    // Remove selected monitor
    fun removeSelectedMonitor() {
        _uiState.update { currentState ->
            currentState.copy(
                monitors = currentState.monitors.filter {!it.isSelected}
            )
        }
    }

    // Deselect all monitors
    fun deselectAllMonitors() {
        _uiState.update { currentState ->
            currentState.copy(
                monitors = currentState.monitors.map {it.copy(isSelected = false)}
            )
        }
    }

    // Rotate monitor 90°
    fun rotateSelectMonitor() {
        _uiState.update { currentState ->
            currentState.copy(
                monitors = currentState.monitors.map { monitor ->
                    if (monitor.isSelected) {
                        val newRotation = if (monitor.rotation % 180f == 0f) 90f else 0f
                        monitor.copy(rotation = newRotation)
                    } else {
                        monitor
                    }
                }
            )
        }
    }

    // Move monitor by dx, dy
    fun moveMonitorByID(id: String, dx: Float, dy: Float) {
        _uiState.update { state ->
            val updatedMonitors = state.monitors.map { monitor ->
                if (monitor.id == id) {
                    val newX = monitor.x + dx
                    val newY = monitor.y + dy

                    // Set the drag limit for monitors
                    val limitedX = newX.coerceIn(-1000f, 1000f)
                    val limitedY = newY.coerceIn(-800f, 100f)

                    monitor.copy(x = limitedX, y = limitedY)
                } else {
                    monitor
                }
            }

            state.copy(monitors = updatedMonitors)
        }
    }

    // ============== Canvas Operations ==============
    // Update canvas scale
    fun updateScale(scale: Float) {
        _uiState.update { currentState ->
            val newScale = (currentState.scale * scale).coerceIn(0.5f, 1.5f)
            currentState.copy(scale = newScale)
        }
    }

    // Update canvas offset
    fun updateOffset(dx: Float, dy: Float) {
        _uiState.update { currentState ->
            val newOffsetX = currentState.offsetX + dx
            val newOffsetY = currentState.offsetY + dy

            // Set the drag limit for canvas
            val limit = 600f / currentState.scale  // Apply canvas scale to the limitation

            val limitedX = newOffsetX.coerceIn(-limit, limit)
            val limitedY = newOffsetY.coerceIn(-limit, limit)

            currentState.copy(
                offsetX = limitedX,
                offsetY = limitedY
            )
        }
    }

    // Reset canvas to default view
    fun resetView() {
        _uiState.update { currentState ->
            currentState.copy(
                scale = 1f,
                offsetX = 0f,
                offsetY = 0f
            )
        }
    }
}

