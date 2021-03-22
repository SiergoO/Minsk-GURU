package com.minsk.guru.data.model

data class NetProperties(
    val companyMetaData: NetCompanyMetaData,
    val boundedBy: List<List<Double>>,
    val description: String,
    val name: String
)

fun NetProperties.toDomainModel() = com.minsk.guru.domain.model.Properties(companyMetaData.toDomainModel(), boundedBy, description, name)