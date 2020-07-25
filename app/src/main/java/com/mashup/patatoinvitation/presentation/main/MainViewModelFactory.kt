package com.mashup.patatoinvitation.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.patatoinvitation.presentation.typechoice.data.TypeData

class MainViewModelFactory(
    private val listener: MainViewModel.MainListener,
    private val typeData: TypeData
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(listener, typeData) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}