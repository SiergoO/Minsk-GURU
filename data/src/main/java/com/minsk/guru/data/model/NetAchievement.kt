package com.minsk.guru.data.model

import com.minsk.guru.domain.model.Achievement

data class NetAchievement(
    val category: String,
    val name: String,
    val description: String,
    val count: Int,
    val placesIds: List<String>
)

fun NetAchievement.toDomainModel() =
    Achievement(category, name, description, count, placesIds)