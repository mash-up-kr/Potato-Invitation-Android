package com.mashup.nawainvitation.data.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invitationV2")
data class InvitationEntityV2(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val templateId: Int = -1,
    val templateImageUrl: String? = null,
    val templateBackgroundImageUrl: String? = null,
    val templateTypeName: String? = null,
    val templateTypeDescription: String? = null,

    val invitationTitle: String? = null,
    val invitationContents: String? = null,
    val invitationTime: String? = null,
    val images: String? = null,

    @Embedded val locationEntity: LocationEntity? = null,

    val hashCode: String? = null,
    val createdTime: Long? = null // unixTime
)
