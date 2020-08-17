package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val listener: MainViewModel.MainListener,
    private val templateId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(listener, templateId) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}