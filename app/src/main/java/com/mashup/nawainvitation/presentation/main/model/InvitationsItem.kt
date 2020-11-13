package com.mashup.nawainvitation.presentation.main.model

import com.mashup.nawainvitation.data.room.entity.InvitationEntityV2
import com.mashup.nawainvitation.data.room.typeadpter.ImageListTypeAdapter

data class InvitationsItem(
    val templateId: Int?,
    val templateImageUrl: String?,
    val templateBackgroundImageUrl: String?,
    val templateTypeName: String?,
    val templateTypeDescription: String?,

    val invitationTitle: String?,
    val invitationContents: String?,
    val invitationTime: String?,

    val mapInfo: MapInfoItem?,

    val invitationImages: List<ImageInfoItem>?,

    val hashcode: String?,
    val createdTime: Long?
)

data class MapInfoItem(
    val invitationPlaceName: String?,
    val invitationAddressName: String?,
    val invitationRoadAddressName: String?,
    val longitude: Double?,
    val latitude: Double?
)

data class ImageInfoItem(
    val id: Long?,
    val imageUri: String?
)

fun InvitationEntityV2.mapToPresentation() = InvitationsItem(
    templateId = templateId,
    templateImageUrl = templateImageUrl,
    templateBackgroundImageUrl = templateBackgroundImageUrl,
    templateTypeName = templateTypeName,
    templateTypeDescription = templateTypeDescription,

    invitationTitle = invitationTitle,
    invitationContents = invitationContents,
    invitationTime = invitationTime,

    mapInfo = MapInfoItem(
        invitationPlaceName = locationEntity?.invitationPlaceName,
        invitationAddressName = locationEntity?.invitationAddressName,
        invitationRoadAddressName = locationEntity?.invitationRoadAddressName,
        longitude = locationEntity?.longitude,
        latitude = locationEntity?.latitude
    ),

    invitationImages = ImageListTypeAdapter.jsonStringToImageList(images),

    hashcode = hashCode,
    createdTime = createdTime
)


