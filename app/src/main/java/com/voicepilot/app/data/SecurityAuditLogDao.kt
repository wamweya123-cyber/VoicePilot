package com.voicepilot.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SecurityAuditLogDao {

    @Query("SELECT * FROM security_audit_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<SecurityAuditLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: SecurityAuditLog)

    @Delete
    suspend fun delete(log: SecurityAuditLog)

    @Query("DELETE FROM security_audit_logs")
    suspend fun clearAll()
}
