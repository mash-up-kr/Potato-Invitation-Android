package com.mashup.patatoinvitation.presentation.invitationtitle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivityInvitationTitleBinding

class InvitationTitleActivity :
    BaseActivity<ActivityInvitationTitleBinding>(R.layout.activity_invitation_title) {

    companion object {
        fun startInvitationTitleActivityForResult(activity: Activity, resultCode: Int) {
            activity.startActivityForResult(
                Intent(activity, InvitationTitleActivity::class.java),
                resultCode
            )
        }
    }

    private val invitationTitleViewModel by lazy {
        ViewModelProvider(
            this, InvitationTitleViewModelFactory(

            )
        ).get(InvitationTitleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = invitationTitleViewModel
    }
}