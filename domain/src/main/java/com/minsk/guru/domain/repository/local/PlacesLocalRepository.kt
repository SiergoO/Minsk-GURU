package com.minsk.guru.domain.repository.local

import com.minsk.guru.domain.model.Place

interface PlacesLocalRepository {
    fun getPlaces(): List<Place>
    fun getPlaceById(id: Int): Place
    fun getPlacesByCategory(categoryName: String?): List<Place>
    fun updatePlace(place: Place)
    fun insertPlace(place: Place)
    fun deletePlace(place: Place)
}