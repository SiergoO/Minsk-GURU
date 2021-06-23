package com.minsk.guru.data.repository.local.user

import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.repository.local.UserLocalRepository

class UserLocalRepositoryImpl(private val userDao: UserDao) : UserLocalRepository {
    override fun getUser(): User = userDao.getLocalUser().toDomainUser()

    override fun updateUser(user: User) {
        userDao.updateLocalUser(user.toLocalUser())
    }

    override fun insertUser(user: User) {
        userDao.insertLocalUser(user.toLocalUser())
    }
}