package com.mashup.nawainvitation.data.model.response


import com.google.gson.annotations.SerializedName

data class InvitationsResponse(
    @SerializedName("invitationContents")
    val invitationContents: String?,
    @SerializedName("invitationPlaceName")
    val invitationPlaceName: String?,
    @SerializedName("invitationTime")
    val invitationTime: String?,
    @SerializedName("invitationTitle")
    val invitationTitle: String?,
    @SerializedName("mapInfo")
    val mapInfo: MapInfo?,
    @SerializedName("templateBackgroundImageUrl")
    val templateBackgroundImageUrl: String?,
    @SerializedName("templateTypeDescription")
    val templateTypeDescription: String?
) {
    data class MapInfo(
        @SerializedName("invitationAddressName")
        val invitationAddressName: String?,
        @SerializedName("invitationRoadAddressName")
        val invitationRoadAddressName: String?,
        @SerializedName("x")
        val x: Double?,
        @SerializedName("y")
        val y: Double?
    )
}
