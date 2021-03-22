package com.minsk.guru.data.model

data class NetPlaces(
    val features: List<NetFeature>,
    val properties: NetPropertiesX,
    val type: String
)

fun NetPlaces.toDomainModel() = com.minsk.guru.domain.model.Places(
    features.map { it.toDomainModel() },
    properties.toDomainModel(),
    type
)