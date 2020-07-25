package com.mashup.patatoinvitation.data.model.response


import com.google.gson.annotations.SerializedName

data class InvitationTypeResponse(
    @SerializedName("invitationTypeItemList")
    val invitationTypeItemList: List<InvitationTypeItem>
) {
    data class InvitationTypeItem(
        @SerializedName("typeName")
        val typeName: String,
        @SerializedName("typeDescription")
        val typeDescription: String,
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("isExistInvitation")
        val isExistInvitation: Boolean
    )
}