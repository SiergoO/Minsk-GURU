package com.minsk.guru.data.api

import com.minsk.guru.data.model.toDomainModel
import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.model.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesApiImpl(private val apiHelper: ApiHelper) : PlacesApi {

    override suspend fun getPlaces(
        text: String,
    ): Places =
        withContext(Dispatchers.IO) {
            apiHelper.getPlaces(text).toDomainModel()
    }
}