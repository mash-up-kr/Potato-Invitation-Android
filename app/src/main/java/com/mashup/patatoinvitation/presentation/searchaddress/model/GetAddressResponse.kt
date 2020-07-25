package com.mashup.patatoinvitation.presentation.searchaddress.model

import com.google.gson.annotations.SerializedName

data class GetAddressResponse(
    val meta: Meta,
    val documents: MutableList<Documents>
)

data class Meta(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean,
    @SerializedName("same_name") val sameName: RegionInfo
)

data class Documents(
    @SerializedName("address_name") val addressName: String? = "",
    @SerializedName("road_address_name") val roadAddressName: String? = "",
    @SerializedName("place_name") val placeName: String,
    val x: Double? = null,
    val y: Double? = null
)
