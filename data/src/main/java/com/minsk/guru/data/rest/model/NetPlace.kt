package com.minsk.guru.data.rest.model

import com.minsk.guru.domain.model.Place

data class NetPlace(
    val address: String,
    val category: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val opening_hours: String,
    val phone: String,
    val url: String
)

fun NetPlace.toDomainModel() = Place(address,
    category, id, latitude, longitude, name, opening_hours, phone, url)
