package com.mashup.nawainvitation.data.room.entity

data class LocationEntity(
    val invitationAddressName: String?,
    val invitationPlaceName: String?,
    val invitationRoadAddressName: String?,
    val latitude: Double?, // y좌표값
    val longitude: Double?  // x좌표값
)