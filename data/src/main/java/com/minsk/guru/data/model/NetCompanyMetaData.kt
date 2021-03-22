package com.minsk.guru.data.model

data class NetCompanyMetaData(
    val categories: List<NetCategory>,
    val hours: NetHours,
    val phones: List<NetPhone>,
    val address: String,
    val id: String,
    val name: String,
    val url: String
)

fun NetCompanyMetaData.toDomainModel() = com.minsk.guru.domain.model.CompanyMetaData(
    categories.map { it.toDomainModel() },
    hours.toDomainModel(),
    phones.map { it.toDomainModel() },
    address,
    id,
    name,
    url
)