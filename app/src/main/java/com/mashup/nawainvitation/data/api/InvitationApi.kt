package com.mashup.nawainvitation.data.api

import com.mashup.nawainvitation.data.model.request.InvitationAddressRequest
import com.mashup.nawainvitation.data.model.request.InvitationTimeRequest
import com.mashup.nawainvitation.data.model.request.InvitationWordsRequest
import com.mashup.nawainvitation.data.model.response.InvitationTypeResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface InvitationApi {

    @GET("template-types-list")
    fun getTemplateTypes(): Single<InvitationTypeResponse>

    @GET("invitations/{hash-code}")
    fun getInvitations(@Path("hash-code") hashCode: String): Single<Any> //TODO InvitationsResponse

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