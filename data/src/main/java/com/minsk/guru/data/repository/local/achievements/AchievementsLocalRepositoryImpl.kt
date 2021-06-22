package com.minsk.guru.data.repository.local.achievements

import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.repository.local.AchievementsLocalRepository

class AchievementsLocalRepositoryImpl(private val achievementsDao: AchievementsDao) :
    AchievementsLocalRepository {

    override fun getAchievements(): List<Achievement> =
        achievementsDao.getAchievements().map { it.toDomainAchievement() }

    override fun getAchievementById(id: Int): Achievement =
        achievementsDao.getAchievementById(id).toDomainAchievement()

    override fun updateAchievement(achievement: Achievement) {
        achievementsDao.updateAchievement(achievement.toLocalAchievement())
    }

    override fun insertAchievements(achievements: List<Achievement>) {
        achievementsDao.insertAchievements(achievements.map { it.toLocalAchievement() })
    }
}