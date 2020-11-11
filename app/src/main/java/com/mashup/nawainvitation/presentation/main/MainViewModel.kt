package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents

class MainViewModel(
    private val invitationRepository: InvitationRepository,
    val listener: MainListener
) : BaseViewModel() {

    /**
     * @templateId : 1 ~ 5
     */
    private var templateId = -1
    private val currentTypeIndex = MutableLiveData(0)

    fun setTemplateIdFromPos(pos: Int) {
        templateId = pos + 1
        invitationRepository.updateInvitationTemplateId(templateId)
        currentTypeIndex.postValue(pos)
    }

    val currentType = MediatorLiveData<TypeItem?>()
    val enableBtn = MediatorLiveData<Boolean>()

    private val _allTypes = MutableLiveData<List<TypeItem>>(emptyList())
    val allTypes: LiveData<List<TypeItem>> get() = _allTypes

    private val _isTitle = MutableLiveData(false)
    val isTitle: LiveData<Boolean> get() = _isTitle

    private val _isDate = MutableLiveData(false)
    val isDate: LiveData<Boolean> get() = _isDate

    private val _isLocation = MutableLiveData(false)
    val isLocation: LiveData<Boolean> get() = _isLocation

    private val _isPhoto = MutableLiveData(false)
    val isPhoto: LiveData<Boolean> get() = _isPhoto

    init {
        currentType.addSource(currentTypeIndex) { index ->
            currentType.value = _allTypes.value?.getOrNull(index)
        }

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

    fun loadAllTypes() {
        if (templateId > -1) {
            //TODO [LeeJinSeong] templateId 초기화를 어떻게 하면 좋을까?

            return
        }

        invitationRepository.getAllTypes(object : BaseResponse<List<TypeItem>> {
            override fun onSuccess(data: List<TypeItem>) {
                _allTypes.postValue(data)
                templateId = data.first().templateId
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

    fun loadInvitations() {
        Dlog.d("loadInvitations templateId : $templateId")
        invitationRepository.getLatestInvitation()
            .subscribe({ invitation ->
                if (invitation.hashcode != null && invitation.hashcode.isNotEmpty()) {
                    invitationRepository.insertTempInvitation()

                    _isTitle.postValue(false)
                    _isDate.postValue(false)
                    _isLocation.postValue(false)
                    _isPhoto.postValue(false)

                    return@subscribe
                }

                _isTitle.postValue(invitation.invitationContents.isNullOrEmpty().not())
                _isDate.postValue(invitation.invitationTime.isNullOrEmpty().not())
                _isLocation.postValue(invitation.invitationPlaceName.isNullOrEmpty().not())
                _isPhoto.postValue(invitation.invitationImages.isNullOrEmpty().not())
            }) {
                Dlog.e(it.message)
            }.addTo(compositeDisposable)
    }

    fun completeInvitation() {
        if (templateId < 1) {
            return
        }

        invitationRepository.pathInvitation(
            templateId, object : BaseResponse<String> {
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

        fun goToInvitationInputLocation(documents: Documents?)

        fun goToInvitationSearchLocation(documents: Documents?)

        fun goToInvitationPhoto()

        fun goToPreview(hashCode: String)

        fun showLoading()

        fun hideLoading()
    }
}