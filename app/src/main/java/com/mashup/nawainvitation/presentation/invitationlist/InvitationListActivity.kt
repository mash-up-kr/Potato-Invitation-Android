package com.mashup.nawainvitation.presentation.invitationlist

import android.os.Bundle
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.databinding.ActivityInvitationListBinding
import com.mashup.nawainvitation.presentation.main.MainActivity
import com.mashup.nawainvitation.presentation.main.model.TypeItem

class InvitationListActivity :
    BaseActivity<ActivityInvitationListBinding>(R.layout.activity_invitation_list) {

    private val repository by lazy {
        Injection.provideInvitationRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository.getAllTypes(object : BaseResponse<List<TypeItem>> {
            override fun onSuccess(data: List<TypeItem>) {
                MainActivity.startMainActivity(this@InvitationListActivity, data)
                finish()
            }

            override fun onFail(description: String) {
                //..
            }

            override fun onError(throwable: Throwable) {
                //..
            }

            override fun onLoading() {
                //..
            }

            override fun onLoaded() {
                //..
            }
        })


    }
}