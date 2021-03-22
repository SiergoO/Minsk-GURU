package com.minsk.guru.data.model

data class NetCategory(
    val `class`: String,
    val name: String
)

fun NetCategory.toDomainModel() = com.minsk.guru.domain.model.Category(`class`, name)