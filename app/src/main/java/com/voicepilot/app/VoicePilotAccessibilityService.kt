package com.voicepilot.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class VoicePilotAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Accessibility events will be handled here.
    }

    override fun onInterrupt() {
        // Called when the service is interrupted.
    }
}
