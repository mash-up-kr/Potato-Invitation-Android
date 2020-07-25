package com.mashup.patatoinvitation.presentation.invitationdate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivityInvitationDateBinding
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_invitation_date.*
import java.util.*


class InvitationDateActivity :
    BaseActivity<ActivityInvitationDateBinding>(R.layout.activity_invitation_date),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    companion object {

        fun startInvitationDateActivity(context: Context) {
            context.startActivity(
                Intent(context, InvitationDateActivity::class.java).apply {
                }
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initButton()
    }

    private fun initButton() {
        btnInvitationDateBack.setOnClickListener {
            onBackPressed()
        }

        btnInvitationDate.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd =
                DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
                )

            dpd.show(supportFragmentManager, "DatePickerDialog")
        }

        btnInvitationTime.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd =
                TimePickerDialog.newInstance(
                    this,
                    now.get(Calendar.HOUR),
                    now.get(Calendar.MINUTE),
                    false
                )

            dpd.show(supportFragmentManager, "TimePickerDialog")
        }

        btnInvitationDateShare.setOnClickListener {

        }
    }

    private fun initView() {

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        tvInvitationDate.text = "${year}년 ${monthOfYear + 1}월 ${dayOfMonth}일"
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        tvInvitationTime.text = "${hourOfDay}시 ${minute}분"
    }
}