package com.minsk.guru.data.rest.model

import com.minsk.guru.domain.model.remote.RemoteAchievements
import com.minsk.guru.domain.model.remote.RemoteAchievement

data class NetAchievements(
    val achievements: MutableList<NetAchievement>
)

fun NetAchievements.toDomainModel() =
    RemoteAchievements(achievements.map { it.toDomainModel() }.toList() as ArrayList<RemoteAchievement>)