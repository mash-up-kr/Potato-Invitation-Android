package com.mashup.nawainvitation.data.model.response


import com.google.gson.annotations.SerializedName

data class InvitationsResponse(
    @SerializedName("temp")
    val temp: String
)