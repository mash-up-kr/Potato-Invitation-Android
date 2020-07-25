package com.mashup.patatoinvitation.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.BaseActivity
import com.mashup.patatoinvitation.databinding.ActivitySplashBinding
import com.mashup.patatoinvitation.presentation.typechoice.TypeChoiceActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goTypeChoiceActivity()
    }

    private fun goTypeChoiceActivity() {
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, TypeChoiceActivity::class.java))
            finish()
        }, 3000L)
    }
}