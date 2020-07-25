package com.mashup.patatoinvitation.presentation.searchaddress.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.patatoinvitation.R
import kotlinx.android.synthetic.main.activity_input_address.*

//TODO: fragment 로 수정
class InputAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_address)

        cvInputAddress.setOnClickListener {
            startActivity(Intent(this, SearchAddressActivity::class.java))
        }

        val data = intent.getStringExtra("place")
        data?.let {
            tvInputAddress.text = it
            btnInputAddressSubmit.setBackgroundColor(Color.parseColor("#fef051"))
            btnInputAddressSubmit.setTextColor(Color.parseColor("#000000"))
            tvInputAddress.setTextColor(Color.parseColor("#000000"))
        }
    }
}