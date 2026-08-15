package com.voicepilot.app.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.speech.tts.TextToSpeech
import java.util.Locale

class VoiceExecutionEngine(
    private val context: Context
) {

    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.getDefault()
            }
        }
    }

    fun executeSystemAction(
        action: String,
        targetApp: String,
        parameter: String
    ): String {

        return try {
            when (action) {

                "OPEN_APP" -> {
                    openApplication(targetApp)
                }

                "CHANGE_SETTING" -> {
                    openRelevantSettings(parameter)
                }

                "LAUNCH_CAMERA" -> {
                    val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    "Camera opened."
                }

                "SECURITY_LOCK" -> {
                    requestDeviceLock()
                }

                "SYSTEM_NAVIGATION" -> {
                    "Navigation command received: $parameter"
                }

                "MEDIA_CONTROL" -> {
                    "Media command received: $parameter"
                }

                "ACCESSIBILITY_READ" -> {
                    "Screen-reading request received."
                }

                else -> {
                    "Command received but not yet supported: $action"
                }
            }
        } catch (e: Exception) {
            "Unable to execute command: ${e.message ?: "Unknown error"}"
        }
    }

    private fun openApplication(appName: String): String {

        val packageName = when (appName.lowercase()) {
            "settings" -> "com.android.settings"
            "youtube" -> "com.google.android.youtube"
            "chrome" -> "com.android.chrome"
            "camera" -> "com.android.camera"
            else -> null
        }

        if (packageName != null) {
            val launchIntent =
                context.packageManager.getLaunchIntentForPackage(packageName)

            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
                return "Opening $appName."
            }
        }

        return "I couldn't find $appName on this phone."
    }

    private fun openRelevantSettings(parameter: String): String {

        val intent = Intent(Settings.ACTION_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        return "Opening Android settings."
    }

    private fun requestDeviceLock(): String {
        /*
         * Device locking will be connected to the enabled
         * Accessibility Service in a later step.
         *
         * We deliberately don't attempt to bypass Android's
         * security restrictions here.
         */
        return "Phone lock requested. Accessibility authorization is required."
    }

    fun speak(text: String, rate: Float = 1.0f) {

        textToSpeech?.setSpeechRate(rate.coerceIn(0.5f, 2.0f))
        textToSpeech?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "VoicePilot_${System.currentTimeMillis()}"
        )
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}
