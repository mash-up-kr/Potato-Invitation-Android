package com.mashup.nawainvitation.data.model.request

data class InvitationAddressRequest(
    val deviceIdentifier: String,
    val invitationLatitude: Double,
    val invitationLongitude: Double,
    val invitationAddress: String,
    val templatesId: Int
)