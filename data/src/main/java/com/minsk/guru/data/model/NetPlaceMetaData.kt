package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.PlaceMetaData

data class NetPlaceMetaData(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("address") val address: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("Phones") val phones: List<NetPhone>,
    @field:SerializedName("Categories") val categories: List<NetCategory>,
    @field:SerializedName("Hours") val hours: NetHours
)

fun NetPlaceMetaData.toDomainModel() = PlaceMetaData(
    id,
    name,
    address,
    url,
    phones.map { it.toDomainModel() },
    categories.map { it.toDomainModel() },
    hours.toDomainModel()
)