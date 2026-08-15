package com.voicepilot.app.service

class VoiceCommandDispatcher(
    private val executionEngine: VoiceExecutionEngine
) {

    fun dispatch(command: ParsedVoiceCommand): String {

        return when (command.command) {

            "open" -> {
                val targetApp = command.arguments.joinToString(" ")

                if (targetApp.isBlank()) {
                    "Please tell me which app to open."
                } else {
                    executionEngine.executeSystemAction(
                        action = "OPEN_APP",
                        targetApp = targetApp,
                        parameter = ""
                    )
                }
            }

            "back" -> {
                executionEngine.executeSystemAction(
                    action = "SYSTEM_NAVIGATION",
                    targetApp = "",
                    parameter = "back"
                )
            }

            "home" -> {
                executionEngine.executeSystemAction(
                    action = "SYSTEM_NAVIGATION",
                    targetApp = "",
                    parameter = "home"
                )
            }

            "volume_up" -> {
                executionEngine.executeSystemAction(
                    action = "MEDIA_CONTROL",
                    targetApp = "",
                    parameter = "volume_up"
                )
            }

            "volume_down" -> {
                executionEngine.executeSystemAction(
                    action = "MEDIA_CONTROL",
                    targetApp = "",
                    parameter = "volume_down"
                )
            }

            "mute" -> {
                executionEngine.executeSystemAction(
                    action = "MEDIA_CONTROL",
                    targetApp = "",
                    parameter = "mute"
                )
            }

            "screenshot" -> {
                executionEngine.executeSystemAction(
                    action = "ACCESSIBILITY_READ",
                    targetApp = "",
                    parameter = "screenshot"
                )
            }

            "close" -> {
                "Closing apps will be connected to the Accessibility Service later."
            }

            "unknown" -> {
                "I didn't understand that command."
            }

            else -> {
                "Command not supported yet: ${command.command}"
            }
        }
    }
}
