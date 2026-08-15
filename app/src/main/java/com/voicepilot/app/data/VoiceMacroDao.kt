package com.voicepilot.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceMacroDao {

    @Query("SELECT * FROM voice_macros ORDER BY title ASC")
    fun getAllMacros(): Flow<List<VoiceMacro>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(macro: VoiceMacro)

    @Update
    suspend fun update(macro: VoiceMacro)

    @Delete
    suspend fun delete(macro: VoiceMacro)
}
