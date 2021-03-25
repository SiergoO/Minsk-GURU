package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.model.Places

class GetPlacesUseCase(private val placesApi: PlacesApi) {

    suspend fun getPlaces(
        text: String,
    ): Places = placesApi.getPlaces(text)
}