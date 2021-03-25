package com.minsk.guru.data.model

import com.google.gson.annotations.SerializedName
import com.minsk.guru.domain.model.Category

data class NetCategory(
    @field:SerializedName("class") val `class`: String,
    @field:SerializedName("name") val name: String
)

fun NetCategory.toDomainModel() = Category(`class`, name)