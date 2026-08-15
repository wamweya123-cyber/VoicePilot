package com.voicepilot.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppPermissionRuleDao {

    @Query("SELECT * FROM app_permission_rules ORDER BY appName ASC")
    fun getAllRules(): Flow<List<AppPermissionRule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rule: AppPermissionRule)

    @Update
    suspend fun update(rule: AppPermissionRule)

    @Delete
    suspend fun delete(rule: AppPermissionRule)

    @Query("SELECT COUNT(*) FROM app_permission_rules")
    suspend fun count(): Int
}
