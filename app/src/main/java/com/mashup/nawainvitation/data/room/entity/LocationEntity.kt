package com.mashup.nawainvitation.data.room.entity

data class LocationEntity(
    val invitationAddressName: String? = null,
    val invitationPlaceName: String? = null,
    val invitationRoadAddressName: String? = null,
    val latitude: Double? = null, // y좌표값
    val longitude: Double? = null  // x좌표값
)
