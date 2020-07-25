package com.mashup.patatoinvitation.data.api

import com.mashup.patatoinvitation.data.model.request.InvitationAddressRequest
import com.mashup.patatoinvitation.data.model.request.InvitationTimeRequest
import com.mashup.patatoinvitation.data.model.request.InvitationWordsRequest
import com.mashup.patatoinvitation.data.model.response.InvitationTypeResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface InvitationApi {

    @GET("template-types-list")
    fun getTemplateTypes(
        @Query("deviceIdentifier") deviceIdentifier: String
    ): Single<InvitationTypeResponse>

    @PATCH("invitation/words")
    fun patchInvitationWords(
        @Body request: InvitationWordsRequest
    ): Single<Response<Void>>

    @PATCH("invitation/time")
    fun patchInvitationTime(
        @Body request: InvitationTimeRequest
    ): Single<Response<Void>>

    @PATCH("invitation/address")
    fun patchInvitationAddress(
        @Body request: InvitationAddressRequest
    ): Single<Response<Void>>
}