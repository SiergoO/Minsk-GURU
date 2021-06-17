package com.minsk.guru.data.repository.local.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.repository.local.PlacesLocalRepository

class PlacesLocalRepositoryImpl(private val placesDao: PlacesDao) :
    PlacesLocalRepository {

    override fun getPlaces(): List<Place> =
        placesDao.getPlaces().map { it.toDomainPlace() }

    override fun getPlaceById(id: Int): Place =
        placesDao.getPlaceById(id).toDomainPlace()

    override fun getPlacesByCategory(categoryName: String?): List<Place> =
        placesDao.getPlacesByCategory(categoryName).map { it.toDomainPlace() }

    override fun updatePlace(place: Place) {
        placesDao.updatePlace(place.toLocalPlace())
    }

    override fun insertPlace(place: Place) {
        placesDao.insertPlace(place.toLocalPlace())
    }

    override fun deletePlace(place: Place) {
        placesDao.deletePlace(place.toLocalPlace())
    }
}