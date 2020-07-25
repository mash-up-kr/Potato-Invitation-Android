package com.mashup.patatoinvitation.presentation.imagepicker

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.patatoinvitation.base.BaseViewModel
import com.mashup.patatoinvitation.presentation.imagepicker.data.ImageClickData
import gun0912.tedimagepicker.builder.TedRxImagePicker
import io.reactivex.disposables.CompositeDisposable

class ImagePickerViewModel : BaseViewModel() {

    private var _imageUriList = MutableLiveData<MutableList<Uri>>()
    val imageUriList: MutableLiveData<MutableList<Uri>>
        get() = _imageUriList

    init {
        _imageUriList.value = arrayListOf()
    }

    fun requestAddImage(context: Context) {
        TedRxImagePicker.with(context)
            .startMultiImage()
            .subscribe({ uriList ->
                addImageUriList(uriList)
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    fun requestUpdateImage(context: Context, position: Int) {
        TedRxImagePicker.with(context)
            .start()
            .subscribe({ uri ->
                updateImage(uri, position)
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    fun addImageUriList(uriList: List<Uri>) {
        val list = _imageUriList.value ?: return
        list.addAll(uriList)
        _imageUriList.value = list
    }

    fun updateImage(uri: Uri, position: Int) {
        val list = _imageUriList.value ?: return
        list[position] = uri
        _imageUriList.value = list
    }

    fun deleteImage(position: Int) {
        val list = _imageUriList.value ?: return
        list.removeAt(position)
        _imageUriList.value = list
    }

}