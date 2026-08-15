package com.voicepilot.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_permission_rules")
data class AppPermissionRule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val appName: String,
    val packageName: String = "",
    val isAllowed: Boolean = true,
    val requiresPin: Boolean = false
)
