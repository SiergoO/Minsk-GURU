package com.minsk.guru.data.repository.places

import com.minsk.guru.data.repository.room.PlacesDao
import com.minsk.guru.data.repository.room.toDomainPlace
import com.minsk.guru.data.repository.room.toLocalPlace
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.repository.places.PlacesLocalRepository

class PlacesLocalRepositoryImpl(private val placesDao: PlacesDao) : PlacesLocalRepository {

    override suspend fun isDatabaseEmpty(): Boolean = placesDao.getAnyPlace() == null

    override suspend fun save(places: List<Place>) =
        places.forEach { place -> placesDao.insertPlace(place.toLocalPlace()) }

    override suspend fun getAll(): List<Place> =
        placesDao.loadAllPlaces().map { it.toDomainPlace() }

    override suspend fun getByCategory(category: String): List<Place> =
        placesDao.loadPlacesByCategory(category).map { it.toDomainPlace() }

}