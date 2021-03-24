package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Interval

data class NetInterval(
    @field:SerializedName("from") val from: String,
    @field:SerializedName("to") val to: String
)

fun NetInterval.toDomainModel() = Interval(from, to)