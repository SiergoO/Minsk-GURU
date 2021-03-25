package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Places

data class NetPlacesResponse(
    @field:SerializedName("features") val places: List<NetPlace>,
)

fun NetPlacesResponse.toDomainModel() = Places(places.map { it.toDomainModel() })