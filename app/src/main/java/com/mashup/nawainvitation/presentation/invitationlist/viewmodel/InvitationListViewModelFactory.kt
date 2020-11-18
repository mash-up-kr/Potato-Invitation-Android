package com.mashup.nawainvitation.presentation.invitationlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.data.repository.InvitationRepository

class InvitationListViewModelFactory(
    private val invitationRepository: InvitationRepository,
    private val listener: InvitationListViewModel.InvitationListListener
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(InvitationListViewModel::class.java)) {
            InvitationListViewModel(invitationRepository, listener) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}