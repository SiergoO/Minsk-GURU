package com.minsk.guru.domain.repository.firebase.places

import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place

interface PlacesRepository {
    suspend fun getAll(): List<Place> // TODO delete suspend
    suspend fun getByCategory(categoryName: String?): List<Place> // TODO delete suspend
    fun getCategories(): List<Category>
}