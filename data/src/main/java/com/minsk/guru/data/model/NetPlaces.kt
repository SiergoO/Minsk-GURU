package com.minsk.guru.data.model

import com.minsk.guru.domain.model.remote.RemotePlaces

data class NetPlaces(
    val places: MutableList<NetPlace>
)

fun NetPlaces.toDomainModel() = RemotePlaces(places.map{it.toDomainModel()}.toMutableList())