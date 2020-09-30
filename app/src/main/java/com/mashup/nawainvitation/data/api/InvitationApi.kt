package com.mashup.nawainvitation.data.api

import com.mashup.nawainvitation.data.model.response.InvitationTypeResponse
import com.mashup.nawainvitation.data.model.response.InvitationsResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface InvitationApi {

    @GET("template-types")
    fun getTemplateTypes(): Single<InvitationTypeResponse>

    //@POST("invitations")
    //fun postInvitations(@Body request: InvitationsRequest): Single<InvitationsResponse>

    @Multipart
    @POST("invitations")
    fun postInvitations(
        @Part("templateId") templateId: RequestBody,
        @Part("invitationTitle") invitationTitle: RequestBody,
        @Part("invitationContents") invitationContents: RequestBody,
        @Part("invitationTime") invitationTime: RequestBody,
        @Part("invitationAddressName") invitationAddressName: RequestBody,
        @Part("invitationRoadAddressName") invitationRoadAddressName: RequestBody,
        @Part("invitationPlaceName") invitationPlaceName: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody
    ): Single<InvitationsResponse>
}
