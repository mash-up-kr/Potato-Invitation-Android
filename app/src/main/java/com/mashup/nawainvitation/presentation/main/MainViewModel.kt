package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.model.response.InvitationsResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.typechoice.data.TypeData

class MainViewModel(
    private val invitationRepository: InvitationRepository,
    val listener: MainListener,
    val typeData: TypeData
) : BaseViewModel() {

    val enableBtn = MediatorLiveData<Boolean>()
    val invitations = MutableLiveData<InvitationsResponse>()

    private val _isTitle = MutableLiveData(false)
    val isTitle: LiveData<Boolean> get() = _isTitle

    private val _isDate = MutableLiveData(false)
    val isDate: LiveData<Boolean> get() = _isDate

    private val _isLocation = MutableLiveData(false)
    val isLocation: LiveData<Boolean> get() = _isLocation

    //TODO photo next step
    private val _isPhoto = MutableLiveData(false)
    val isPhoto: LiveData<Boolean> get() = _isPhoto

    init {
        enableBtn.addSource(isTitle) {
            enableBtn.value = getIsEnableButton()
        }
        enableBtn.addSource(isDate) {
            enableBtn.value = getIsEnableButton()
        }
        enableBtn.addSource(isLocation) {
            enableBtn.value = getIsEnableButton()
        }
    }

    private fun getIsEnableButton() =
        isTitle.value == true && isDate.value == true && isLocation.value == true

    fun loadInvitations() {
        invitationRepository.getInvitations(
            typeData.templateId,
            object : BaseResponse<InvitationsResponse> {
                override fun onSuccess(data: InvitationsResponse) {
                    Dlog.d("data : $data")
                    invitations.postValue(data)

                    _isTitle.postValue(data.invitationContents.isNullOrEmpty().not())
                    _isDate.postValue(data.invitationTime.isNullOrEmpty().not())
                    _isLocation.postValue(data.invitationPlaceName.isNullOrEmpty().not())
                }

                override fun onFail(description: String) {
                Dlog.e("$description")
            }

            override fun onError(throwable: Throwable) {
                Dlog.e("${throwable.message}")
            }

            override fun onLoading() {
                listener.showLoading()
            }

            override fun onLoaded() {
                listener.hideLoading()
            }
        })
    }

    interface MainListener {
        fun goToInvitationMain()

        fun goToInvitationInfo()

        fun goToInvitationDate()

        fun goToInvitationInputLocation(data: Documents?)

        fun goToInvitationSearchLocation()

        fun goToInvitationPhoto()

        fun goToPreview()

        fun showLoading()

        fun hideLoading()
    }
}