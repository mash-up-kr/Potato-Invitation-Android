package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.model.InvitationsData
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.typechoice.model.TypeData

class MainViewModel(
        private val invitationRepository: InvitationRepository,
        val listener: MainListener,
        val typeData: TypeData
) : BaseViewModel() {

    val enableBtn = MediatorLiveData<Boolean>()
    val invitations = MutableLiveData<InvitationsData>()

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
        invitationRepository.getInvitation(
            typeData.templateId,
            object : BaseResponse<InvitationsData> {
                override fun onSuccess(data: InvitationsData) {
                    invitations.postValue(data)
                    _isTitle.postValue(data.invitationContents.isNullOrEmpty().not())
                    _isDate.postValue(data.invitationTime.isNullOrEmpty().not())
                    _isLocation.postValue(data.invitationPlaceName.isNullOrEmpty().not())
                }

                override fun onFail(description: String) {
                    Dlog.e("onFail $description")
                }

                override fun onError(throwable: Throwable) {
                    Dlog.e("onError ${throwable.message}")
                }

                override fun onLoading() {
                    listener.showLoading()
                }

                override fun onLoaded() {
                    listener.hideLoading()
                }
            })
    }

    fun completeInvitation() {
        invitationRepository.pathInvitation(
            typeData.templateId,
            object : BaseResponse<String> {
                override fun onSuccess(data: String) {
                    listener.goToPreview(data)
                }

                override fun onFail(description: String) {
                    Dlog.e("onFail $description")
                }

                override fun onError(throwable: Throwable) {
                    Dlog.e("onError ${throwable.message}")
                }

                override fun onLoading() {
                    listener.showLoading()
                }

                override fun onLoaded() {
                    listener.hideLoading()
                }
            }
        )
    }

    interface MainListener {

        fun goToInvitationMain()

        fun goToInvitationInfo()

        fun goToInvitationDate()

        fun goToInvitationInputLocation(data: Documents?)

        fun goToInvitationSearchLocation(data: Documents?)

        fun goToInvitationPhoto()

        fun goToPreview(hashCode: String)

        fun showLoading()

        fun hideLoading()
    }
}