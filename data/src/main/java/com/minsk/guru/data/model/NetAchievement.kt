package com.minsk.guru.data.model

import com.minsk.guru.domain.model.firebase.FirebaseAchievement

data class NetAchievement(
    val category: String,
    val count: Int,
    val description: String,
    val name: String,
    val placesIds: List<String>
)

fun NetAchievement.toDomainModel() =
    FirebaseAchievement(category, count, description, name, placesIds)