package com.minsk.guru.data.repository.local.achievements

import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.repository.local.AchievementsLocalRepository

class AchievementsLocalRepositoryImpl(private val achievementsDao: AchievementsDao) :
    AchievementsLocalRepository {

    override fun getAchievements(): List<Achievement> =
        achievementsDao.getLocalAchievements().map { it.toDomainAchievement() }

    override fun getAchievementById(id: Int): Achievement =
        achievementsDao.getLocalAchievementById(id).toDomainAchievement()

    override fun updateAchievement(achievement: Achievement) {
        achievementsDao.updateLocalAchievement(achievement.toLocalAchievement())
    }

    override fun insertAchievements(achievements: List<Achievement>) {
        achievementsDao.insertLocalAchievements(achievements.map { it.toLocalAchievement() })
    }
}