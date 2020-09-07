package com.mashup.nawainvitation.presentation.typechoice

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mashup.nawainvitation.BuildConfig
import com.mashup.nawainvitation.R

class LandingPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_landing_page)

        val btnBackLanding = findViewById<TextView>(R.id.btnBackLanding)

        if (intent.extras == null) {
        } else {
            val extras = intent.extras
            val id = extras!!.getInt("landingPageId")

            val tvLandingTitle = findViewById<TextView>(R.id.tvLandingTitle)
            val tvLandingFirstTitle = findViewById<TextView>(R.id.tvLandingFirstTitle)
            val tvLandingFirstText = findViewById<TextView>(R.id.tvLandingFirstText)

            val tvLandingSecondTitle = findViewById<TextView>(R.id.tvLandingSecondTitle)
            val tvLandingSecondText = findViewById<TextView>(R.id.tvLandingSecondText)
            val tvLandingThirdTitle = findViewById<TextView>(R.id.tvLandingThirdTitle)
            val tvLandingThirdText = findViewById<TextView>(R.id.tvLandingThirdText)
            val tvLandingFourthTitle = findViewById<TextView>(R.id.tvLandingFourthTitle)
            val tvLandingFourthText = findViewById<TextView>(R.id.tvLandingFourthText)

            if (id==0) {
                tvLandingTitle.setText(getText(R.string.vesion_title))
                tvLandingFirstTitle.setText("Ver . ${BuildConfig.VERSION_NAME}")
                //tvLandingFirstText.setText(getText(R.string.vesion_first_text))
            }

            if (id==1){
                tvLandingTitle.setText(getText(R.string.landing_title))
                tvLandingFirstTitle.setText(getText(R.string.landing_first_title))
                tvLandingFirstText.setText(getText(R.string.landing_first_text))
                tvLandingSecondTitle.setText(getText(R.string.landing_second_title))
                tvLandingSecondText.setText(getText(R.string.landing_second_text))
                tvLandingThirdTitle.setText(getText(R.string.landing_third_title))
                tvLandingThirdText.setText(getText(R.string.landing_third_text))
                tvLandingFourthTitle.setText(getText(R.string.landing_fourth_title))
                tvLandingFourthText.setText(getText(R.string.landing_fourth_text))
            }

            if (id==2){
                tvLandingTitle.setText(getText(R.string.fedb_title))
                tvLandingFirstTitle.setText(getText(R.string.fedb_first_title))
                tvLandingFirstText.setText(getText(R.string.fedb_first_text))
            }
        }

        btnBackLanding.setOnClickListener(){
            finish()
        }
    }
}