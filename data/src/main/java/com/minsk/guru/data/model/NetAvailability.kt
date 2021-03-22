package com.minsk.guru.data.model

data class NetAvailability(
    val friday: Boolean,
    val intervals: List<NetInterval>,
    val monday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,
    val thursday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean
)

fun NetAvailability.toDomainModel() = com.minsk.guru.domain.model.Availability(
    friday,
    intervals.map { it.toDomainModel() },
    monday,
    saturday,
    sunday,
    thursday,
    tuesday,
    wednesday
)