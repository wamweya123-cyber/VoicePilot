package com.voicepilot.app.service

/**
 * Represents a voice command after it has been recognized
 * and converted into a structured command.
 */
data class ParsedVoiceCommand(
    val originalText: String,
    val command: String,
    val arguments: List<String> = emptyList(),
    val confidence: Float = 1.0f
)
