package com.mashup.nawainvitation.presentation.selectdatatime

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.api.ApiProvider
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepositoryImpl
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_selecting_datetime.*
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

    private val invitationRepository by lazy {
        InvitationRepositoryImpl(
            ApiProvider.provideInvitationApi()
        )
    }

    var now = System.currentTimeMillis()
    var mDate = Date(now)

    var hourNow: SimpleDateFormat = SimpleDateFormat("hh")
    var hourNowInt = hourNow.format(mDate).toInt()
    var hourNow12 = if (hourNowInt < 12) hourNowInt else hourNowInt - 12
    var minNow: SimpleDateFormat = SimpleDateFormat("mm")
    var ampmNow: SimpleDateFormat = SimpleDateFormat("aa")

    var userHour = if (hourNow12 < 10) "0" + hourNow12 else "" + hourNow12
    var userMin = "" + minNow.format(mDate)
    var userAmPm = "" + ampmNow.format(mDate)

    var userDay = ""
    var userMonth = ""
    var userYear = ""

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            //$userDay $userMonth $userYear
            //ex) 202 08 04

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
                minute = userMin,
                userAmPm = userAmPm
            )

            Dlog.d("invitationTime : $invitationTime")

            invitationRepository.patchInvitationTime(
                invitationTime,
                mainViewModel.typeData.templateId,
                object : BaseResponse<Any> {
                    override fun onSuccess(data: Any) {
                        mainViewModel.listener.goToInvitationMain()
                    }

                    override fun onFail(description: String) {
                        Dlog.e("onFail : $description")
                    }

                    override fun onError(throwable: Throwable) {
                        Dlog.e("onError : ${throwable.message}")
                    }

                    override fun onLoading() {
                        mainViewModel.listener.showLoading()
                    }

                    override fun onLoaded() {
                        mainViewModel.listener.hideLoading()
                    }
                })
        }

    }

    fun InitializeListener() {
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