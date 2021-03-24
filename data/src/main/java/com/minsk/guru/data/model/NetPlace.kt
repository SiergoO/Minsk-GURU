package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Place

data class NetPlace(
    @field:SerializedName("geometry") val geometry: NetGeometry,
    @field:SerializedName("properties") val properties: NetProperties
)

fun NetPlace.toDomainModel() = Place(geometry.toDomainModel(), properties.toDomainModel())
