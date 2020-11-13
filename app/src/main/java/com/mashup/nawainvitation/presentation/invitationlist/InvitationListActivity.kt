package com.mashup.nawainvitation.presentation.invitationlist

import android.os.Bundle
import com.mashup.nawainvitation.R
import com.mashup.nawainvitation.base.BaseActivity
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.injection.Injection
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.databinding.ActivityInvitationListBinding
import com.mashup.nawainvitation.presentation.main.MainActivity
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class InvitationListActivity :
    BaseActivity<ActivityInvitationListBinding>(R.layout.activity_invitation_list) {

    private val repository: InvitationRepository by lazy {
        Injection.provideInvitationRepository()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository.getAllTypes(object : BaseResponse<List<TypeItem>> {
            override fun onSuccess(data: List<TypeItem>) {
                //TODO [godjoy] MainActivity 넘어갈 때 typeItem을 같이 넘겨주세요
                MainActivity.startMainActivity(this@InvitationListActivity, data)
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


        //TODO [godjoy] 공유했던 초대장 가져오기 API
        /**
         * Room에 저장되어 있는 모든 초대장을 가져옵니다.
         * Flowable 형식으로 받아오게 해놨지만 필요에 따라 변경해서 사용해 주세요.
         * 초대장 중에서 공유했던 초대장은 hashCode 값을 가지고 있으므로 filter 혹은 쿼리문을 통해 내가 생성했던 초대장을 가져올 수 있습니다.
         */
        repository.getInvitations().subscribe({ items ->
            val sharedItems = items.filter { it.hashcode.isNullOrEmpty().not() }
            Dlog.d("sharedItems : $sharedItems")
        }) {
            Dlog.e(it.message)
        }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}