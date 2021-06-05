package com.minsk.guru.domain.repository.achievements

import com.minsk.guru.domain.model.Achievements

interface AchievementsRepository {
    suspend fun getAchievements(): Achievements
}