package com.mashup.nawainvitation.presentation.imagepicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.SearchLocationViewModel

class ImagePickerViewModelFactory() :
ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ImagePickerViewModel::class.java)) {
            ImagePickerViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}