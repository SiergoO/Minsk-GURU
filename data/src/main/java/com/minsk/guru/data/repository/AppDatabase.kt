package com.minsk.guru.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minsk.guru.data.repository.room.user.LocalUser
import com.minsk.guru.data.repository.room.user.UserDao
import com.minsk.guru.data.repository.room.achievements.AchievementsDao
import com.minsk.guru.data.repository.room.achievements.LocalAchievement

@Database(
    entities = [LocalUser::class, LocalAchievement::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun achievementsDao(): AchievementsDao
}