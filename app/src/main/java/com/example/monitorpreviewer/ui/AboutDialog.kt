package com.example.monitorpreviewer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("About Monitor Previewer") },
        text = {
            Column {
                Text("Author: Hongtao Liu 24010936")
                Text("Created for 159.336 Assignment 3")
                Text("\nHow to use:")
                Text("· Tap to select monitor")
                Text("· Drag to move monitor")
                Text("· Two fingers to zoom or move canvas")
                Text("· Use menu for more options")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("OK") }
        }
    )
}