package com.minsk.guru.data.repository.room

import androidx.paging.DataSource
import androidx.room.*
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.Places

@Dao
interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: Place)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlaces(places: Places)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlace(place: Place)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlaces(places: Places)

    @Delete
    fun deletePlace(place: Place)

    @Delete
    fun deleteAllPlaces(places: Places)

    @Query("SELECT * FROM places order by place_id")
    fun loadAllGifs(): DataSource.Factory<Int, Place>

    @Query("SELECT * FROM places where place_category LIKE :category order by place_id")
    fun loadAllGifsByCategory(category: String): DataSource.Factory<Int, Place>

}