package com.mashup.patatoinvitation.presentation.invitationtitle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.patatoinvitation.base.BaseViewModel
import com.mashup.patatoinvitation.data.base.BaseResponse
import com.mashup.patatoinvitation.data.repository.InvitationRepository

class InvitationTitleViewModel(
    private val repository: InvitationRepository
) : BaseViewModel() {

    val title = MutableLiveData("")
    val description = MutableLiveData("")

    val descriptionCount: LiveData<Int> = Transformations.map(description) { title ->
        title.length
    }

    private val _finishView = MutableLiveData<String>()
    val finishView: LiveData<String> get() = _finishView

    fun saveData() {
        val title = title.value
        val description = description.value

        if (title.isNullOrEmpty()) return

        if (description.isNullOrEmpty()) return

        //TODO deviceId 넣기
        repository.patchInvitationWords("1111", title, description, 0, object : BaseResponse<Any> {
            override fun onSuccess(data: Any) {
                _finishView.postValue("finish")
            }

            override fun onFail(description: String) {

            }

            override fun onError(throwable: Throwable) {

            }

            override fun onLoading() {

            }

            override fun onLoaded() {

            }
        })
    }
}