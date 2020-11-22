package com.mashup.nawainvitation.presentation.selectdatatime

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_selecting_datetime.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SelectingDateTimeFragment : Fragment() {

    companion object {

        fun newInstance(): SelectingDateTimeFragment {
            return SelectingDateTimeFragment()
        }
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MainViewModel::class.java)
    }

    private val invitationRepository: InvitationRepository by lazy {
        Injection.provideInvitationRepository()
    }

    var now = System.currentTimeMillis()
    var mDate = Date(now)

    var hourNow: SimpleDateFormat = SimpleDateFormat("HH")
    var hourNowInt = hourNow.format(mDate).toInt()
    var minNow: SimpleDateFormat = SimpleDateFormat("mm")

    var userHour = if (hourNowInt < 10) "0$hourNowInt" else "$hourNowInt"
    var userMin = "" + minNow.format(mDate)

    val today = Calendar.getInstance()
    val minDate = Calendar.getInstance()

    var userDay = "" + today.get(Calendar.DAY_OF_MONTH)
    var userMonth = "" + today.get(Calendar.MONTH)
    var userYear = "" + today.get(Calendar.YEAR)


    private var callbackMethod: DatePickerDialog.OnDateSetListener? = null

    private val dispatcher by lazy { requireActivity().onBackPressedDispatcher }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            mainViewModel.listener.goToInvitationMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_selecting_datetime, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invitationRepository.getLatestInvitation().subscribe({
            val mTime = it?.invitationTime
            if (mTime.isNullOrEmpty().not()) {
                val tvInputDate = getView()?.findViewById(R.id.tvInputDate) as TextView

                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val yearF = SimpleDateFormat("yyyy")
                val monthF = SimpleDateFormat("MM")
                val dayF = SimpleDateFormat("dd")
                val hourF = SimpleDateFormat("HH")
                val minuteF = SimpleDateFormat("mm")

                try {
                    val dateParsed = inputFormat.parse(mTime) // 문자열을 파싱해 Date형으로 저장한다
                    val saveHourInt = hourF.format(dateParsed).toInt()
                    val mUserHour = saveHourInt.toString()
                    val mUserMin = minuteF.format(dateParsed)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        timePicker.hour = mUserHour.toInt()
                        timePicker.minute = mUserMin.toInt()
                    }

                    userHour = mUserHour
                    userMin = mUserMin

                    userDay = dayF.format(dateParsed)
                    userMonth = monthF.format(dateParsed)
                    userYear = yearF.format(dateParsed)

                    val dateMsg = userYear + "년 " + userMonth + "월 " + userDay + "일"
                    tvInputDate.text = dateMsg
                    tvInputDate.setTextColor(Color.parseColor("#000000"))

                    btnDateTimeFinish.setEnabled(true)

                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }) {
            Dlog.e(it.message)
        }

        //date
        cvInputDate.setOnClickListener {
            this.initializeListener()

            //선택된 초기 날짜 설정
            val dialog =
                activity?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        R.style.MyDatePickerDialogStyle,
                        callbackMethod,
                        userYear.toInt(),
                        userMonth.toInt()-1,
                        userDay.toInt()
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

        //time
        onClickTime()

        //finish
        btnDateTimeFinish.setOnClickListener {
            //date
            //$userDay $userMonth $userYear
            //ex) 2020 08 04

            //time
            //$userHour $userMin $userAmPm
            //ex) 04 05 오전

            //test 코드
            //val tvTest = getView()?.findViewById<TextView>(R.id.tvDateTest) as TextView
            //val timeMsg = "[Test] Time is: $userHour : $userMin $userAmPm"
            //tvTest.text = timeMsg

            val invitationTime = TimeUtils.getDataLocalTime(
                year = userYear,
                month = userMonth,
                day = userDay,
                hour = userHour,
                minute = userMin
            )

            Dlog.d("invitationTime : $invitationTime")

            invitationRepository.updateInvitationTime(invitationTime)
            mainViewModel.listener.goToInvitationMain()
        }
    }

    private fun initializeListener() {
        val tvInputDate = view?.findViewById<View>(R.id.tvInputDate) as TextView

        callbackMethod =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                //TO DO SOMETHING
                val month = monthOfYear + 1 //DatePickerDialog에서 1월은 0 12월은 11로 배정돼서, +1

                userDay = if (dayOfMonth < 10) "0" + dayOfMonth else "" + dayOfMonth
                userMonth = if (month < 10) "0" + month else "" + month
                userYear = "" + year

                val dateMsg = userYear + "년 " + userMonth + "월 " + userDay + "일"
                tvInputDate.text = dateMsg
                tvInputDate.setTextColor(Color.parseColor("#000000"))

                btnDateTimeFinish.setEnabled(true)
            }
    }

    private fun onClickTime() {

        //선택된 초기 시간 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            timePicker.hour = userHour.toInt()
            timePicker.minute = userMin.toInt()
        }

        //listner
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour

            userHour = if (hour < 10) "0" + hour else "" + hour
            userMin = if (minute < 10) "0" + minute else "" + minute
        }
    }
}