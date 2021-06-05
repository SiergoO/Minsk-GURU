package com.minsk.guru.data.repository.places

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.minsk.guru.domain.model.Achievements
import com.minsk.guru.domain.repository.achievements.AchievementsRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AchievementsRepositoryImpl :
    AchievementsRepository {
    override suspend fun getAchievements() = withContext(Dispatchers.IO) {
        val achievementsDeferred = CompletableDeferred<Achievements>()
        val db = FirebaseDatabase.getInstance().reference
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