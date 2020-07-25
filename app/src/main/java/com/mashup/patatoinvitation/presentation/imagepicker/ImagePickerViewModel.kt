package com.mashup.patatoinvitation.presentation.imagepicker

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.patatoinvitation.base.BaseViewModel
import com.mashup.patatoinvitation.presentation.imagepicker.data.ImageClickData
import io.reactivex.disposables.CompositeDisposable

class ImagePickerViewModel : BaseViewModel() {

    private var _imageUriList = MutableLiveData<MutableList<Uri>>()
    val imageUriList: MutableLiveData<MutableList<Uri>>
    get() = _imageUriList

    fun deleteItem(data: ImageClickData){

    }

}