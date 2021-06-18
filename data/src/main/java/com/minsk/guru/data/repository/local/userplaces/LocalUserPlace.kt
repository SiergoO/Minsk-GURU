package com.minsk.guru.data.repository.local.userplaces

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minsk.guru.data.repository.local.userplaces.LocalUserPlace.Companion.TABLE_NAME
import com.minsk.guru.domain.model.UserPlace

@Entity(tableName = TABLE_NAME)
data class LocalUserPlace(
    @PrimaryKey
    @ColumnInfo(name = USER_ID)
    val userId: String,
    @ColumnInfo(name = USER_PLACE_ID)
    val userPlaceId: String
) {
    companion object {
        const val TABLE_NAME = "user_places"
        const val USER_ID = "user_id"
        const val USER_PLACE_ID = "user_place_id"
    }
}

fun LocalUserPlace.toDomainUser() = UserPlace(userId, userPlaceId)

fun UserPlace.toLocalUserPlace() = LocalUserPlace(userId, placeId)