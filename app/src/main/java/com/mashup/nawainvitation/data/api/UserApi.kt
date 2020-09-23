package com.mashup.nawainvitation.data.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST

interface UserApi {

    @POST("users")
    fun postUsers(): Single<Response<Void>>
}