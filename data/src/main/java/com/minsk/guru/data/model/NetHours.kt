package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Hours

data class NetHours(
    @field:SerializedName("text") val text: String,
    @field:SerializedName("Availabilities") val availabilities: List<NetAvailability>?
)

fun NetHours.toDomainModel() = Hours(text, availabilities?.map { it.toDomainModel() })