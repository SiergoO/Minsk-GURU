package com.minsk.guru.domain.repository.places

import com.minsk.guru.domain.model.Place

interface PlacesLocalRepository {
    suspend fun save(places: List<Place>)
    suspend fun getAll(): List<Place>
    suspend fun getByCategory(category: String): List<Place>
}