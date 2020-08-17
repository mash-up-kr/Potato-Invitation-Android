package com.mashup.nawainvitation.presentation.invitationinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.MainViewModel

class InvitationInfoViewModel(
    private val repository: InvitationRepository,
    private val mainViewModel: MainViewModel
) : BaseViewModel() {

    val title = MutableLiveData("")
    val description = MutableLiveData("")

    val descriptionCount: LiveData<Int> = Transformations.map(description) { title ->
        title.length
    }

    fun saveData() {
        val title = title.value
        val description = description.value

        if (title.isNullOrEmpty()) return

        if (description.isNullOrEmpty()) return

        repository.patchInvitationWords(
            title,
            description,
            mainViewModel.templateId,
            object : BaseResponse<Any> {
                override fun onSuccess(data: Any) {
                    mainViewModel.listener.goToInvitationMain()
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