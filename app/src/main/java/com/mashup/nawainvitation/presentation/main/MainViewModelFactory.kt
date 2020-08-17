package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.presentation.typechoice.data.TypeData

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