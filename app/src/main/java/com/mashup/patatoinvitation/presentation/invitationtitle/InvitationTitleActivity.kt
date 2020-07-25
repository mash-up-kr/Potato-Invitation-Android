package com.mashup.patatoinvitation.presentation.invitationtitle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.data.injection.Injection
import com.mashup.patatoinvitation.databinding.ActivityInvitationTitleBinding
import kotlinx.android.synthetic.main.activity_invitation_title.*

class InvitationTitleActivity() :
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
                Injection.provideInvitationRepository()
            )
        ).get(InvitationTitleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = invitationTitleViewModel

        initButton()
        initObserver()
    }

    private fun initObserver() {
        invitationTitleViewModel.finishView.observe(this, Observer {
            finish()
        })
    }

    private fun initButton() {
        btnInvitationTitleBack.setOnClickListener {
            onBackPressed()
        }
    }
}