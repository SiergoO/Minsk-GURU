package com.minsk.guru.data.repository.places

import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.repository.places.PlacesLocalRepository
import com.minsk.guru.domain.repository.places.PlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepositoryImpl(
    override val placesApi: PlacesApi,
    override val localRepository: PlacesLocalRepository
) : PlacesRepository {

    override suspend fun isNeedToLoadData(): Boolean = localRepository.isDatabaseEmpty()

    override suspend fun loadAndSave(categoryNames: List<String>) = withContext(Dispatchers.IO) {
        val places: MutableList<Place> = mutableListOf()
        for (element in categoryNames) {
            placesApi.getPlaces(element).places.forEach { places.add(it) }
        }
        localRepository.save(places)
    }

    override suspend fun getAll(): List<Place> =
        withContext(Dispatchers.IO) { localRepository.getAll() }

    override suspend fun getByCategory(category: String): List<Place> =
        withContext(Dispatchers.IO) { localRepository.getByCategory(category) }

}