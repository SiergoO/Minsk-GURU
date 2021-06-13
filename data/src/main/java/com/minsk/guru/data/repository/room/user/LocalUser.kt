package com.minsk.guru.data.repository.room.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.minsk.guru.data.repository.room.user.LocalUser.Companion.TABLE_NAME
import com.minsk.guru.domain.model.User

@Entity(tableName = TABLE_NAME)
data class LocalUser(
    @PrimaryKey
    @ColumnInfo(name = USER_ID)
    val id: String,
    @ColumnInfo(name = USER_EMAIL)
    val email: String? = null,
    @ColumnInfo(name = USER_NAME)
    val name: String? = null,
    @ColumnInfo(name = USER_SURNAME)
    val surname: String? = null,
) {
    companion object {
        const val TABLE_NAME = "user"
        const val USER_ID = "user_id"
        const val USER_EMAIL = "user_email"
        const val USER_NAME = "user_name"
        const val USER_SURNAME = "user_surname"
    }
}

fun LocalUser.toDomainUser() =
    User(id, email ?: "", name ?: "", surname ?: "", null)

fun User.toLocalUser() = LocalUser(id, email, name, surname)