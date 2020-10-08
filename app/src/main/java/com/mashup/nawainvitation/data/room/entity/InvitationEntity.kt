package com.mashup.nawainvitation.data.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mashup.nawainvitation.data.room.typeadpter.ImageListTypeAdapter
import com.mashup.nawainvitation.presentation.main.model.InvitationsData

@Entity(tableName = "invitation")
data class InvitationEntity(
    @PrimaryKey val templateId: Int,
    val templateBackgroundImageUrl: String? = null,
    val templateTypeDescription: String? = null,
    val invitationTitle: String? = null,
    val invitationContents: String? = null,
    val invitationTime: String? = null,
    @Embedded val locationEntity: LocationEntity? = null,
    val images: String? = null
)
