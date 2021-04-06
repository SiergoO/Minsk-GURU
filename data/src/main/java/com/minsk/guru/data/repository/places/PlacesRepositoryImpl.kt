package com.minsk.guru.data.repository.places

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.minsk.guru.domain.model.Places
import com.minsk.guru.domain.repository.places.PlacesRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepositoryImpl() : PlacesRepository {

    override suspend fun getAll(): Places =
        withContext(Dispatchers.IO) {
            val placesDeferred = CompletableDeferred<Places>()
            val db = FirebaseDatabase.getInstance().reference
            db.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val places = snapshot.getValue(Places::class.java) ?: Places(mutableListOf())
//                    val function = { place: Place -> place.category.split(", ") }
//                    val categories = places.places
//                        .flatMap(function).toSet().joinToString("\n")
                    placesDeferred.complete(places)
                }
            })
            return@withContext placesDeferred.await()
        }


}