package com.mashup.nawainvitation.presentation.invitationlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mashup.nawainvitation.R
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_landing_page)

        val btnBackLanding = findViewById<TextView>(R.id.btnBackLanding)

        if (intent.extras != null) {
            val extras = intent.extras
            val id = extras!!.getInt(LANDING_PAGE_ID)

            when (id) {
                EXTRA_VERSION_TYPE -> setVersionTypeData()
                EXTRA_LANDING_TYPE -> setLandingTypeData()
                EXTRA_FEEDBACK_TYPE -> setFeedBackTypeData()
            }
        }

        btnBackLanding.setOnClickListener() {
            finish()
        }
    }

    private fun setVersionTypeData() {
        tvLandingTitle.text = getText(R.string.vesion_title)
        tvLandingFirstTitle.text = getText(R.string.vesion_first_title)
        tvLandingFirstText.text = getText(R.string.vesion_first_text)
    }

    private fun setLandingTypeData() {
        tvLandingTitle.text = getText(R.string.landing_title)
        tvLandingFirstTitle.text = getText(R.string.landing_first_title)
        tvLandingFirstText.text = getText(R.string.landing_first_text)
        tvLandingSecondTitle.text = getText(R.string.landing_second_title)
        tvLandingSecondText.text = getText(R.string.landing_second_text)
        tvLandingThirdTitle.text = getText(R.string.landing_third_title)
        tvLandingThirdText.text = getText(R.string.landing_third_text)
        tvLandingFourthTitle.text = getText(R.string.landing_fourth_title)
        tvLandingFourthText.text = getText(R.string.landing_fourth_text)
    }

    private fun setFeedBackTypeData() {
        tvLandingTitle.text = getText(R.string.fedb_title)
        tvLandingFirstTitle.text = getText(R.string.fedb_first_title)
        tvLandingFirstText.text = getText(R.string.fedb_first_text)
    }

    companion object {
        const val EXTRA_VERSION_TYPE = 0
        const val EXTRA_LANDING_TYPE = 1
        const val EXTRA_FEEDBACK_TYPE = 2
        private const val LANDING_PAGE_ID = "landingPageId"

        fun startLandingPageActivity(context: Context, landingPageId: Int) {
            context.startActivity(
                Intent(context, LandingPageActivity::class.java).apply {
                    putExtra(LANDING_PAGE_ID, landingPageId)
                }
            )
        }
    }
}