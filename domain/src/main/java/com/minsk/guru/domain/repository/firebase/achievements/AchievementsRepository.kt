package com.minsk.guru.domain.repository.firebase.achievements

import com.minsk.guru.domain.model.Achievement

interface AchievementsRepository {
    fun getRemoteAchievements(): List<Achievement>
}