package com.minsk.guru.domain.repository.local

import com.minsk.guru.domain.model.Achievement

interface AchievementsLocalRepository {
    fun getAchievements(): List<Achievement>
    fun getAchievementById(id: Int): Achievement
    fun updateAchievement(achievement: Achievement)
    fun insertAchievements(achievements: List<Achievement>)
}