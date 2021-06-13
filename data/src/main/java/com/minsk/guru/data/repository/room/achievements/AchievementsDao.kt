package com.minsk.guru.data.repository.room.achievements

import androidx.room.*

@Dao
interface AchievementsDao {
    @Query("SELECT * FROM achievements")
    fun getAchievements(): List<LocalAchievement>

    @Query("SELECT * FROM achievements WHERE achievement_id=:id")
    fun getAchievementById(id: Int): LocalAchievement

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAchievements(achievements: List<LocalAchievement>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAchievement(achievement: LocalAchievement)

    @Delete
    fun deleteAchievement(achievement: LocalAchievement)
}