package com.minsk.guru.data.model

import com.minsk.guru.domain.model.Achievements
import com.minsk.guru.domain.model.firebase.FirebaseAchievement

data class NetAchievements(
    val achievements: MutableList<NetAchievement>
)

fun NetAchievements.toDomainModel() =
    Achievements(achievements.map { it.toDomainModel() }.toList() as ArrayList<FirebaseAchievement>)