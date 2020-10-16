package com.mego.myapplication.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mego.myapplication.models.News
import java.lang.reflect.Type

class Converters {

/*TypeConverter for MultiMedia List to get image url */
    @TypeConverter
    fun fromString(value: String?): List<News.Multimedia?>? {
        val listType = object : TypeToken<List<News.Multimedia?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<News.Multimedia?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}