package com.minsk.guru.data.api

import com.minsk.guru.data.model.NetPlacesResponse
import kotlinx.coroutines.coroutineScope

class ApiHelper(private val yandexPlacesApi: YandexPlacesApi) {

    suspend fun getPlaces(
        text: String,
        type: String = MapConsts.TYPE,
        lang: String = MapConsts.LANG,
        ll: String = MapConsts.LAT_LONG,
        spn: String = MapConsts.SPN,
        apikey: String = MapConsts.API_KEY
    ): NetPlacesResponse = coroutineScope { yandexPlacesApi.getPlaces(text) }
}