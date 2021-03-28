package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.repository.places.PlacesRepository

class GetPlacesUseCase(private val placesRepository: PlacesRepository) {

    suspend fun getPlaces(
        categoryNames: List<String>,
    ): List<Place> {
        if (placesRepository.isNeedToLoadData()) {
            placesRepository.loadAndSave(categoryNames)
        }
        return placesRepository.getAll()
    }
}
