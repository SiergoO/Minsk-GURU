package com.minsk.guru.domain.repository.local

import com.minsk.guru.domain.model.UserPlace

interface UserPlacesLocalRepository {
    fun insertUserPlace(userPlace: UserPlace)
    fun updateUserPlace(userPlace: UserPlace)
    fun deleteUserPlace(userPlace: UserPlace)
}