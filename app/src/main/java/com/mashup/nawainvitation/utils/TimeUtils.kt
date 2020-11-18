package com.mashup.nawainvitation.utils

import com.mashup.nawainvitation.base.util.Dlog
import java.text.SimpleDateFormat
import java.util.*

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

    private fun String.toDateTimeDefault(): Date {
        val defaultFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return defaultFormat.parse(this)
    }

    fun getTimeStampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000L)
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        Dlog.d("time stamp to Date : ${sdf.format(date)}")
        return sdf.format(date)
    }

    fun String.dateToFormat(fm: SimpleDateFormat): String {
        return fm.format(this.toDateTimeDefault())
    }

    fun String.toYearMonthDay(): String {
        val yearFormat = SimpleDateFormat("yyyy.MM.dd")
        return this.dateToFormat(yearFormat)
    }

    fun String.toYearMonth(): String {
        val yearMonthFormat = SimpleDateFormat("yyyy년 MM월")
        return this.dateToFormat(yearMonthFormat)
    }

    fun String.toMonthDay(): String {
        val monthDayFormat = SimpleDateFormat("MM월 dd일")
        return this.dateToFormat(monthDayFormat)
    }

    fun String.toTime(): String {
        val timeFormat = SimpleDateFormat("a hh:mm")
        return this.dateToFormat(timeFormat)
    }
}
