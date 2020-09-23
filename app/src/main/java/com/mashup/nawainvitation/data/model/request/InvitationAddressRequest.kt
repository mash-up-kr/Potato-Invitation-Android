package com.mashup.nawainvitation.data.model.request

data class InvitationAddressRequest(
    val invitationAddressName: String?,
    val invitationPlaceName: String?,
    val invitationRoadAddressName: String?,
    val templatesId: Int,
    val x: Double?,
    val y: Double?
)