package com.mashup.patatoinvitation.data.model.request

data class InvitationAddressRequest(
    val deviceIdentifier: String,
    val invitationLatitude: Double,
    val invitationLongitude: Double,
    val invitationAddress: String,
    val templatesId: Int
)