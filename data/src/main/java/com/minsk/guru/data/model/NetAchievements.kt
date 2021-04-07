package com.minsk.guru.data.model

import com.minsk.guru.domain.model.Achievements

data class NetAchievements(
    val achievements: MutableList<NetAchievement>
)

fun NetAchievements.toDomainModel() =
    Achievements(achievements.map { it.toDomainModel() }.toMutableList())