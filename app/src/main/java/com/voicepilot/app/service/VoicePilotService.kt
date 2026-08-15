package com.voicepilot.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class VoicePilotService : Service() {

    private lateinit var executionEngine: VoiceExecutionEngine
    private lateinit var parser: VoiceCommandParser
    private lateinit var dispatcher: VoiceCommandDispatcher
    private lateinit var voiceListener: ContinuousVoiceListener

    override fun onCreate() {
        super.onCreate()

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

        voiceListener.destroy()
        executionEngine.shutdown()

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {

        const val ACTION_START_LISTENING =
            "com.voicepilot.app.action.START_LISTENING"

        const val ACTION_STOP_LISTENING =
            "com.voicepilot.app.action.STOP_LISTENING"
    }
}
