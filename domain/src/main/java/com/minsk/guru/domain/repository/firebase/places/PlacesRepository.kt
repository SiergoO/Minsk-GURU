package com.minsk.guru.domain.repository.firebase.places

import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place

interface PlacesRepository {
    suspend fun getAll(): List<Place> // TODO delete suspend
    suspend fun getByCategory(categoryName: String?): List<Place> // TODO delete suspend
    fun getCategories(): List<Category>
    fun getPlacesVisitedByUser(userId: String): List<Place>
    fun getVisitedPlacesByCategory(userId: String, categoryName: String?): List<Place>
    fun insertVisitedPlace(userId: String, place: Place)
    fun deleteVisitedPlace(userId: String, placeId: String)
    //    fun getPlaces(): List<Place>
//    fun getPlaceById(id: Int): Place
//    fun updatePlace(place: Place)
}