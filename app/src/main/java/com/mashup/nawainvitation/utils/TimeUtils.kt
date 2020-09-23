package com.mashup.nawainvitation.utils

object TimeUtils {

    //2020-08-17T07:56:26.559Z
    fun getDataLocalTime(
        year: String,
        month: String,
        day: String,
        hour: String,
        minute: String,
        userAmPm: String
    ): String {
        val intHour = hour.toInt()

        val mHour = if (userAmPm == "오후") {
            (intHour + 12).toString()
        } else {
            hour
        }

        return "$year-$month-${day}T$mHour:${minute}:00.000Z"
    }
}