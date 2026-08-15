package com.voicepilot.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Column
import androidx.compose.material3.Text
import androidx.core.content.ContextCompat
import com.voicepilot.app.service.VoicePilotService

class MainActivity : ComponentActivity() {

    private val microphonePermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                startVoicePilotService()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column {

                Text("VoicePilot")

                Button(
                    onClick = {
                        requestMicrophonePermission()
                    }
                ) {
                    Text("Start VoicePilot")
                }

                Button(
                    onClick = {
                        stopVoicePilotService()
                    }
                ) {
                    Text("Stop VoicePilot")
                }
            }
        }
    }

    private fun requestMicrophonePermission() {

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {

                startVoicePilotService()
            }

            else -> {
                microphonePermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO
                )
            }
        }
    }

    private fun startVoicePilotService() {

        val intent = Intent(
            this,
            VoicePilotService::class.java
        ).apply {
            action = VoicePilotService.ACTION_START_LISTENING
        }

        ContextCompat.startForegroundService(
            this,
            intent
        )
    }

    private fun stopVoicePilotService() {

        val intent = Intent(
            this,
            VoicePilotService::class.java
        ).apply {
            action = VoicePilotService.ACTION_STOP_LISTENING
        }

        startService(intent)
    }
}
