package com.minsk.guru.data.repository.room.places

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minsk.guru.data.repository.room.places.LocalPlace.Companion.TABLE_NAME
import com.minsk.guru.domain.model.Place

@Entity(tableName = TABLE_NAME)
data class LocalPlace(
    @PrimaryKey
    @ColumnInfo(name = PLACE_ID)
    val id: String,
    @ColumnInfo(name = PLACE_ADDRESS)
    val address: String? = null,
    @ColumnInfo(name = PLACE_CATEGORY)
    val category: String? = null,
    @ColumnInfo(name = PLACE_LATITUDE)
    val latitude: Double? = null,
    @ColumnInfo(name = PLACE_LONGITUDE)
    val longitude: Double? = null,
    @ColumnInfo(name = PLACE_NAME)
    val name: String? = null,
    @ColumnInfo(name = PLACE_OPENING_HOURS)
    val openingHours: String? = null,
    @ColumnInfo(name = PLACE_PHONE)
    val phone: String? = null,
    @ColumnInfo(name = PLACE_URL)
    val url: String? = null
) {
    companion object {
        const val TABLE_NAME = "places"
        const val PLACE_ID = "place_id"
        const val PLACE_ADDRESS = "place_address"
        const val PLACE_CATEGORY = "place_category"
        const val PLACE_LATITUDE = "place_latitude"
        const val PLACE_LONGITUDE = "place_longitude"
        const val PLACE_NAME = "place_name"
        const val PLACE_OPENING_HOURS = "place_opening_hours"
        const val PLACE_PHONE = "place_phone"
        const val PLACE_URL = "place_url"
    }
}

fun LocalPlace.toDomainPlace() =
    Place(
        address ?: "",
        category ?: "",
        id,
        latitude ?: 0.0,
        longitude ?: 0.0,
        name ?: "",
        openingHours ?: "",
        phone ?: "",
        url ?: ""
    )

fun Place.toLocalPlace() =
    LocalPlace(id, address, category, latitude, longitude, name, openingHours, phone, url)