package com.minsk.guru.data.model

data class NetGeometry(
    val coordinates: List<Double>,
    val type: String
)

fun NetGeometry.toDomainModel() = com.minsk.guru.domain.model.Geometry(coordinates, type)