package com.voicepilot.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class VoicePilotAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // VoicePilot will process accessibility events here.
    }

    override fun onInterrupt() {
        // Called when Android interrupts the accessibility service.
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        // Accessibility service initialization will be added here.
    }
}
