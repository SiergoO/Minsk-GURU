package com.minsk.guru.data.repository.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.minsk.guru.domain.model.Achievements
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AchievementsRepositoryImpl(private val firebaseDatabase: FirebaseDatabase) :
    AchievementsRepository {
    override suspend fun getAchievements() = withContext(Dispatchers.IO) {
        val achievementsDeferred = CompletableDeferred<Achievements>()
        val db = firebaseDatabase.reference
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val achievements =
                    snapshot.getValue(Achievements::class.java) ?: Achievements(mutableListOf())
                achievementsDeferred.complete(achievements)
            }
        })
        return@withContext achievementsDeferred.await()
    }
}