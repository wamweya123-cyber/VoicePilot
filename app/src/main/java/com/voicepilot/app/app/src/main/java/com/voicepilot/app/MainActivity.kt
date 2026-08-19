package com.voicepilot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VoicePilotDashboard()
        }
    }
}

@Composable
fun VoicePilotDashboard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "VoicePilot",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Your voice-controlled Android assistant",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Service Status: Not running",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                // Voice control will be connected here later.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Voice Control")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Accessibility settings will be connected here later.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Accessibility Settings")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Recent Command: None",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
