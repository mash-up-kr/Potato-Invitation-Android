package com.mashup.nawainvitation.presentation.invitationinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.util.Dlog
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.main.MainViewModel

class InvitationInfoViewModel(
    private val repository: InvitationRepository,
    private val mainViewModel: MainViewModel
) : BaseViewModel() {

    val title = MutableLiveData("")
    val contents = MutableLiveData("")
    val enableBtn = MediatorLiveData<Boolean>()

    val contentsCount: LiveData<Int> = Transformations.map(contents) { title ->
        title.length
    }

    val limitCount = 100

    init {
        enableBtn.addSource(title) {
            enableBtn.value = (title.value.isNullOrEmpty() || contents.value.isNullOrEmpty()).not()
        }
        enableBtn.addSource(contents) {
            enableBtn.value = (title.value.isNullOrEmpty() || contents.value.isNullOrEmpty()).not()
        }

        repository.getLatestInvitation().subscribe({
            val mTitle = it?.invitationTitle
            if (mTitle.isNullOrEmpty().not()) title.postValue(mTitle)

            val mContents = it?.invitationContents
            if (mContents.isNullOrEmpty().not()) contents.postValue(mContents)
        }) {
            Dlog.e(it.message)
        }.addTo(compositeDisposable)
    }

    fun saveData() {
        val mTitle = title.value
        val mContents = contents.value

        if (mTitle.isNullOrEmpty()) return

        if (mContents.isNullOrEmpty()) return

        repository.updateInvitationWords(mTitle, mContents)
        mainViewModel.listener.goToInvitationMain()
    }
}