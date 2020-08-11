package com.mashup.patatoinvitation.presentation.searchlocation.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {

    @Headers("Authorization: KakaoAK ${KakaoApiProvider.API_KEY}")
    @GET(KakaoApiProvider.SEARCH_ADDRESS)
    fun getSearchAddress(
        @Query("query") query: String,
        @Query("page") page: Int? = null,
        @Query("AddressSize") addressSize: Int? = null
    ): Call<GetAddressResponse> //주소 검색

    @Headers("Authorization: KakaoAK ${KakaoApiProvider.API_KEY}")
    @GET(KakaoApiProvider.GEO_COORD_ADDRESS)
    fun getGeoCoordAddress(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double,
        @Query("input_coord") inputCoord: String
    ): Call<GetAddressResponse> //위도 경도 검색

    @Headers("Authorization: KakaoAK ${KakaoApiProvider.API_KEY}")
    @GET(KakaoApiProvider.SEARCH_KEYWORD)
    fun getSearchKeyword(
        @Query("query") query: String
//        @Query("category_group_code") cgCode: String? = null,
//        @Query("x") longitude: Double? = null,
//        @Query("y") latitude: Double? = null,
//        @Query("radius") radius: Int? = null,
//        @Query("rect") rect: String? = null,
//        @Query("page") page: Int? = null,
//        @Query("size") size: Int? = null,
//        @Query("sort") sort: Int? = null
    ): Single<GetAddressResponse>
}