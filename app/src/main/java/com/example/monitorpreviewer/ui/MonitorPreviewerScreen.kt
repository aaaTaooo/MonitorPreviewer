package com.example.monitorpreviewer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.monitorpreviewer.MonitorPreviewerViewModel


@Composable
fun MonitorPreviewerScreen(
    viewModel: MonitorPreviewerViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var showMonitorSizeDialog by remember { mutableStateOf(false) }
    var showDeskSizeDialog by remember { mutableStateOf(false) }

    // Create a default monitor when the screen is first opened
    LaunchedEffect(Unit) {
        if (uiState.monitors.isEmpty()) {
            viewModel.addMonitor(
                width = 531.5f,
                height = 299.0f,
                name = "Default Monitor"
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Canvas
        MonitorPreviewerCanvas(viewModel = viewModel)

        // Floating Menu
        FloatingMenu(
            viewModel = viewModel,

            // Delete selected monitor
            onDeleteMonitor = {
                viewModel.removeSelectedMonitor()
            },

            // Rotate the selected monitor
            onRotateMonitor = {
                viewModel.rotateSelectMonitor()
            },

            // Swap the selected monitor with another size through dialog
            onSwapMonitor = {
                viewModel.removeSelectedMonitor()
                showMonitorSizeDialog = true
            },

            // Add a new monitor through dialog
            onAddMonitor = {
                showMonitorSizeDialog = true
            },

            // Change Desk Surface size through dialog
            onDeskSize = {
                showDeskSizeDialog = true
            },

            // Zoom in the whole canvas
            onZoomIn = {
                viewModel.updateScale(1.1f)
            },

            // Zoom out the whole canvas
            onZoomOut = {
                viewModel.updateScale(0.9f)
            },

            // Reset the canvas
            onResetView = {
                viewModel.resetView()
            },
            modifier = Modifier.align(Alignment.TopStart),
        )

        // Monitor size dialog
        if (showMonitorSizeDialog) {
            MonitorSizeDialog(
                onConfirm = { width, height, name ->
                    viewModel.addMonitor(width, height, name)
                    showMonitorSizeDialog = false
                },
                onDismiss = {
                    showMonitorSizeDialog = false
                }
            )
        }

        // Desk size dialog
        if (showDeskSizeDialog) {
            DeskSizeDialog(
                currentWidth = uiState.desk.width,
                //currentHeight = uiState.desk.height,
                onConfirm = { width ->
                    viewModel.updateDeskSurface(width)
                    showDeskSizeDialog = false
                },
                onDismiss = {
                    showDeskSizeDialog = false
                }
            )
        }
    }
}