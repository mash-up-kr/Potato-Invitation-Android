package com.mashup.patatoinvitation.presentation.searchlocation.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object KakaoApiProvider {
    private const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = "3b56ccc71d41b0dd00f92fd540e90ecf"  //private 하는 방법
    const val SEARCH_ADDRESS = "v2/local/search/address"
    const val GEO_COORD_ADDRESS = "v2/local/geo/coord2address"
    const val SEARCH_KEYWORD = "v2/local/search/keyword"

    val kakaoApiBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getKakaoApi(): KakaoApi = kakaoApiBuilder.create(
        KakaoApi::class.java
    )
}