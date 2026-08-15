package com.voicepilot.app.data

import kotlinx.coroutines.flow.Flow

class VoiceRemoteRepository(
    database: VoiceRemoteDatabase
) {
    private val appRuleDao = database.appPermissionRuleDao()
    private val auditLogDao = database.securityAuditLogDao()
    private val macroDao = database.voiceMacroDao()

    val allAppRules: Flow<List<AppPermissionRule>> =
        appRuleDao.getAllRules()

    val allLogs: Flow<List<SecurityAuditLog>> =
        auditLogDao.getAllLogs()

    val allMacros: Flow<List<VoiceMacro>> =
        macroDao.getAllMacros()

    suspend fun insertDefaultRulesIfEmpty() {
        if (appRuleDao.count() == 0) {
            appRuleDao.insert(
                AppPermissionRule(
                    appName = "Settings",
                    packageName = "com.android.settings",
                    isAllowed = true,
                    requiresPin = false
                )
            )

            appRuleDao.insert(
                AppPermissionRule(
                    appName = "Camera",
                    packageName = "com.android.camera",
                    isAllowed = true,
                    requiresPin = false
                )
            )
        }
    }

    suspend fun insertLog(log: SecurityAuditLog) {
        auditLogDao.insert(log)
    }

    suspend fun clearLogs() {
        auditLogDao.clearAll()
    }

    suspend fun updateRule(rule: AppPermissionRule) {
        appRuleDao.update(rule)
    }

    suspend fun insertMacro(macro: VoiceMacro) {
        macroDao.insert(macro)
    }

    suspend fun updateMacro(macro: VoiceMacro) {
        macroDao.update(macro)
    }

    suspend fun deleteMacro(macro: VoiceMacro) {
        macroDao.delete(macro)
    }
}
