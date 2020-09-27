package com.mashup.nawainvitation.data.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invitation")
data class Invitation(
    @PrimaryKey val templateId: Int? = null,
    val invitationTitle: String? = null,
    val invitationContents: String? = null,
    val invitationTime: String? = null,
    @Embedded val location: Location? = null,
    @Embedded val images: Images? = null
)