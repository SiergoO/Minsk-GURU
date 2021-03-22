package com.minsk.guru.data.model

data class NetResponseMetaData(
    val searchRequest: NetSearchRequest,
    val searchResponse: NetSearchResponse
)

fun NetResponseMetaData.toDomainModel() = com.minsk.guru.domain.model.ResponseMetaData(
    searchRequest.toDomainModel(),
    searchResponse.toDomainModel()
)