package com.mashup.patatoinvitation.presentation.invitationtitle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.patatoinvitation.base.BaseViewModel

class InvitationTitleViewModel : BaseViewModel() {

    val title = MutableLiveData("")
    val description = MutableLiveData("")

    val descriptionCount: LiveData<Int> = Transformations.map(description) { title ->
        title.length
    }
}