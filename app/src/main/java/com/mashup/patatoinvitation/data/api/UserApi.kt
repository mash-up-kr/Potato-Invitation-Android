package com.mashup.patatoinvitation.data.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users")
    fun getUsers(
        @Query("deviceIdentifier") deviceIdentifier: String
    ): Single<Response<Void>>
}