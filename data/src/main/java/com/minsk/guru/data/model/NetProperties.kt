package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Properties

data class NetProperties(
    @field:SerializedName("CompanyMetaData") val placeMetaData: NetPlaceMetaData?,
)

fun NetProperties.toDomainModel() = Properties(placeMetaData?.toDomainModel())