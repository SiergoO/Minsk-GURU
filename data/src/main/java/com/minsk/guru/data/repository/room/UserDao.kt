package com.minsk.guru.data.repository.room

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): LocalUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(localUser: LocalUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(localUser: LocalUser)

    @Delete
    fun deleteUser(localUser: LocalUser)
}