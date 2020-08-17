package com.mashup.nawainvitation.presentation.searchlocation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents

class InputLocationViewModel(val listener: InputListener) : BaseViewModel() {

    val place = MutableLiveData<Documents>()
    private val _placeName = MutableLiveData("")
    val placeName: LiveData<String> get() = _placeName
    private val _isDataExists = MutableLiveData(false)

    fun setLocation(doc: Documents) {
        place.value = doc
        _placeName.value = doc.placeName
    }

    fun dataExists() {
        _isDataExists.value = true
    }

    interface InputListener {
        fun goToSearch()
        fun submit()
    }
}