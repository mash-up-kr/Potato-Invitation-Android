package com.mashup.nawainvitation.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.data.repository.InvitationRepository

class MainViewModelFactory(
    private val invitationRepository: InvitationRepository,
    private val listener: MainViewModel.MainListener
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(invitationRepository, listener) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}