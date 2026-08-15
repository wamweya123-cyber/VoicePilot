package com.voicepilot.app.service

class VoiceCommandParser {

    fun parse(text: String): ParsedVoiceCommand {
        val normalizedText = text.trim().lowercase()

        if (normalizedText.isEmpty()) {
            return ParsedVoiceCommand(
                originalText = text,
                command = "unknown",
                confidence = 0f
            )
        }

        val command = when {
            normalizedText.startsWith("open ") -> "open"
            normalizedText.startsWith("launch ") -> "open"
            normalizedText.startsWith("start ") -> "open"

            normalizedText.startsWith("close ") -> "close"
            normalizedText.startsWith("stop ") -> "close"

            normalizedText.startsWith("go back") -> "back"
            normalizedText == "back" -> "back"

            normalizedText.startsWith("go home") -> "home"
            normalizedText == "home" -> "home"

            normalizedText.contains("volume up") -> "volume_up"
            normalizedText.contains("increase volume") -> "volume_up"

            normalizedText.contains("volume down") -> "volume_down"
            normalizedText.contains("decrease volume") -> "volume_down"

            normalizedText.contains("mute") -> "mute"

            normalizedText.contains("take screenshot") ||
                normalizedText.contains("take a screenshot") -> "screenshot"

            else -> "unknown"
        }

        val arguments = extractArguments(normalizedText, command)

        return ParsedVoiceCommand(
            originalText = text,
            command = command,
            arguments = arguments,
            confidence = if (command == "unknown") 0.5f else 1.0f
        )
    }

    private fun extractArguments(
        text: String,
        command: String
    ): List<String> {
        return when (command) {
            "open" -> {
                text
                    .removePrefix("open ")
                    .removePrefix("launch ")
                    .removePrefix("start ")
                    .trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
            }

            "close" -> {
                text
                    .removePrefix("close ")
                    .removePrefix("stop ")
                    .trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
            }

            else -> emptyList()
        }
    }
}
