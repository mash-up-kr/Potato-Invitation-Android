package com.mashup.nawainvitation.data.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invitation")
data class InvitationEntity(
    @PrimaryKey val templateId: Int,
    val templateBackgroundImageUrl: String? = null,
    val templateTypeDescription: String? = null,
    val invitationTitle: String? = null,
    val invitationContents: String? = null,
    val invitationTime: String? = null,
    @Embedded val locationEntity: LocationEntity? = null,
    @Embedded val images: ImagesEntity? = null
)
