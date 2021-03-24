package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Geometry

data class NetGeometry(
    @field:SerializedName("coordinates") val coordinates: List<Double>,
)

fun NetGeometry.toDomainModel() = Geometry(coordinates)