package com.voicepilot.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        AppPermissionRule::class,
        SecurityAuditLog::class,
        VoiceMacro::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VoiceRemoteDatabase : RoomDatabase() {

    abstract fun appPermissionRuleDao(): AppPermissionRuleDao
    abstract fun securityAuditLogDao(): SecurityAuditLogDao
    abstract fun voiceMacroDao(): VoiceMacroDao

    companion object {
        @Volatile
        private var INSTANCE: VoiceRemoteDatabase? = null

        fun getDatabase(context: Context): VoiceRemoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VoiceRemoteDatabase::class.java,
                    "voice_remote_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
