package com.minsk.guru.domain.repository.places

import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.model.Place

interface PlacesRepository {

    val localRepository: PlacesLocalRepository
    val placesApi: PlacesApi

    suspend fun loadAndSave(query: String)
    suspend fun getAll(): List<Place>
    suspend fun getByCategory(category: String): List<Place>
}