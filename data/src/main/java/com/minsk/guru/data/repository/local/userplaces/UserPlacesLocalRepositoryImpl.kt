package com.minsk.guru.data.repository.local.userplaces

import com.minsk.guru.domain.model.UserPlace
import com.minsk.guru.domain.repository.local.UserPlacesLocalRepository

class UserPlacesLocalRepositoryImpl(private val userPlacesDao: UserPlacesDao) :
    UserPlacesLocalRepository {

//    override fun getUserVisitedPlaces(userId: String): List<Place> =
//        userPlacesDao.getUserVisitedPlaces(userId).map { it.toDomainPlace() }

    override fun insertUserPlace(userPlace: UserPlace) {
        userPlacesDao.insertUserPlace(userPlace.toLocalUserPlace())
    }

    override fun updateUserPlace(userPlace: UserPlace) {
        userPlacesDao.updateUserPlace(userPlace.toLocalUserPlace())
    }

    override fun deleteUserPlace(userPlace: UserPlace) {
        userPlacesDao.deleteUserPlace(userPlace.toLocalUserPlace())
    }
}