package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Places
import com.minsk.guru.domain.repository.places.PlacesRepository

class GetPlacesUseCase(private val placesRepository: PlacesRepository) {

    suspend fun getPlaces(): Places = placesRepository.getAll()
}
