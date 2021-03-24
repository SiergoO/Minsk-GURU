package com.minsk.guru.data.repository.api

import com.minsk.guru.data.model.NetPlacesResponse
import com.minsk.guru.domain.api.PlacesApi
import kotlinx.coroutines.coroutineScope

class ApiHelper(private val apiService: PlacesApi) {

    suspend fun getPlaces(): NetPlacesResponse = coroutineScope { apiService.getPlaces("Музеи") }
}