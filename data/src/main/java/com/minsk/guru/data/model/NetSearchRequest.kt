package com.minsk.guru.data.model

data class NetSearchRequest(
    val boundedBy: List<List<Double>>,
    val request: String,
    val results: Int,
    val skip: Int
)

fun NetSearchRequest.toDomainModel() =
    com.minsk.guru.domain.model.SearchRequest(boundedBy, request, results, skip)