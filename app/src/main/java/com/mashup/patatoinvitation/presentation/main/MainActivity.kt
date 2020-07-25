package com.mashup.patatoinvitation.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.base.ext.toast
import com.mashup.patatoinvitation.base.util.Dlog
import com.mashup.patatoinvitation.databinding.ActivityMainBinding
import com.mashup.patatoinvitation.presentation.invitationtitle.InvitationTitleActivity
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
        setSupportActionBar(toolbar)
        toolbar.title = ""

        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                toast("test")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObserver() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Dlog.d("requestCode : $requestCode , resultCode : $resultCode")
    }

    override fun goToInvitationTitle() {
        InvitationTitleActivity.startInvitationTitleActivityForResult(this, RESULT_CODE)
    }

    override fun goToInvitationDate() {

    }

    override fun goToInvitationLocation() {

    }

    override fun goToInvitationPhoto() {

    }
}