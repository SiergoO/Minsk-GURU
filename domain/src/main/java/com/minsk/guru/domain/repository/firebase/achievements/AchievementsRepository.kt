package com.minsk.guru.domain.repository.firebase.achievements

import com.minsk.guru.domain.model.Achievements

interface AchievementsRepository {
    suspend fun getAchievements(): Achievements
}