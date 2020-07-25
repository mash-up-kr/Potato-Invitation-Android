package com.mashup.patatoinvitation.data.api

import com.mashup.patatoinvitation.data.model.InvitationTypeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface InvitationApi {

    @GET("template-types-list")
    fun getTemplateTypes(
        @Query("deviceIdentifier") deviceIdentifier: String
    ): Single<InvitationTypeResponse>
}