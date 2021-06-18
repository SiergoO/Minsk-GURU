package com.minsk.guru.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minsk.guru.data.repository.local.achievements.AchievementsDao
import com.minsk.guru.data.repository.local.achievements.LocalAchievement
import com.minsk.guru.data.repository.local.places.LocalPlace
import com.minsk.guru.data.repository.local.places.PlacesDao
import com.minsk.guru.data.repository.local.user.LocalUser
import com.minsk.guru.data.repository.local.user.UserDao
import com.minsk.guru.data.repository.local.userplaces.LocalUserPlace
import com.minsk.guru.data.repository.local.userplaces.UserPlacesDao

@Database(
    entities = [LocalUser::class, LocalAchievement::class, LocalPlace::class, LocalUserPlace::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun achievementsDao(): AchievementsDao
    abstract fun placesDao(): PlacesDao
    abstract fun userPlacesDao(): UserPlacesDao
}