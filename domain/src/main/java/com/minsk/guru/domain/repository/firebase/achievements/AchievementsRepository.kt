package com.minsk.guru.domain.repository.firebase.achievements

import com.minsk.guru.domain.model.Achievement

interface AchievementsRepository {
    fun getAchievements(): List<Achievement>
//    fun getAchievementById(id: Int): Achievement
//    fun updateAchievement(achievement: Achievement)
//    fun insertAchievements(achievements: List<Achievement>)
}