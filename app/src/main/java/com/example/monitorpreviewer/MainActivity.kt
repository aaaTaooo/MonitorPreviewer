package com.example.monitorpreviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.monitorpreviewer.ui.MonitorPreviewerScreen
import com.example.monitorpreviewer.ui.theme.MonitorPreviewerTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MonitorPreviewerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonitorPreviewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MonitorPreviewerScreen(viewModel)
                }
            }
        }
    }
}