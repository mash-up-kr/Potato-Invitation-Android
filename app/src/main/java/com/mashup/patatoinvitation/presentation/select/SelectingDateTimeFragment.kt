package com.mashup.patatoinvitation.presentation.select

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.mashup.patatoinvitation.R
import kotlinx.android.synthetic.main.fragment_selecting_datetime.*
import java.text.SimpleDateFormat
import java.util.*


class SelectingDateTimeFragment : Fragment() {

    //TODO 회의 : 초대장에 보여지는 형식 -> 넘기는 방식 논의, spinner 10분 단위?, 디자인 확인
    //로케일, 배포 직전 테스트 코드 지우기, 데이터 바인딩 이해 -> fragment 레이아웃에 데이터 바인딩, 애뮬레이터말고도 테스트 해보기

    //사용자가 시간 스피너 변경없이, 현재 시간 바로 입력했을 경우 대비해서 초기 시간 설정 //TODO 오후에도 현재값 잘 받나 테스트 해보기
    var now = System.currentTimeMillis()
    var mDate = Date(now)

    var hourNow: SimpleDateFormat = SimpleDateFormat("hh")
    var minNow: SimpleDateFormat = SimpleDateFormat("mm")

    var hourNowInt = hourNow.format(mDate).toInt()
    var hourNow12 = if (hourNowInt < 12) hourNowInt else hourNowInt-12

    var userHour = if (hourNow12 < 10) "0" + hourNow12 else "" + hourNow12
    var userMin = "" + minNow.format(mDate)
    var userAmPm = if (hourNowInt < 12) "오전" else "오후"

    var userDay = ""
    var userMonth = ""
    var userYear = ""

    private var callbackMethod: DatePickerDialog.OnDateSetListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_selecting_datetime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnDateTimeFinish = getView()?.findViewById<View>(R.id.btnDateTimeFinish) as Button


        cvInputDate.setOnClickListener {
            this.InitializeListener()

            val today = Calendar.getInstance()
            val minDate = Calendar.getInstance()

            val dialog =
                activity?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        R.style.MyDatePickerDialogStyle,
                        callbackMethod,
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                    )
                }

            //선택 가능 날짜 지정 : 현재 날짜 부터 선택 가능하게
            minDate.set(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
            )
            dialog?.datePicker?.minDate = minDate.time.time

            dialog?.show()
        }

        OnClickTime()

        btnDateTimeFinish.setOnClickListener {
            //date
            //TODO 사용자가 선택한 date
            //$userDay $userMonth $userYear
            //2020 08 04

            //time
            //TODO 사용자가 선택한 time
            //$userHour $userMin $userAmPm
            //04 05 오전

            //test 코드
            //val tvTest = getView()?.findViewById<TextView>(R.id.tvDateTest) as TextView
            //val timeMsg = "[Test] Time is: $userHour : $userMin $userAmPm"
            //tvTest.text = timeMsg

            activity?.finish()
        }

    }

    fun InitializeListener() {
        val tvInputDate = view?.findViewById<View>(R.id.tvInputDate) as TextView

        callbackMethod =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //TO DO SOMETHING
                val month = monthOfYear + 1 //DatePickerDialog에서 1월은 0 -> 12월은 11로 배정돠서 +1

                userDay = if (dayOfMonth < 10) "0" + dayOfMonth else "" + dayOfMonth
                userMonth = if (month < 10) "0" + month else "" + month
                userYear = "" + year

                val dateMsg = userYear + "년 " + userMonth + "월 " + userDay + "일"
                tvInputDate.text = dateMsg
                tvInputDate.setTextColor(Color.parseColor("#000000"))
            }
    }

    private fun OnClickTime() {
        val timePicker = view?.findViewById<View>(R.id.timePicker) as TimePicker

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