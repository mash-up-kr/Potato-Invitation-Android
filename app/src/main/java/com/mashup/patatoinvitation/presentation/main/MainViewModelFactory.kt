package com.mashup.patatoinvitation.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val listener: MainViewModel.MainListener
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(listener) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}