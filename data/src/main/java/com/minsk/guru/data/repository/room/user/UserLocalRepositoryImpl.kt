package com.minsk.guru.data.repository.room.user

import com.minsk.guru.data.repository.room.UserDao
import com.minsk.guru.data.repository.room.toDomainUser
import com.minsk.guru.data.repository.room.toLocalUser
import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.repository.room.UserLocalRepository

class UserLocalRepositoryImpl(private val userDao: UserDao) : UserLocalRepository {
    override fun getUser(): User = userDao.getUser().toDomainUser()

    override fun updateUser(user: User) {
        if (userDao.getUser() != user.toLocalUser()) {
            userDao.updateUser(user.toLocalUser())
        }
    }

    override fun insertUser(user: User) {
        if (getUser() != user) {
            userDao.insertUser(user.toLocalUser())
        } else updateUser(user)
    }
}