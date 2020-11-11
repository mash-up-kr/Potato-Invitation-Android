package com.mashup.nawainvitation.presentation.main.model

import android.os.Parcelable
import com.mashup.nawainvitation.data.model.response.InvitationTypeResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeItem(
    val templateId: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val backgroundImageUrl: String
) : Parcelable

fun InvitationTypeResponse.InvitationTypeItem.mapToPresentation() = TypeItem(
    templateId = templateId,
    title = typeName,
    description = typeDescription.replace("\n", " "),
    imageUrl = imageUrl,
    backgroundImageUrl = templateBackgroundImageUrl
)