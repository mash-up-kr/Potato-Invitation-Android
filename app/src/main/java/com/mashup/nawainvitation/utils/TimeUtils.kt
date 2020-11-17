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

        val mHour = if (hour == "0") {
            "0$hour"
        } else {
            hour
        }

        return "$year-$month-${day}T$mHour:${minute}:00.000Z"
    }
}