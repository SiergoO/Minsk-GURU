package com.minsk.guru.data.model

import com.minsk.guru.domain.model.Places

data class NetPlaces(
    val places: MutableList<NetPlace>
)

fun NetPlaces.toDomainModel() = Places(places.map{it.toDomainModel()}.toMutableList())