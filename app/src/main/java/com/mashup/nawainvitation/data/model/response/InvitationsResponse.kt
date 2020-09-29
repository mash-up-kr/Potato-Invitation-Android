package com.mashup.nawainvitation.data.model.response

import com.google.gson.annotations.SerializedName

data class InvitationsResponse(
    @SerializedName("invitationHashCode")
    val invitationHashCode: String?
)
