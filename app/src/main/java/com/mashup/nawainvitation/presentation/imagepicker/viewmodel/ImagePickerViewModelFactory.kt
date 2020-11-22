package com.mashup.nawainvitation.presentation.imagepicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.searchlocation.viewmodel.SearchLocationViewModel

class ImagePickerViewModelFactory(
    private val repository: InvitationRepository,
    private val mainViewModel: MainViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ImagePickerViewModel::class.java)) {
            ImagePickerViewModel(repository, mainViewModel) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}