package com.mashup.nawainvitation.imagepicker

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class ImagePickerViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}