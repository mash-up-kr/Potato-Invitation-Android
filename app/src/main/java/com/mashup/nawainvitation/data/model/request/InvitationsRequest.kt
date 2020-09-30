package com.mashup.nawainvitation.data.model.request


data class InvitationsRequest(
    val templateId: Long,
    val invitationTitle: String?,
    val invitationContents: String?,
    val invitationTime: String?,
    val invitationAddressName: String?,
    val invitationRoadAddressName: String?,
    val invitationPlaceName: String?,
    val latitude: Double?,
    val longitude: Double?
)
