package com.mashup.nawainvitation.presentation.invitationinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.MainViewModel

class InvitationInfoViewModelFactory(
    private val repository: InvitationRepository,
    private val mainViewModel: MainViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(InvitationInfoViewModel::class.java)) {
            InvitationInfoViewModel(repository, mainViewModel) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}