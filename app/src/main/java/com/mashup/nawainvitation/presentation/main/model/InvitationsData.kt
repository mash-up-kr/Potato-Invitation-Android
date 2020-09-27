package com.mashup.nawainvitation.presentation.main.model

import com.mashup.nawainvitation.data.room.entity.InvitationEntity

data class InvitationsData(
    val invitationContents: String?,
    val invitationPlaceName: String?,
    val invitationTime: String?,
    val invitationTitle: String?,
    val mapInfo: MapInfoData?,
    val templateBackgroundImageUrl: String?,
    val templateTypeDescription: String?
)
{
    data class MapInfoData(
        val invitationAddressName: String?,
        val invitationRoadAddressName: String?,
        val x: Double?,
        val y: Double?
    )
}

fun InvitationEntity.mapToPresentation() = InvitationsData(
    invitationContents = invitationContents,
    invitationPlaceName = locationEntity?.invitationPlaceName,
    invitationTime = invitationTime,
    invitationTitle = invitationTitle,
    mapInfo = InvitationsData.MapInfoData(
        invitationAddressName = locationEntity?.invitationAddressName,
        invitationRoadAddressName = locationEntity?.invitationRoadAddressName,
        x = locationEntity?.longitude,
        y = locationEntity?.latitude
    ),
    templateBackgroundImageUrl = images?.templateBackgroundImageUrl,
    templateTypeDescription = images?.templateTypeDescription
)