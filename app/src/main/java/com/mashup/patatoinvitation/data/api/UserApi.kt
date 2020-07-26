package com.mashup.patatoinvitation.data.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("users")
    fun postUsers(
        @Header("deviceIdentifier") deviceIdentifier: String
    ): Single<Response<Void>>
}