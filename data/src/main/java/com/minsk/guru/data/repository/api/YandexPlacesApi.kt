package com.minsk.guru.data.repository.api

import com.minsk.guru.data.model.NetPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query



interface YandexPlacesApi {

    @GET("/v1")
    suspend fun getPlaces(
        @Query(value = "text") text: String,
        @Query(value = "type") type: String = "biz",
        @Query(value = "lang") lang: String = "ru_RU",
        @Query(value = "ll") ll: String = "27.5441555,53.902141",
        @Query(value = "spn") spn: String = "0.132267,0.138400",
        @Query(value = "results") results: String = "500",
        @Query(value = "apikey") apikey: String = "48f6515a-d9f7-4e5e-97ef-15bdb43c0496"
    ): NetPlacesResponse
}