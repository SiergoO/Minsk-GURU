package com.minsk.guru.data.repository.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.Places
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepositoryImpl(private val firebaseDatabase: FirebaseDatabase) : PlacesRepository {

    override suspend fun getAll(): List<Place> =
        withContext(Dispatchers.IO) {
            val placesDeferred = CompletableDeferred<List<Place>>()
            firebaseDatabase.reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val places = snapshot.getValue(Places::class.java)
                        ?: Places(mutableListOf())
                    placesDeferred.complete(places.places)
                }
            })
            return@withContext placesDeferred.await()
        }

    override suspend fun getByCategory(categoryName: String?): List<Place> =
        withContext(Dispatchers.IO) {
            val placesDeferred = CompletableDeferred<List<Place>>()
            firebaseDatabase.reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val places = snapshot.getValue(Places::class.java)
                        ?: Places(mutableListOf())
                    val filteredPlaces =
                        places.places.filter { place -> place.category.contains(categoryName.toString()) }
                    placesDeferred.complete(filteredPlaces)
                }
            })
            return@withContext placesDeferred.await()
        }
}