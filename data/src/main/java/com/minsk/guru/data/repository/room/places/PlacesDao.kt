package com.minsk.guru.data.repository.room.places

import androidx.room.*

@Dao
interface PlacesDao {
    @Query("SELECT * FROM places")
    fun getPlaces(): List<LocalPlace>

    @Query("SELECT * FROM places WHERE place_id=:id")
    fun getPlaceById(id: Int): LocalPlace

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: LocalPlace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlace(place: LocalPlace)

    @Delete
    fun deletePlace(place: LocalPlace)
}