package com.mashup.nawainvitation.presentation.invitationinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.MainViewModel

class InvitationInfoViewModel(
    private val repository: InvitationRepository,
    private val mainViewModel: MainViewModel
) : BaseViewModel() {

    val title = MutableLiveData("")
    val contents = MutableLiveData("")
    val enableBtn = MediatorLiveData<Boolean>()

    val contentsCount: LiveData<Int> = Transformations.map(contents) { title ->
        title.length
    }

    val limitCount = 100

    init {
        enableBtn.addSource(title) {
            enableBtn.value = (title.value.isNullOrEmpty() || contents.value.isNullOrEmpty()).not()
        }
        enableBtn.addSource(contents) {
            enableBtn.value = (title.value.isNullOrEmpty() || contents.value.isNullOrEmpty()).not()
        }

        mainViewModel.invitations.value?.let {
            val mTitle = it.invitationTitle
            if (mTitle.isNullOrEmpty().not()) title.postValue(mTitle)

            val mContents = it.invitationContents
            if (mContents.isNullOrEmpty().not()) contents.postValue(mContents)
        }
    }

    fun saveData() {
        val mTitle = title.value
        val mContents = contents.value

        if (mTitle.isNullOrEmpty()) return

        if (mContents.isNullOrEmpty()) return

        repository.patchInvitationWords(
            mTitle,
            mContents,
            mainViewModel.typeData.templateId,
            object : BaseResponse<Any> {
                override fun onSuccess(data: Any) {
                    mainViewModel.listener.goToInvitationMain()
                }

                override fun onFail(description: String) {
                    Dlog.e("$description")
                }

                override fun onError(throwable: Throwable) {
                    Dlog.e("${throwable.message}")
                }

                override fun onLoading() {
                    mainViewModel.listener.showLoading()
                }

                override fun onLoaded() {
                    mainViewModel.listener.hideLoading()
                }
            })
    }
}