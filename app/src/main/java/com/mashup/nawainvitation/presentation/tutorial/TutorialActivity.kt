package com.mashup.nawainvitation.presentation.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.nawainvitation.R
import kotlinx.android.synthetic.main.activity_tutorial.*


class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        btnOKTutorial.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun startTutorialActivity(context: Context) {
            context.startActivity(Intent(context, TutorialActivity::class.java))
        }
    }
}