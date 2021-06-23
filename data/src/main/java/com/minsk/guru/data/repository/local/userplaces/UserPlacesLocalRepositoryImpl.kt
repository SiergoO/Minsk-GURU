package com.minsk.guru.data.repository.local.userplaces

import com.minsk.guru.domain.model.UserPlace
import com.minsk.guru.domain.repository.local.UserPlacesLocalRepository

class UserPlacesLocalRepositoryImpl(private val userPlacesDao: UserPlacesDao) :
    UserPlacesLocalRepository {

    override fun insertUserPlace(userPlace: UserPlace) {
        userPlacesDao.insertLocalUserPlace(userPlace.toLocalUserPlace())
    }

    override fun updateUserPlace(userPlace: UserPlace) {
        userPlacesDao.updateLocalUserPlace(userPlace.toLocalUserPlace())
    }

    override fun deleteUserPlace(userPlace: UserPlace) {
        userPlacesDao.deleteLocalUserPlace(userPlace.placeId)
    }
}