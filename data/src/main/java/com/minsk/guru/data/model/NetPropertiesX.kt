package com.minsk.guru.data.model

data class NetPropertiesX(
    val responseMetaData: NetResponseMetaData
)

fun NetPropertiesX.toDomainModel() =
    com.minsk.guru.domain.model.PropertiesX(responseMetaData.toDomainModel())