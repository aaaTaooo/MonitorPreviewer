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
import com.example.monitorpreviewer.MonitorPresets

@Composable
fun MonitorSizeDialog(
    onConfirm: (width: Float, height: Float, name: String) -> Unit,
    onDismiss: () -> Unit
) {
    var showCustomInput by remember { mutableStateOf(false) }
    var widthInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Monitor") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (!showCustomInput) {
                    // Show preset options
                    Text("Select monitor size:")

                    MonitorPresets.presets.forEach { preset ->
                        OutlinedButton(
                            onClick = {
                                onConfirm(
                                    preset.width,
                                    preset.height,
                                    preset.name
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(preset.name)
                        }
                    }

                    OutlinedButton(
                        onClick = { showCustomInput = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Custom Size")
                    }
                } else {
                    // Show custom input fields
                    Text("Enter custom monitor size:")

                    // Custom monitor width
                    OutlinedTextField(
                        value = widthInput,
                        onValueChange = { widthInput = it },
                        label = { Text("Width (cm)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Custom monitor height
                    OutlinedTextField(
                        value = heightInput,
                        onValueChange = { heightInput = it },
                        label = { Text("Height (cm)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Back to choose presets
                    TextButton(
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
                        // if null set to default 24" monitor size
                        val width = (widthInput.toFloatOrNull()?.times(10)) ?: 531.5f
                        val height = (heightInput.toFloatOrNull()?.times(10)) ?: 299.0f
                        if (width > 0 && height > 0) {
                            onConfirm(width, height, nameInput)
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


