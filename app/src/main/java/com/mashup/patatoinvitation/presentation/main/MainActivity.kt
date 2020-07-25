package com.mashup.patatoinvitation.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.base.util.Dlog
import com.mashup.patatoinvitation.databinding.ActivityMainBinding
import com.mashup.patatoinvitation.presentation.invitationpreview.InvitationPreviewActivity
import com.mashup.patatoinvitation.presentation.invitationtitle.InvitationTitleActivity
import com.mashup.patatoinvitation.presentation.searchaddress.ui.InputAddressActivity
import com.mashup.patatoinvitation.presentation.select.SelectingDateTimeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    MainViewModel.MainListener {

    companion object {

        private const val RESULT_CODE = 1000
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
            this, MainViewModelFactory(this)
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = mainViewModel

        initButton()
    }

    private fun initButton() {
        btnMainBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Dlog.d("requestCode : $requestCode , resultCode : $resultCode")
    }

    override fun goToInvitationTitle() {
        InvitationTitleActivity.startInvitationTitleActivityForResult(this, RESULT_CODE)
    }

    override fun goToInvitationDate() {
        //InvitationDateActivity.startInvitationDateActivity(this)
        startActivity(
            Intent(this, SelectingDateTimeActivity::class.java)
        )
    }

    override fun goToInvitationLocation() {
        startActivity(
            Intent(this, InputAddressActivity::class.java)
        )
    }

    override fun goToInvitationPhoto() {
        //TODO next step
    }

    override fun goToPreview() {
        InvitationPreviewActivity.startPreviewActivity(this)
    }
}