package com.mashup.nawainvitation.presentation.main.model

import com.mashup.nawainvitation.data.room.entity.Invitation

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

fun Invitation.mapToPresentation() = InvitationsData(
    invitationContents = invitationContents,
    invitationPlaceName = location?.invitationPlaceName,
    invitationTime = invitationTime,
    invitationTitle = invitationTitle,
    mapInfo = InvitationsData.MapInfoData(
        invitationAddressName = location?.invitationAddressName,
        invitationRoadAddressName = location?.invitationRoadAddressName,
        x = location?.longitude,
        y = location?.latitude
    ),
    templateBackgroundImageUrl = images?.templateBackgroundImageUrl,
    templateTypeDescription = images?.templateTypeDescription
)