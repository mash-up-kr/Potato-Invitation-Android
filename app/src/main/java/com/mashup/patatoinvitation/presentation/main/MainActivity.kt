package com.mashup.patatoinvitation.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivityMainBinding
import com.mashup.patatoinvitation.presentation.invitationinfo.InvitationInfoFragment
import com.mashup.patatoinvitation.presentation.invitationpreview.InvitationPreviewActivity
import com.mashup.patatoinvitation.presentation.searchlocation.view.InputLocationFragment
import com.mashup.patatoinvitation.presentation.searchlocation.view.SearchLocationFragment
import com.mashup.patatoinvitation.presentation.selectdatatime.SelectingDateTimeFragment
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    MainViewModel.MainListener {

    companion object {

        private const val RESULT_CODE = 1000

        private const val EXTRA_TYPE_DATA = "type_data"

        fun startMainActivityWithData(context: Context, data: TypeData) {
            context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    putExtra(EXTRA_TYPE_DATA, data)
                }
            )
        }
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
            this, MainViewModelFactory(this, getTypeData())
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = mainViewModel

        initTopBar()
        initFragment()
    }

    private fun initTopBar() {
        btnMainBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initFragment() {
        //TODO 처음 초기화시 서버로 부터 데이터를 받아온다.
        goToInvitationMain()
    }

    private fun replaceFragmentWithTitle(title: String, fragment: Fragment, tag: String? = null) {
        tvMainTopBardTitle.text = title

        supportFragmentManager.beginTransaction()
            .replace(R.id.flMainContainer, fragment, tag)
            .commit()
    }

    private fun getTypeData() = intent?.getParcelableExtra<TypeData>(EXTRA_TYPE_DATA)!!

    override fun goToInvitationMain() {
        //TODO 데이터 동기화 필요
        replaceFragmentWithTitle(
            getString(R.string.make_invitation),
            MainFragment.newInstance(),
            MainFragment.TAG_ID
        )
    }

    override fun goToInvitationInfo() {
        replaceFragmentWithTitle(
            getString(R.string.input_invitation),
            InvitationInfoFragment.newInstance()
        )
    }

    override fun goToInvitationDate() {
        replaceFragmentWithTitle(
            getString(R.string.input_date),
            SelectingDateTimeFragment.newInstance()
        )
    }

    override fun goToInvitationInputLocation() {
        replaceFragmentWithTitle(
            getString(R.string.input_address_title),
            InputLocationFragment.newInstance()
        )
    }

    override fun goToInvitationSearchLocation() {
        replaceFragmentWithTitle(
            getString(R.string.input_address_title),
            SearchLocationFragment.newInstance()
        )
    }

    override fun goToInvitationPhoto() {
        //TODO next step
    }

    override fun goToPreview() {
        InvitationPreviewActivity.startPreviewActivity(this)
    }

    override fun onBackPressed() {
        val isMainFragment = supportFragmentManager.findFragmentByTag(MainFragment.TAG_ID)
        if (isMainFragment == null) {
            goToInvitationMain()
        } else {
            super.onBackPressed()
        }
    }
}