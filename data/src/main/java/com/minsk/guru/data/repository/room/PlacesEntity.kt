package com.minsk.guru.data.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PlacesEntity.TABLE_NAME)
data class PlacesEntity(
    @PrimaryKey
    @ColumnInfo(name = PLACE_ID)
    val id: String,
    @ColumnInfo(name = PLACE_NAME)
    val name: String,
    @ColumnInfo(name = PLACE_ADDRESS)
    val address: String,
    @ColumnInfo(name = PLACE_PHONE)
    val phone: String,
    @ColumnInfo(name = PLACE_URL)
    val url: String,
    @ColumnInfo(name = PLACE_CATEGORY)
    val category: String
) {
    companion object {
        const val TABLE_NAME = "places"
        const val PLACE_ID = "place_id"
        const val PLACE_NAME = "place_name"
        const val PLACE_ADDRESS = "place_address"
        const val PLACE_PHONE = "place_phone"
        const val PLACE_URL = "place_url"
        const val PLACE_CATEGORY = "place_category"
    }
}