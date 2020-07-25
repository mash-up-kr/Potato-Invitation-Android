package com.mashup.patatoinvitation.data.model.request

data class InvitationTimeRequest(
    val deviceIdentifier: String,
    val invitationTime: String,
    val templatesId: Int
)