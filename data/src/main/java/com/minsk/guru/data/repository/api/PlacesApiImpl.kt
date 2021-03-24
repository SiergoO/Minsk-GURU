package com.minsk.guru.data.repository.api

import com.minsk.guru.data.model.toDomainModel
import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.model.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesApiImpl(private val apiHelper: ApiHelper) : PlacesApi {

    override suspend fun getPlaces(
        text: String,
        type: String,
        lang: String,
        ll: String,
        spn: String,
        apikey: String
    ): PlacesResponse = withContext(Dispatchers.IO) {
        apiHelper.getPlaces().toDomainModel()
    }
}