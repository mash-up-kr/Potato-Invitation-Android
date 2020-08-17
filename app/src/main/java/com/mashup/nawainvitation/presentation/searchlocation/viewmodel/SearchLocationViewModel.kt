package com.mashup.nawainvitation.presentation.searchlocation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mashup.nawainvitation.base.BaseViewModel
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import com.mashup.nawainvitation.presentation.searchlocation.repository.LocationRepository

class SearchLocationViewModel(val listener: SearchListener) : BaseViewModel() {
    private val mRepository by lazy { LocationRepository() }
    private val tempList = mutableListOf<Documents>()
    private val _isDataExists = MutableLiveData(false)

    val locationList = MutableLiveData<List<Documents>>()

    fun getLocationList(keyword: String) {
        mRepository.getLocationResponse(keyword).subscribe(
            {
                tempList.run {
                    add(Documents("", "", keyword))
                    it.documents.forEach { doc -> this.add(doc) }
                }
                setLocationList()
            },
            {
                //error
            }
        ).apply { compositeDisposable.add(this) }
    }

    private fun setLocationList() {
        locationList.value = tempList
        _isDataExists.value = true
    }

    fun clearLocationList() {
        tempList.clear()
        locationList.value = tempList
    }

    interface SearchListener {
        fun goToInput()
    }

}