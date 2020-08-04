package com.mashup.patatoinvitation.presentation.select

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.mashup.patatoinvitation.R
import kotlinx.android.synthetic.main.activity_selectingdatetime.*
import java.text.SimpleDateFormat
import java.util.*


class SelectingDateTimeActivity : AppCompatActivity() {

    //사용자가 시간 스피너 변경없이, 현재 시간 바로 입력했을 경우 대비해서 초기 시간 설정
    //: 이 경우에는 부득이하게 24시간제로 am pm 값을 null로
    var now = System.currentTimeMillis()
    var mDate = Date(now)

    var hourNow: SimpleDateFormat = SimpleDateFormat("hh")
    var minNow: SimpleDateFormat = SimpleDateFormat("mm")

    var userHour = "" + hourNow.format(mDate);
    var userMin = "" + minNow.format(mDate);
    var userAmPm = ""

    var userDay = ""
    var userMonth = ""
    var userYear = ""

    private var callbackMethod: DatePickerDialog.OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectingdatetime)

        val btnDateTimeFinish = findViewById<Button>(R.id.btnDateTimeFinish)


        cvInputDate.setOnClickListener {
            this.InitializeListener();

            val today = Calendar.getInstance()
            val minDate = Calendar.getInstance()

            val dialog =
                DatePickerDialog(
                    this,
                    R.style.MyDatePickerDialogStyle,
                    callbackMethod,
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                )

            //선택 가능 날짜 지정
            minDate.set(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            );
            dialog.getDatePicker().setMinDate(minDate.getTime().getTime());

            dialog.show()
        }

        OnClickTime()

        btnDateTimeFinish.setOnClickListener {
            //date
            //todo 사용자가 선택한 date
            //$userDay $userMonth $userYear
            //2020 08 04

            //time
            //todo 사용자가 선택한 time
            //$userHour $userMin $userAmPm
            //04 05 오전
            //사용자가 변경없이 현재 시간 바로 했을 경우 : 04 38

            finish()
        }
    }

    fun InitializeListener() {
        val tvInputDate = findViewById<TextView>(R.id.tvInputDate)

        callbackMethod =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //TO DO SOMETHING
                val month = monthOfYear + 1

                userDay = if (dayOfMonth < 10) "0" + dayOfMonth else "" + dayOfMonth
                userMonth = if (month < 10) "0" + month else "" + month
                userYear = "" + year

                val dateMsg = userYear + "년 " + userMonth + "월 " + userDay + "일"
                tvInputDate.text = dateMsg
                tvInputDate.setTextColor(Color.parseColor("#000000"));
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
                    am_pm = "오전"
                }
                hour == 12 -> am_pm = "오후"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "오후"
                }
                else -> am_pm = "오전"
            }

            userHour = if (hour < 10) "0" + hour else "" + hour
            userMin = if (minute < 10) "0" + minute else "" + minute
            userAmPm = am_pm
        }
    }
}
