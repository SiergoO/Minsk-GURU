package com.minsk.guru.data.repository.room.user

import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.repository.room.UserLocalRepository

class UserLocalRepositoryImpl(private val userDao: UserDao) : UserLocalRepository {
    override fun getUser(): User = userDao.getUser().toDomainUser()

    override fun updateUser(user: User) {
        userDao.updateUser(user.toLocalUser())
    }

    override fun insertUser(user: User) {
        userDao.insertUser(user.toLocalUser())
    }
}