package com.minsk.guru.data.model

data class NetHours(
    val availabilities: List<NetAvailability>,
    val text: String
)

fun NetHours.toDomainModel() =
    com.minsk.guru.domain.model.Hours(availabilities.map { it.toDomainModel() }, text)