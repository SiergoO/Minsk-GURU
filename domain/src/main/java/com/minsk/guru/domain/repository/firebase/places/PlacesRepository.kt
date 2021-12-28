package com.minsk.guru.domain.repository.firebase.places

import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place

interface PlacesRepository {
    fun getAllPlaces(): List<Place>
    suspend fun getByCategory(categoryName: String?): List<Place> // TODO delete suspend
    fun getCategories(): List<Category>
    fun getPlacesVisitedByUser(userId: String): List<Place>
    fun getVisitedPlacesByCategory(userId: String, categoryName: String): List<Place>
    fun updatePlaceVisitStatus(userId: String, place: Place, isVisited: Boolean)
}