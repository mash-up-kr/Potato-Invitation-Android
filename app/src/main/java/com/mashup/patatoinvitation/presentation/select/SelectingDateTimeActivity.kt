package com.mashup.patatoinvitation.presentation.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.patatoinvitation.R
import com.mashup.patatoinvitation.data.injection.Injection
import com.mashup.patatoinvitation.data.repository.InvitationRepository

class SelectingDateTimeActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_TEMPLATE_ID = "template_id"

        fun startSelectingDateActivity(context: Context, templateId: Int) {
            context.startActivity(
                Intent(context, SelectingDateTimeActivity::class.java).apply {
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
        setContentView(R.layout.activity_selectingdatetime)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, SelectingDateTimeFragment())
            .commit()
    }
}
