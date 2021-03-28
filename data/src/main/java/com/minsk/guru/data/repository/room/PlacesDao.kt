package com.minsk.guru.data.repository.room

import androidx.room.*

@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: LocalPlace)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlaces(places: List<LocalPlace>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlace(place: LocalPlace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlaces(places: List<LocalPlace>)

    @Delete
    fun deletePlace(place: LocalPlace)

    @Delete
    fun deleteAllPlaces(places: List<LocalPlace>)

    @Query("SELECT * FROM places order by place_id")
    suspend fun loadAllPlaces(): List<LocalPlace>

    @Query("SELECT * FROM places where place_category LIKE :category order by place_id")
    suspend fun loadPlacesByCategory(category: String): List<LocalPlace>

    @Query("SELECT * FROM places LIMIT 1")
    suspend fun getAnyPlace():LocalPlace?
}