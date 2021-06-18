package com.minsk.guru.data.repository.local.userplaces

import androidx.room.*

@Dao
interface UserPlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserPlace(userPlace: LocalUserPlace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserPlace(userPlace: LocalUserPlace)

    @Delete
    fun deleteUserPlace(userPlace: LocalUserPlace)
}