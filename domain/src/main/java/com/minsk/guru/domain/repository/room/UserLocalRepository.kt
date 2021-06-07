package com.minsk.guru.domain.repository.room

import com.minsk.guru.domain.model.User

interface UserLocalRepository {
    fun getUser(): User
    fun updateUser(user: User)
    fun insertUser(user: User)
}