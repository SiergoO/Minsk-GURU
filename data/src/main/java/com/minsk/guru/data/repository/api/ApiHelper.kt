package com.minsk.guru.data.repository.api

import com.minsk.guru.domain.api.PlacesApi

class ApiHelper(private val apiService: PlacesApi) {

    suspend fun getPlaces() = apiService.getPlaces("Музеи")
}