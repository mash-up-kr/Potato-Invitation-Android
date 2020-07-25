package com.mashup.patatoinvitation.presentation.select

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.mashup.patatoinvitation.R
import java.util.*

class SelectingDateTimeActivity : AppCompatActivity() {
    var userHour = ""
    var userMin = ""
    var userAmPm = ""
    var userDay = ""
    var userMonth = ""
    var userYear = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectingdatetime)

        val btnDateTimeFinish = findViewById<Button>(R.id.btnDateTimeFinish)
        val tvTimeTest = findViewById<TextView>(R.id.tvTimeTest)
        val tvDateTest = findViewById<TextView>(R.id.tvDateTest)

        OnClickTime()
        OnClickDate()

        btnDateTimeFinish.setOnClickListener {
            //date
            //todo 사용자가 선택한 date
            //$userDay $userMonth $userYear
            //test
            val dateMsg = "[Test] Date is : $userDay/$userMonth/$userYear"
            tvDateTest.text = dateMsg
            tvDateTest.visibility = ViewGroup.VISIBLE
            //Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()

            //time
            //todo 사용자가 선택한 time
            //$userHour $userMin $userAmPm
            //test
            val timeMsg = "[Test] Time is: $userHour : $userMin $userAmPm"
            tvTimeTest.text = timeMsg
            tvTimeTest.visibility = ViewGroup.VISIBLE

            finish()
        }
    }

    private fun OnClickDate() {
        val datePicker = findViewById<DatePicker>(R.id.date_Picker)
        datePicker.minDate = System.currentTimeMillis()
        val today = Calendar.getInstance()
        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1

            userDay = "" + day
            userMonth = "" + month
            userYear = "" + year
        }
    }

    private fun OnClickTime() {
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""

            // AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }

            userHour = if (hour < 10) "0" + hour else "" + hour
            userMin = if (minute < 10) "0" + minute else "" + minute
            userAmPm = am_pm

        }
    }
}
