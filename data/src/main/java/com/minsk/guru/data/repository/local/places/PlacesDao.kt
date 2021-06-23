package com.minsk.guru.data.repository.local.places

import androidx.room.*

@Dao
interface PlacesDao {
    @Query("SELECT * FROM places")
    fun getLocalPlaces(): List<LocalPlace>

    @Query("SELECT * FROM places WHERE place_id IN (SELECT user_place_id FROM user_places WHERE user_id=:userId)")
    fun getLocalPlacesVisitedByUser(userId: String): List<LocalPlace>

    @Query("SELECT * FROM places WHERE place_id=:id")
    fun getLocalPlaceById(id: Int): LocalPlace

    @Query("SELECT * FROM places WHERE place_id IN (SELECT user_place_id FROM user_places WHERE place_category LIKE '%' || :categoryName || '%' AND user_id LIKE :userId) ")
    fun getLocalPlacesByCategory(userId: String, categoryName: String?): List<LocalPlace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocalPlace(place: LocalPlace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocalPlace(place: LocalPlace)

    @Delete
    fun deleteLocalPlace(place: LocalPlace)
}