package com.mashup.nawainvitation.utils

object TimeUtils {

    //2020-08-17T07:56:26.559Z
    fun getDataLocalTime(
        year: String,
        month: String,
        day: String,
        hour: String,
        minute: String
    ): String {
        return "$year-$month-${day}T$hour:${minute}:00.000Z"
    }
}