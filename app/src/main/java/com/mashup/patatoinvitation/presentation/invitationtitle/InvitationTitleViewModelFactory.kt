package com.mashup.patatoinvitation.presentation.invitationtitle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InvitationTitleViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(InvitationTitleViewModel::class.java)) {
            InvitationTitleViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}