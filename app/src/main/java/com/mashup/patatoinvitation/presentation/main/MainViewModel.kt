package com.mashup.patatoinvitation.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.patatoinvitation.base.BaseViewModel
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData

class MainViewModel(
    val listener: MainListener,
    val typeData: TypeData
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
        fun goToInvitationTitle()

        fun goToInvitationDate()

        fun goToInvitationLocation()

        fun goToInvitationPhoto()

        fun goToPreview()
    }
}