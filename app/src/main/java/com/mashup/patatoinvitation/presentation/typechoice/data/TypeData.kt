package com.mashup.patatoinvitation.presentation.typechoice.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeData(
    val title: String,
    val imageUrl: String,
    val description: String,
    // 작성중인 초대장이 있는지 여부
    val isEditing: Boolean,
    val templateId: Int
) : Parcelable