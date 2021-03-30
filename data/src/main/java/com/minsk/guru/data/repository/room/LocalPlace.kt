package com.minsk.guru.data.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minsk.guru.domain.model.*

@Entity(tableName = LocalPlace.TABLE_NAME)
data class LocalPlace(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = ADDRESS)
    val address: String,
    @ColumnInfo(name = LONGITUDE)
    val longitude: Double,
    @ColumnInfo(name = LATITUDE)
    val latitude: Double,
    @ColumnInfo(name = OPENING_HOURS)
    val openingHours: String,
    @ColumnInfo(name = PHONE)
    val phone: String,
    @ColumnInfo(name = URL)
    val url: String,
    @ColumnInfo(name = CATEGORY)
    val category: String,
    @ColumnInfo(name = IS_VISITED)
    val isVisited: Boolean
) {
    companion object {
        const val TABLE_NAME = "places"
        const val ID = "place_id"
        const val NAME = "place_name"
        const val ADDRESS = "place_address"
        const val LATITUDE = "place_latitude"
        const val LONGITUDE = "place_longitude"
        const val OPENING_HOURS = "place_opening_hours"
        const val PHONE = "place_phone"
        const val URL = "place_url"
        const val CATEGORY = "place_category"
        const val IS_VISITED = "place_is_visited"
    }
}

fun Place.toLocalPlace() = LocalPlace(
    properties.PlaceMetaData?.id.toString(),
    properties.PlaceMetaData?.name ?: "",
    properties.PlaceMetaData?.address ?: "",
    geometry.coordinates.first(),
    geometry.coordinates.last(),
    properties.PlaceMetaData?.Hours?.text ?: "",
    properties.PlaceMetaData?.Phones?.joinToString { it.formatted } ?: "",
    properties.PlaceMetaData?.url ?: "",
    properties.PlaceMetaData?.Categories?.joinToString { it.name } ?: "",
    false
)

fun LocalPlace.toDomainPlace() = Place(
    Geometry(emptyList()), Properties(
        PlaceMetaData(
            id,
            name,
            address,
            url,
            listOf(Phone(phone, "")),
            listOf(Category("", category)),
            null
        )
    )
)