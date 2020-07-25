package com.mashup.patatoinvitation.presentation.searchaddress.model

import com.google.gson.annotations.SerializedName

data class RegionInfo(
    val keyword: String,
    @SerializedName("selected_region") val selectedRegion: String
)