package com.minsk.guru.data.model

data class NetPhone(
    val formatted: String,
    val type: String
)

fun NetPhone.toDomainModel() = com.minsk.guru.domain.model.Phone(formatted, type)