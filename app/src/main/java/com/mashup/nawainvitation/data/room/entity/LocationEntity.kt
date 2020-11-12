package com.mashup.nawainvitation.data.room.entity

data class LocationEntity(
    val invitationAddressName: String? = null,
    val invitationPlaceName: String? = null,
    val invitationRoadAddressName: String? = null,
    val longitude: Double? = null,  // x좌표값
    val latitude: Double? = null // y좌표값
)
