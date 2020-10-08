package com.mashup.nawainvitation.data.room.typeadpter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mashup.nawainvitation.presentation.main.model.InvitationsData

class ImageListTypeAdapter {
    companion object{
        val gson: Gson = Gson()

        @TypeConverter
        fun jsonStringToImageList(data: String?) : List<InvitationsData.ImageInfoData>?{
            if(data.isNullOrEmpty()){
                return emptyList()
            }

            val listType = object : TypeToken<List<InvitationsData.ImageInfoData>>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun imageListToJsonString(list: List<InvitationsData.ImageInfoData>) : String?{
            return gson.toJson(list)
        }
    }
}