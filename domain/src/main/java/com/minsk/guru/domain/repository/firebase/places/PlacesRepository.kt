package com.minsk.guru.domain.repository.firebase.places

import com.minsk.guru.domain.model.Place

interface PlacesRepository {
    suspend fun getAll(): List<Place>
    suspend fun getByCategory(categoryName: String?): List<Place>
}