package com.mashup.nawainvitation.data.api

import com.mashup.nawainvitation.data.model.request.InvitationAddressRequest
import com.mashup.nawainvitation.data.model.request.InvitationTimeRequest
import com.mashup.nawainvitation.data.model.request.InvitationWordsRequest
import com.mashup.nawainvitation.data.model.response.InvitationTypeResponse
import com.mashup.nawainvitation.data.model.response.InvitationsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface InvitationApi {

    @GET("template-types-list")
    fun getTemplateTypes(): Single<InvitationTypeResponse>

    @GET("invitations")
    fun getInvitations(@Query("template-id") templateId: Int): Single<InvitationsResponse>

    @PATCH("invitation/words")
    fun patchInvitationWords(
        @Body request: InvitationWordsRequest
    ): Single<Response<Void>>

    @PATCH("invitations/time")
    fun patchInvitationTime(
        @Body request: InvitationTimeRequest
    ): Single<Response<Void>>

    @PATCH("invitations/address")
    fun patchInvitationAddress(
        @Body request: InvitationAddressRequest
    ): Single<Response<Void>>
}