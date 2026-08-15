package com.voicepilot.app.data

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return JSONArray(value).toString()
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val jsonArray = JSONArray(value)
        return List(jsonArray.length()) { index ->
            jsonArray.getString(index)
        }
    }
}
