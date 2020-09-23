package com.mashup.nawainvitation.presentation.searchlocation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InputLocationViewModelFactory(val listener: InputLocationViewModel.InputListener) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(InputLocationViewModel::class.java)) {
            InputLocationViewModel(listener) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}