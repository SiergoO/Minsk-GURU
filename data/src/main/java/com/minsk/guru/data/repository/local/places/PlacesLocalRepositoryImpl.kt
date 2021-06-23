package com.minsk.guru.data.repository.local.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.repository.local.PlacesLocalRepository

class PlacesLocalRepositoryImpl(private val placesDao: PlacesDao) :
    PlacesLocalRepository {

    override fun getPlaces(): List<Place> =
        placesDao.getLocalPlaces().map { it.toDomainPlace() }

    override fun getPlacesVisitedByUser(userId: String): List<Place> =
        placesDao.getLocalPlacesVisitedByUser(userId).map { it.toDomainPlace() }

    override fun getPlaceById(id: Int): Place =
        placesDao.getLocalPlaceById(id).toDomainPlace()

    override fun getPlacesByCategory(userId: String, categoryName: String?): List<Place> =
        placesDao.getLocalPlacesByCategory(userId, categoryName).map { it.toDomainPlace() }

    override fun updatePlace(place: Place) {
        placesDao.updateLocalPlace(place.toLocalPlace())
    }

    override fun insertPlace(place: Place) {
        placesDao.insertLocalPlace(place.toLocalPlace())
    }

    override fun deletePlace(place: Place) {
        placesDao.deleteLocalPlace(place.toLocalPlace())
    }
}