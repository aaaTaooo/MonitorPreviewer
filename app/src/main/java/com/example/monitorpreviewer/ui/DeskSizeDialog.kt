package com.example.monitorpreviewer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.monitorpreviewer.DeskSurfacePresets

@Composable
fun DeskSizeDialog(
    currentWidth: Float,
    onConfirm: (width: Float) -> Unit,
    onDismiss: () -> Unit
) {
    var widthInput by remember { mutableStateOf("") }
    var showCustomInput by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Desk Surface Size") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (!showCustomInput) {
                    // Show presets
                    Text("Select Preset Desk Surface Size:")

                    DeskSurfacePresets.presets.forEach { preset ->
                        OutlinedButton(
                            onClick = {
                                onConfirm(preset.width)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(preset.name)
                        }
                    }

                    // Custom button
                    OutlinedButton(
                        onClick = { showCustomInput = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Custom Size")
                    }
                } else {
                    // Show custom input
                    Text("Enter custom desk surface size:")

                    // Custom width
                    OutlinedTextField(
                        value = widthInput,
                        onValueChange = { widthInput = it },
                        label = { Text("Width (cm)")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Back to choose presets
                    OutlinedButton(
                        onClick = { showCustomInput = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back to Presets")
                    }
                }
            }
        },
        confirmButton = {
            if (showCustomInput) {
                Button(
                    onClick = {
                        // Convert input to display format cm
                        // Keep the origin width if input is null
                        val width = (widthInput.toFloatOrNull()?.times(10)) ?: currentWidth
                        if (width > 0) {
                            onConfirm(width)
                        }
                    }
                ) {
                    Text("Confirm")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}



