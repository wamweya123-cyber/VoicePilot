package com.voicepilot.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class VoicePilotService : Service() {

    private lateinit var executionEngine: VoiceExecutionEngine
    private lateinit var parser: VoiceCommandParser
    private lateinit var dispatcher: VoiceCommandDispatcher
    private lateinit var voiceListener: ContinuousVoiceListener

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        val notification = createNotification()

        startForeground(
            NOTIFICATION_ID,
            notification
        )

        executionEngine = VoiceExecutionEngine(this)

        parser = VoiceCommandParser()

        dispatcher = VoiceCommandDispatcher(
            executionEngine = executionEngine
        )

        voiceListener = ContinuousVoiceListener(
            context = this,
            parser = parser,
            dispatcher = dispatcher
        )
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        when (intent?.action) {

            ACTION_START_LISTENING -> {
                voiceListener.startListening()
            }

            ACTION_STOP_LISTENING -> {
                voiceListener.stopListening()
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {

        if (::voiceListener.isInitialized) {
            voiceListener.destroy()
        }

        if (::executionEngine.isInitialized) {
            executionEngine.shutdown()
        }

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                "VoicePilot Voice Control",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description =
                    "Shows when VoicePilot voice control is active."
            }

            val notificationManager =
                getSystemService(
                    NotificationManager::class.java
                )

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {

        return NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setContentTitle("VoicePilot")
            .setContentText("Voice control is active")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setOngoing(true)
            .build()
    }

    companion object {

        const val ACTION_START_LISTENING =
            "com.voicepilot.app.action.START_LISTENING"

        const val ACTION_STOP_LISTENING =
            "com.voicepilot.app.action.STOP_LISTENING"

        private const val CHANNEL_ID =
            "voicepilot_voice_control"

        private const val NOTIFICATION_ID = 1001
    }
}
