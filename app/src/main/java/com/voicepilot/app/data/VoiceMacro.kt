package com.voicepilot.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voice_macros")
data class VoiceMacro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val voiceTrigger: String,
    val actions: List<String>,
    val isEnabled: Boolean = true
)
