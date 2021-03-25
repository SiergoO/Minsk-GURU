package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Availability

data class NetAvailability(
    @field:SerializedName("Intervals") val intervals: List<NetInterval>,
    @field:SerializedName("monday") val monday: Boolean?,
    @field:SerializedName("tuesday") val tuesday: Boolean?,
    @field:SerializedName("wednesday") val wednesday: Boolean?,
    @field:SerializedName("thursday") val thursday: Boolean?,
    @field:SerializedName("friday") val friday: Boolean?,
    @field:SerializedName("saturday") val saturday: Boolean?,
    @field:SerializedName("sunday") val sunday: Boolean?,
    @field:SerializedName("everyday") val everyday: Boolean?
)

fun NetAvailability.toDomainModel() = Availability(
    intervals.map { it.toDomainModel() },
    monday,
    tuesday,
    wednesday,
    thursday,
    friday,
    saturday,
    sunday,
    everyday
)