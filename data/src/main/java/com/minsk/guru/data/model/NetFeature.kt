package com.minsk.guru.data.model

data class NetFeature(
    val geometry: NetGeometry,
    val properties: NetProperties,
    val type: String
)

fun NetFeature.toDomainModel() =
    com.minsk.guru.domain.model.Feature(geometry.toDomainModel(), properties.toDomainModel(), type)