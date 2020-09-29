package com.mashup.nawainvitation.data.api

import com.mashup.nawainvitation.data.model.request.InvitationsRequest
import com.mashup.nawainvitation.data.model.response.InvitationTypeResponse
import com.mashup.nawainvitation.data.model.response.InvitationsResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InvitationApi {

    @GET("template-types")
    fun getTemplateTypes(): Single<InvitationTypeResponse>

    @POST("invitations")
    fun postInvitations(@Body request: InvitationsRequest): Single<InvitationsResponse>
}
