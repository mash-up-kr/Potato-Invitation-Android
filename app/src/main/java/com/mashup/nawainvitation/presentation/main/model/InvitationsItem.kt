package com.mashup.nawainvitation.presentation.main.model

import com.mashup.nawainvitation.data.room.entity.InvitationEntityV2
import com.mashup.nawainvitation.data.room.typeadpter.ImageListTypeAdapter

data class InvitationsItem(
    val templateId: Int?,
    val invitationTitle: String?,
    val invitationContents: String?,
    val invitationTime: String?,
    val invitationPlaceName: String?,

    val mapInfo: MapInfoItem?,
    val invitationImages: List<ImageInfoItem>?,
    val hashcode: String?
)

data class MapInfoItem(
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
    invitationTitle = invitationTitle,
    invitationContents = invitationContents,
    invitationTime = invitationTime,
    invitationPlaceName = locationEntity?.invitationPlaceName,

    mapInfo = MapInfoItem(
        invitationAddressName = locationEntity?.invitationAddressName,
        invitationRoadAddressName = locationEntity?.invitationRoadAddressName,
        longitude = locationEntity?.longitude,
        latitude = locationEntity?.latitude
    ),

    invitationImages = ImageListTypeAdapter.jsonStringToImageList(images),

    hashcode = hashCode
)


