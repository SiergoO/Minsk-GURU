package com.minsk.guru.data.repository.local.user

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getLocalUser(): LocalUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocalUser(localUser: LocalUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocalUser(localUser: LocalUser)

    @Delete
    fun deleteLocalUser(localUser: LocalUser)
}