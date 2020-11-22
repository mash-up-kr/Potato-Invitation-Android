package com.mashup.nawainvitation.presentation.searchlocation.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetAddressResponse(
    val documents: MutableList<Documents>
)

@Parcelize
data class Documents(
    @SerializedName("address_name") val addressName: String? = "",
    @SerializedName("road_address_name") val roadAddressName: String? = "",
    @SerializedName("place_name") val placeName: String?,
    val x: Double? = null, //longitude
    val y: Double? = null //latitude
) : Parcelable
