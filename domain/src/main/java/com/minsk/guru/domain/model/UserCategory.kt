package com.minsk.guru.domain.model

data class UserCategory(
    val name: String,
    val placesIds: List<String>,
    val visitedPlaces: List<Place>
)