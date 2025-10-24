package com.example.monitorpreviewer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.monitorpreviewer.MonitorPreviewerViewModel
import com.example.monitorpreviewer.R

@Composable
fun FloatingMenu(
    viewModel: MonitorPreviewerViewModel,
    onDeleteMonitor: () -> Unit,
    onRotateMonitor: () -> Unit,
    onSwapMonitor: () -> Unit,
    onAddMonitor: () -> Unit,
    onDeskSize: () -> Unit,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onResetView: () -> Unit,
    onAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Main menu toggle button
        FloatingActionButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Menu,
                contentDescription = "Toggle Menu"
            )
        }

        // Floating Menu Items
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top)
            ) {
                // If there is at least one selected monitor
                if (uiState.monitors.any { it.isSelected }) {

                    // Delete selected monitor
                    SmallFloatingActionButton(onClick = {
                        onDeleteMonitor()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Monitor")
                    }

                    // Rotate selected monitor
                    SmallFloatingActionButton(onClick = {
                        onRotateMonitor()
                    }) {
                        Icon(painterResource(id = R.drawable.outline_rotate_90_degrees_cw_24), contentDescription = "Rotate Monitor")
                    }

                    // Swap to another monitor size
                    SmallFloatingActionButton(onClick = {
                        onSwapMonitor()
                    }) {
                        Icon(painterResource(id = R.drawable.outline_change_circle_24), contentDescription = "Replace Monitor")
                    }
                } else {

                    // If there are no monitor selected
                    // Add a new monitor
                    SmallFloatingActionButton(onClick = {
                        onAddMonitor()
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Monitor")
                    }

                    // Change Desk Surface size
                    SmallFloatingActionButton(onClick = {
                        onDeskSize()
                    }) {
                        Icon(painterResource(id = R.drawable.outline_desk_24), contentDescription = "Desk Size")
                    }

                    // Zoom in the whole canvas
                    SmallFloatingActionButton(onClick = {
                        onZoomIn()
                    }) {
                        Icon(painterResource(id = R.drawable.outline_zoom_in_24), contentDescription = "Zoom In")
                    }

                    // Zoom out the whole canvas
                    SmallFloatingActionButton(onClick = {
                        onZoomOut()
                    }) {
                        Icon(painterResource(id = R.drawable.outline_zoom_out_24), contentDescription = "Zoom Out")
                    }

                    // Reset the canvas
                    SmallFloatingActionButton(onClick = {
                        onResetView()
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset View")
                    }

                    // About dialog
                    SmallFloatingActionButton(onClick = {
                        onAbout()
                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "Reset View")
                    }
                }
            }
        }
    }
}

