package com.minsk.guru.data.model

import com.minsk.guru.domain.model.Phone

data class NetPhone(
    val formatted: String,
    val type: String
)

fun NetPhone.toDomainModel() = Phone(formatted, type)