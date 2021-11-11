package com.minsk.guru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias DomainPlace = com.minsk.guru.domain.model.Place
typealias UiPlace = Place

@Parcelize
data class Place(
    val address: String,
    val category: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val openingHours: String,
    val phone: String,
    val url: String,
) : Parcelable

fun DomainPlace.toUiModel() = UiPlace(
    address, category, id, latitude, longitude, name, openingHours, phone, url
)

fun UiPlace.toDomainModel() = DomainPlace(
    address, category, id, latitude, longitude, name, openingHours, phone, url
)