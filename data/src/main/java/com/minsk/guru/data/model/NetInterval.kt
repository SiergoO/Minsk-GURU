package com.minsk.guru.data.model

data class NetInterval(
    val from: String,
    val to: String
)

fun NetInterval.toDomainModel() = com.minsk.guru.domain.model.Interval(from, to)