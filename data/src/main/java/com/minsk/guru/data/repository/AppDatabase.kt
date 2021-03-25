package com.minsk.guru.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minsk.guru.data.repository.room.PlacesDao
import com.minsk.guru.data.repository.room.LocalPlace

@Database(entities = [LocalPlace::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}