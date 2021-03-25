package com.minsk.guru.domain.api

import com.minsk.guru.domain.model.Places

interface PlacesApi {

    suspend fun getPlaces(
        text: String,
    ): Places

}