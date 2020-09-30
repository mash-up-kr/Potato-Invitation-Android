package com.mashup.nawainvitation.presentation.typechoice.model

import android.os.Parcelable
import com.mashup.nawainvitation.data.model.response.InvitationTypeResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeData(
    val title: String,
    val imageUrl: String,
    val description: String,

    // 작성중인 초대장이 있는지 여부
    val isEditing: Boolean,

    // 템플릿 정보
    val templateId: Int
) : Parcelable

fun InvitationTypeResponse.InvitationTypeItem.mapToPresentation(isEditing: Boolean) =
    TypeData(
        title = typeName,
        description = typeDescription,
        imageUrl = imageUrl,
        isEditing = isEditing,
        templateId = templateId
    )
