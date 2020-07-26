package com.mashup.patatoinvitation.data.model.request

data class InvitationWordsRequest(
    val invitationTitle: String,
    val invitationContents: String,
    val templatesId: Int
)