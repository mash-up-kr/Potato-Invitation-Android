package com.mashup.nawainvitation.presentation.imagepicker.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.base.ext.toast
import com.mashup.nawainvitation.data.base.BaseResponse
import com.mashup.nawainvitation.data.repository.InvitationRepository
import com.mashup.nawainvitation.presentation.imagepicker.Constant
import com.mashup.nawainvitation.presentation.main.MainViewModel
import com.mashup.nawainvitation.presentation.main.model.InvitationsData
import gun0912.tedimagepicker.builder.TedRxImagePicker

class ImagePickerViewModel(
    private val repository: InvitationRepository,
    private val mainViewModel: MainViewModel
) : BaseViewModel() {
    private var _enableBtn = MediatorLiveData<Boolean>()
    val enableBtn: MediatorLiveData<Boolean>
        get() = _enableBtn

    private var _imageUriList = MutableLiveData<MutableList<Uri>>()
    val imageUriList: MutableLiveData<MutableList<Uri>>
        get() = _imageUriList

    init {
        _imageUriList.value = arrayListOf()

        _enableBtn.addSource(_imageUriList){
            _enableBtn.postValue(getImageUriCount() > 0)
        }
    }

    fun requestAddImage(context: Context) {
        TedRxImagePicker.with(context)
            .startMultiImage()
            .map { selectedList ->
                // 최대 5개까지 입력가능함으로 이미 add된 이미지 갯수를 뺀 만큼만 add해준다.
                if(getImageUriCount() + selectedList.size > Constant.MAX_IMAGE_COUNT){
                    context.toast("최대 5개까지 추가 가능합니다.")
                }
                // 추가 가능한 이미지 갯수
                val canAddCount = Constant.MAX_IMAGE_COUNT - getImageUriCount()

                // 추가해야하는 이미지 갯수
                val needCount = if(selectedList.size > canAddCount) canAddCount else selectedList.size
                selectedList.take(needCount)
            }
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

    fun getImageUriCount() = _imageUriList.value?.size ?: 0

    fun saveImage(){
        val uriList = _imageUriList.value

        if(uriList.isNullOrEmpty()){
            return
        }

        repository.pathInvitationImages(
            uriList.toImageInfoDataList(),
            mainViewModel.typeData.templateId,
            object : BaseResponse<Any>{
                override fun onSuccess(data: Any) {
                    TODO("Not yet implemented")
                }

                override fun onFail(description: String) {
                    TODO("Not yet implemented")
                }

                override fun onError(throwable: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onLoading() {
                    TODO("Not yet implemented")
                }

                override fun onLoaded() {
                    TODO("Not yet implemented")
                }
            }
        )
    }

    private fun List<Uri>.toImageInfoDataList(): List<InvitationsData.ImageInfoData>{
        val imageInfoDataList = mutableListOf<InvitationsData.ImageInfoData>()

        for(i in this.indices){
            imageInfoDataList.add(InvitationsData.ImageInfoData(i.toLong(), this[i].toString()))
        }
        return imageInfoDataList
    }

}