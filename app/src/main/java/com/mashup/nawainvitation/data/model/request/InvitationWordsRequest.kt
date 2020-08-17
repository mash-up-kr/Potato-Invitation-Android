package com.mashup.nawainvitation.data.model.request

data class InvitationWordsRequest(
    val invitationTitle: String,
    val invitationContents: String,
    val templatesId: Int
)