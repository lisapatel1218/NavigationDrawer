package com.example.navigationdrawer
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class Converters {
    @TypeConverter
    fun fromCommentsListToJson(value: List<String>?): String {
        val gson = Gson()
        if (value == null) {
            return "[]"
        }
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCommentsList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}