package com.minsk.guru.data.model

data class NetSearchResponse(
    val boundedBy: List<List<Double>>,
    val display: String,
    val found: Int
)

fun NetSearchResponse.toDomainModel() =
    com.minsk.guru.domain.model.SearchResponse(boundedBy, display, found)