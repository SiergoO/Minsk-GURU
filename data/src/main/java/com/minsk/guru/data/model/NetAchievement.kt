package com.minsk.guru.data.model

import com.minsk.guru.domain.model.remote.RemoteAchievement

data class NetAchievement(
    val category: String,
    val count: Int,
    val description: String,
    val name: String,
    val placesIds: List<String>
)

fun NetAchievement.toDomainModel() =
    RemoteAchievement(category, count, description, name, placesIds)