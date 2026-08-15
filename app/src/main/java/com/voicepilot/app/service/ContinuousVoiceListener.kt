package com.voicepilot.app.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class ContinuousVoiceListener(
    private val context: Context,
    private val parser: VoiceCommandParser,
    private val dispatcher: VoiceCommandDispatcher
) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    fun startListening() {

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            return
        }

        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(
                recognitionListener
            )
        }

        isListening = true

        startRecognition()
    }

    private fun startRecognition() {

        if (!isListening) {
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                java.util.Locale.getDefault()
            )

            putExtra(
                RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                false
            )
        }

        speechRecognizer?.startListening(intent)
    }

    private val recognitionListener =
        object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                // Speech recognition is ready.
            }

            override fun onBeginningOfSpeech() {
                // User started speaking.
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Audio level changed.
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                // Audio buffer received.
            }

            override fun onEndOfSpeech() {
                // User stopped speaking.
            }

            override fun onError(error: Int) {
                if (isListening) {
                    restartRecognition()
                }
            }

            override fun onResults(results: Bundle?) {

                val matches =
                    results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )

                val spokenText = matches?.firstOrNull()

                if (!spokenText.isNullOrBlank()) {

                    val parsedCommand =
                        parser.parse(spokenText)

                    val result =
                        dispatcher.dispatch(parsedCommand)

                    // The execution result can later be sent
                    // to Text-to-Speech through VoiceExecutionEngine.
                }

                if (isListening) {
                    restartRecognition()
                }
            }

            override fun onPartialResults(
                partialResults: Bundle?
            ) {
                // Partial results are currently ignored.
            }

            override fun onEvent(
                eventType: Int,
                params: Bundle?
            ) {
                // Reserved for future recognition events.
            }
        }

    private fun restartRecognition() {

        speechRecognizer?.cancel()

        android.os.Handler(
            android.os.Looper.getMainLooper()
        ).postDelayed(
            {
                if (isListening) {
                    startRecognition()
                }
            },
            300
        )
    }

    fun stopListening() {

        isListening = false

        speechRecognizer?.stopListening()
        speechRecognizer?.cancel()
    }

    fun destroy() {

        isListening = false

        speechRecognizer?.stopListening()
        speechRecognizer?.cancel()
        speechRecognizer?.destroy()

        speechRecognizer = null
    }
}
