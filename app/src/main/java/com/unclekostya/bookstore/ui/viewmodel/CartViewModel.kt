package com.unclekostya.bookstore.ui.viewmodel

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toIntList(data: String?): List<Int> {
        if (data.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, type)
    }
}