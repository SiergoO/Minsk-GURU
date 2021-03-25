package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.repository.places.PlacesRepository

class GetPlacesUseCase(private val placesRepository: PlacesRepository) {

    suspend fun getPlaces(
        text: String,
    ): List<Place> {
        placesRepository.loadAndSave(text)
        return placesRepository.getAll()
    }
}

