package com.mashup.nawainvitation.data.room.typeadpter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mashup.nawainvitation.presentation.main.model.ImageInfoItem

class ImageListTypeAdapter {
    companion object {
        val gson: Gson = Gson()

        @TypeConverter
        fun jsonStringToImageList(data: String?): List<ImageInfoItem>? {
            if (data.isNullOrEmpty()) {
                return emptyList()
            }

            val listType = object : TypeToken<List<ImageInfoItem>>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun imageListToJsonString(list: List<ImageInfoItem>): String? {
            return gson.toJson(list)
        }
    }
}