package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.model.PlacesResponse

class GetPlacesUseCase(private val placesApi: PlacesApi) {

    suspend fun getPlaces(text: String): PlacesResponse = placesApi.getPlaces(text)
}