package com.mashup.nawainvitation.presentation.main.model

import com.mashup.nawainvitation.data.room.entity.InvitationEntity
import com.mashup.nawainvitation.data.room.typeadpter.ImageListTypeAdapter

data class InvitationsData(
    val templateBackgroundImageUrl: String?,
    val templateTypeDescription: String?,

    val invitationTitle: String?,
    val invitationContents: String?,
    val invitationTime: String?,
    val invitationPlaceName: String?,

    val mapInfo: MapInfoData?,

    val invitationImages: List<ImageInfoData>?
) {
    data class MapInfoData(
        val invitationAddressName: String?,
        val invitationRoadAddressName: String?,
        val longitude: Double?,
        val latitude: Double?
    )

    data class ImageInfoData(
        val id: Long?,
        val imageUri: String?
    )
}

fun InvitationEntity.mapToPresentation() = InvitationsData(
    templateBackgroundImageUrl = templateBackgroundImageUrl,
    templateTypeDescription = templateTypeDescription,

    invitationTitle = invitationTitle,
    invitationContents = invitationContents,
    invitationTime = invitationTime,
    invitationPlaceName = locationEntity?.invitationPlaceName,

    mapInfo = InvitationsData.MapInfoData(
        invitationAddressName = locationEntity?.invitationAddressName,
        invitationRoadAddressName = locationEntity?.invitationRoadAddressName,
        longitude = locationEntity?.longitude,
        latitude = locationEntity?.latitude
    ),

    invitationImages = ImageListTypeAdapter.jsonStringToImageList(images)
)


