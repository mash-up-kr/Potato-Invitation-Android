package com.mashup.patatoinvitation.presentation.searchlocation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.patatoinvitation.base.BaseViewModel
import com.mashup.patatoinvitation.presentation.searchlocation.api.Documents
import com.mashup.patatoinvitation.presentation.searchlocation.repository.LocationRepository

class LocationViewModel : BaseViewModel() {
    private val mRepository by lazy { LocationRepository() }
    private val tempList = mutableListOf<Documents>()

    private val _isSearch = MutableLiveData(false)
    val isSearch: LiveData<Boolean> get() = _isSearch
    private val _isSubmit = MutableLiveData(false)
    val isSubmit: LiveData<Boolean> get() = _isSubmit
    private val _isDataExists = MutableLiveData(false)
    private val isDataExists: LiveData<Boolean> get() = _isDataExists
    private val _isItemSelected = MutableLiveData(false)
    val isItemSelected: LiveData<Boolean> get() = _isItemSelected
    val locationList = MutableLiveData<List<Documents>>()
    val place = MutableLiveData<Documents>()
    private val _placeName = MutableLiveData("")
    val placeName: LiveData<String> get() = _placeName

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

    private fun setLocation(doc: Documents) {
        place.value = doc
        _placeName.value = doc.placeName
    }

    fun setClickItem(doc: Documents) {
        _isItemSelected.value = true
        setLocation(doc)
    }

    fun clearLocationList() {
        tempList.clear()
        locationList.value = tempList
    }

    fun clickSearch() {
        _isSearch.value = true
    }

    fun clickSubmit() {
        _isSubmit.value = true
    }

    fun initValue() {
        _isSubmit.value = false
    }
}