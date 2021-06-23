package com.minsk.guru.data.repository.local.userplaces

import androidx.room.*

@Dao
interface UserPlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocalUserPlace(userPlace: LocalUserPlace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocalUserPlace(userPlace: LocalUserPlace)

    @Query("DELETE FROM user_places WHERE user_place_id = :placeId")
    fun deleteLocalUserPlace(placeId: String)
}