package com.mashup.nawainvitation.data.room.dao

import androidx.room.*
import com.mashup.nawainvitation.data.room.entity.InvitationEntityV2
import com.mashup.nawainvitation.data.room.entity.LocationEntity
import com.mashup.nawainvitation.presentation.main.model.TypeItem
import com.mashup.nawainvitation.presentation.searchlocation.api.Documents
import io.reactivex.Flowable

@Dao
interface InvitationDaoV2 {

    @Query("SELECT * from invitationV2")
    fun getInvitationsRx(): Flowable<List<InvitationEntityV2>>

    @Query("SELECT * from invitationV2")
    fun getInvitations(): List<InvitationEntityV2>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvitation(entity: InvitationEntityV2)

    @Update
    fun updateInvitation(entity: InvitationEntityV2)

    @Transaction
    fun updateWord(title: String, content: String) {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    invitationTitle = title,
                    invitationContents = content
                )
            )
        }
    }

    @Transaction
    fun updateAddress(documents: Documents) {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    locationEntity = LocationEntity(
                        invitationAddressName = documents.addressName,
                        invitationPlaceName = documents.placeName,
                        invitationRoadAddressName = documents.roadAddressName,
                        longitude = documents.x,
                        latitude = documents.y
                    )
                )
            )
        }
    }

    @Transaction
    fun updateTime(time: String) {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    invitationTime = time
                )
            )
        }
    }

    @Transaction
    fun updateImages(images: String?) {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    images = images
                )
            )
        }
    }

    @Transaction
    fun updateHashCodeAndCreatedTime(hashCode: String, createdTime: Long) {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    hashCode = hashCode,
                    createdTime = createdTime
                )
            )
        }
    }

    @Transaction
    fun updateTemplateInfo(templateInfo: TypeItem) {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    templateId = templateInfo.templateId,
                    templateImageUrl = templateInfo.imageUrl,
                    templateBackgroundImageUrl = templateInfo.backgroundImageUrl,
                    templateTypeName = templateInfo.title,
                    templateTypeDescription = templateInfo.description
                )
            )
        }
    }

    @Transaction
    fun deleteAllImage() {
        val items = getInvitations().lastOrNull()
        if (items != null) {
            updateInvitation(
                items.copy(
                    images = null
                )
            )
        }
    }

}
