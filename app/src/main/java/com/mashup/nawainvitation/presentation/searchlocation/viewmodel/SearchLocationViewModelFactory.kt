package com.mashup.nawainvitation.presentation.searchlocation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchLocationViewModelFactory(val listener: SearchLocationViewModel.SearchListener) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchLocationViewModel::class.java)) {
            SearchLocationViewModel(listener) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}