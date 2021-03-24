package com.minsk.guru.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.PlacesResponse

data class NetPlacesResponse(
    @field:SerializedName("type") val type: String,
    @Expose
    @field:SerializedName("properties") val properties: String? = null,
    @field:SerializedName("features") val places: List<NetPlace>,
)

fun NetPlacesResponse.toDomainModel() = PlacesResponse(places.map { it.toDomainModel() })