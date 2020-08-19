package com.mashup.nawainvitation.presentation.searchlocation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.repository.LocationRepository

class SearchLocationViewModel(val listener: SearchListener) : BaseViewModel() {
    private val mRepository by lazy { LocationRepository() }
    private val _isDataExists = MutableLiveData(false)

    val locationList = MutableLiveData<List<Documents>>()

    fun getLocationList(keyword: String) {
        mRepository.getLocationResponse(keyword).subscribe(
            {
                it.documents.let {list->
                    list.add(0, Documents("","", keyword))
                    setLocationList(list)
                }
            },
            {
                //error
            }
        ).apply { compositeDisposable.add(this) }
    }

    private fun setLocationList(data: MutableList<Documents>) {
        locationList.value = data
        _isDataExists.value = true
    }

    fun clearLocationList(data: MutableList<Documents>) {
        locationList.value = data
    }

    interface SearchListener {
        fun goToInput()
    }

}