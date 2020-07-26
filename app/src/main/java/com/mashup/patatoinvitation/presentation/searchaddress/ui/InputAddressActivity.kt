package com.mashup.patatoinvitation.presentation.searchaddress.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.base.ext.toast
import com.mashup.patatoinvitation.data.injection.Injection
import com.mashup.patatoinvitation.data.repository.InvitationRepository
import com.mashup.patatoinvitation.presentation.searchaddress.model.Documents
import kotlinx.android.synthetic.main.activity_input_address.*

//TODO: fragment 로 수정
class InputAddressActivity : AppCompatActivity() {

    companion object {
        private const val RESULT_CODE = 1000

        private const val EXTRA_TEMPLATE_ID = "template_id"

        fun startInputAddressActivity(context: Context, templateId: Int) {
            context.startActivity(
                Intent(context, InputAddressActivity::class.java).apply {
                    putExtra(EXTRA_TEMPLATE_ID, templateId)
                }
            )
        }
    }

    private val repository: InvitationRepository by lazy {
        Injection.provideInvitationRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_address)

        cvInputAddress.setOnClickListener {
            startActivityForResult(
                Intent(this, SearchAddressActivity::class.java),
                RESULT_CODE
            )
        }

        btnInputAddressSubmit.setOnClickListener {
            toast("$document")
        }
    }

    private var document = intent?.getParcelableExtra<Documents>("place")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE) {

            document = data?.getParcelableExtra("place")

            document?.let {
                tvInputAddress.text = it.placeName
                btnInputAddressSubmit.setBackgroundColor(Color.parseColor("#fef051"))
                btnInputAddressSubmit.setTextColor(Color.parseColor("#000000"))
                tvInputAddress.setTextColor(Color.parseColor("#000000"))
            }
        }
    }
}