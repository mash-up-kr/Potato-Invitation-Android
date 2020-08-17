package com.mashup.nawainvitation.presentation.searchlocation.repository

import com.mashup.nawainvitation.presentation.searchlocation.api.GetAddressResponse
import com.mashup.nawainvitation.presentation.searchlocation.api.KakaoApiProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LocationRepository {

    private val api = KakaoApiProvider.getKakaoApi()

    fun getLocationResponse(keyword: String): Single<GetAddressResponse> {
        return api.getSearchKeyword(keyword).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}


