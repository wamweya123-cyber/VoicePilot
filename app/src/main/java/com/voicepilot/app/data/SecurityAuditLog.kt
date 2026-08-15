package com.voicepilot.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "security_audit_logs")
data class SecurityAuditLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val voiceCommand: String,
    val detectedIntent: String,
    val targetApp: String,
    val status: String,
    val details: String = ""
)
