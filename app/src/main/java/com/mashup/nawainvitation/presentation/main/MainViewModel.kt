package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel

class MainViewModel(
    val listener: MainListener,
    val templateId: Int
) : BaseViewModel() {

    private val _isTitle = MutableLiveData(false)
    val isTitle: LiveData<Boolean> get() = _isTitle

    private val _isDate = MutableLiveData(false)
    val isDate: LiveData<Boolean> get() = _isDate

    private val _isLocation = MutableLiveData(false)
    val isLocation: LiveData<Boolean> get() = _isLocation

    private val _isPhoto = MutableLiveData(false)
    val isPhoto: LiveData<Boolean> get() = _isPhoto

    interface MainListener {
        fun goToInvitationMain()

        fun goToInvitationInfo()

        fun goToInvitationDate()

        fun goToInvitationInputLocation()

        fun goToInvitationSearchLocation()

        fun goToInvitationPhoto()

        fun goToPreview()

        fun showLoading()

        fun hideLoading()
    }
}