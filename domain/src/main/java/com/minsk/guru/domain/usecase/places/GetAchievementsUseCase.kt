package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Achievements
import com.minsk.guru.domain.repository.places.AchievementsRepository
import com.minsk.guru.domain.repository.places.PlacesRepository

class GetAchievementsUseCase(private val achievementsRepository: AchievementsRepository) {

    suspend fun getAchievements(): Achievements = achievementsRepository.getAchievements()
}