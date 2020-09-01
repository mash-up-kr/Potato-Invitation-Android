package com.mashup.nawainvitation.data.model.response


import com.google.gson.annotations.SerializedName
import com.mashup.nawainvitation.presentation.typechoice.data.TypeData

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
        val isExistInvitation: Boolean,
        @SerializedName("invitationHashCode")
        val invitationHashCode: String,
        @SerializedName("templateId")
        val templateId: Int
    )
}

fun List<InvitationTypeResponse.InvitationTypeItem>.mapToItem() = map {
    TypeData(
        title = it.typeName,
        description = it.typeDescription,
        imageUrl = it.imageUrl,
        isEditing = it.isExistInvitation,
        templateId = it.templateId,
        invitationHashCode = it.invitationHashCode
    )
}