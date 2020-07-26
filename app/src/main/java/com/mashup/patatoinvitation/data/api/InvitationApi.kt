package com.mashup.patatoinvitation.data.api

import com.mashup.patatoinvitation.data.model.request.InvitationAddressRequest
import com.mashup.patatoinvitation.data.model.request.InvitationTimeRequest
import com.mashup.patatoinvitation.data.model.request.InvitationWordsRequest
import com.mashup.patatoinvitation.data.model.response.InvitationTypeResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface InvitationApi {

    @GET("template-types-list")
    fun getTemplateTypes(
        @Header("deviceIdentifier") deviceIdentifier: String
    ): Single<InvitationTypeResponse>

    @PATCH("invitation/words")
    fun patchInvitationWords(
        @Header("deviceIdentifier") deviceIdentifier: String,
        @Body request: InvitationWordsRequest
    ): Single<Response<Void>>

    @PATCH("invitation/time")
    fun patchInvitationTime(
        @Header("deviceIdentifier") deviceIdentifier: String,
        @Body request: InvitationTimeRequest
    ): Single<Response<Void>>

    @PATCH("invitation/address")
    fun patchInvitationAddress(
        @Header("deviceIdentifier") deviceIdentifier: String,
        @Body request: InvitationAddressRequest
    ): Single<Response<Void>>
}