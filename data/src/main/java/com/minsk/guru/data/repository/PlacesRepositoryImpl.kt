package com.minsk.guru.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.*
import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.remote.RemotePlaces
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class PlacesRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
) : PlacesRepository {

    override fun getAllPlaces(): List<Place> {
        val taskGetAllPlaces = firebaseDatabase.reference.child("places").get()
        return Tasks.await(taskGetAllPlaces)
            .getValue(object : GenericTypeIndicator<List<Place>>() {}) ?: listOf()
    }


    override suspend fun getByCategory(categoryName: String?): List<Place> =
        withContext(Dispatchers.IO) {
            val placesDeferred = CompletableDeferred<List<Place>>()
            firebaseDatabase.reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val places = snapshot.getValue(RemotePlaces::class.java)
                        ?: RemotePlaces(mutableListOf())
                    val filteredPlaces =
                        places.places.filter { place -> place.category.contains(categoryName.toString()) }
                    placesDeferred.complete(filteredPlaces)
                }
            })
            return@withContext placesDeferred.await()
        }

    override fun getCategories(): List<Category> {
        val taskGetCategories = firebaseDatabase.reference.child("categories").orderByKey().get()
        return Tasks.await(taskGetCategories)
            .getValue(object : GenericTypeIndicator<Map<String, List<Place>>>() {})
            ?.map { Category(it.key, it.value) }?.sortedBy { it.name } ?: listOf()
    }

    override fun getPlacesVisitedByUser(userId: String): List<Place> {
        val taskGetPlacesVisitedByUser =
            firebaseDatabase.reference.child("users").child(userId).child("visitedPlaces").get()
        return Tasks.await(taskGetPlacesVisitedByUser)
            .getValue(object : GenericTypeIndicator<Map<String, Place>>() {})?.values?.toList()
            ?: listOf()
    }

    override fun getVisitedPlacesByCategory(userId: String, categoryName: String): List<Place> {
        val taskGetVisitedPlacesByCategory =
            firebaseDatabase.reference.child("users").child(userId).child("visitedPlaces").get()
        return Tasks.await(taskGetVisitedPlacesByCategory)
            .getValue(object : GenericTypeIndicator<Map<String, Place>>() {})?.values?.toList()
            ?.filter { it.category.contains(categoryName, true) } ?: listOf()
    }

    override fun updatePlaceVisitStatus(userId: String, place: Place, isVisited: Boolean) {
        val visitedPlace = firebaseDatabase.reference.child("users")
            .child(userId)
            .child("visitedPlaces")
            .child(place.id)
        val taskUpdatePlaceVisitStatus =
            if (isVisited) visitedPlace.setValue(place) else visitedPlace.removeValue()
        Tasks.await(taskUpdatePlaceVisitStatus)
    }
}