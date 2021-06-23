package com.minsk.guru.data.repository.local.achievements

import androidx.room.*

@Dao
interface AchievementsDao {
    @Query("SELECT * FROM achievements")
    fun getLocalAchievements(): List<LocalAchievement>

    @Query("SELECT * FROM achievements WHERE achievement_id=:id")
    fun getLocalAchievementById(id: Int): LocalAchievement

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocalAchievements(achievements: List<LocalAchievement>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocalAchievement(achievement: LocalAchievement)

    @Delete
    fun deleteLocalAchievement(achievement: LocalAchievement)
}