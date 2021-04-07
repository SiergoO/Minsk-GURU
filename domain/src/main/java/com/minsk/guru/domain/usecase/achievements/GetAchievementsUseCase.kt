package com.minsk.guru.domain.usecase.achievements

import com.minsk.guru.domain.model.Achievements
import com.minsk.guru.domain.repository.achievements.AchievementsRepository

class GetAchievementsUseCase(private val achievementsRepository: AchievementsRepository) {

    suspend fun getAchievements(): Achievements = achievementsRepository.getAchievements()
}