package com.minsk.guru.data.repository.local.places

import androidx.room.*

@Dao
interface PlacesDao {
    @Query("SELECT * FROM places")
    fun getPlaces(): List<LocalPlace>

//    @Query("SELECT * FROM places WHERE place_id IN (SELECT * FROM user_places WHERE place_id=:userId)")
//    fun getUserVisitedPlaces(userId: String): List<LocalPlace>

    @Query("SELECT * FROM places WHERE place_id=:id")
    fun getPlaceById(id: Int): LocalPlace

    @Query("SELECT * FROM places WHERE place_category LIKE '%' || :categoryName || '%'")
    fun getPlacesByCategory(categoryName: String?): List<LocalPlace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: LocalPlace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlace(place: LocalPlace)

    @Delete
    fun deletePlace(place: LocalPlace)
}