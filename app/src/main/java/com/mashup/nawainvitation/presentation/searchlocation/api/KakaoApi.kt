package com.mashup.nawainvitation.presentation.searchlocation.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {
    @Headers("Authorization: KakaoAK ${KakaoApiProvider.API_KEY}")
    @GET(KakaoApiProvider.SEARCH_KEYWORD)
    fun getSearchKeyword(
        @Query("query") query: String
    ): Single<GetAddressResponse>
}