package com.mashup.nawainvitation.presentation.model

import com.mashup.nawainvitation.data.model.response.InvitationsResponse

data class InvitationsData(
    val invitationContents: String?,
    val invitationPlaceName: String?,
    val invitationTime: String?,
    val invitationTitle: String?,
    val mapInfo: MapInfoData?,
    val templateBackgroundImageUrl: String?,
    val templateTypeDescription: String?
) {
    data class MapInfoData(
        val invitationAddressName: String?,
        val invitationRoadAddressName: String?,
        val x: Double?,
        val y: Double?
    )
}

fun InvitationsResponse.mapToPresentation() = InvitationsData(
    invitationContents = invitationContents,
    invitationPlaceName = invitationPlaceName,
    invitationTime = invitationTime,
    invitationTitle = invitationTitle,
    mapInfo = InvitationsData.MapInfoData(
        invitationAddressName = mapInfo?.invitationAddressName,
        invitationRoadAddressName = mapInfo?.invitationRoadAddressName,
        x = mapInfo?.x,
        y = mapInfo?.y
    ),
    templateBackgroundImageUrl = templateBackgroundImageUrl,
    templateTypeDescription = templateTypeDescription
)