package com.minsk.guru.domain.model

data class UserCategory(
    val name: String,
    val categoryPlaces: List<Place>,
    val visitedPlaces: List<Place>
)