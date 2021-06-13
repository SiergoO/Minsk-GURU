package com.minsk.guru.data.repository.firebase

import com.google.firebase.database.*
import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.model.firebase.FirebaseAchievement
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AchievementsRepositoryImpl(private val firebaseDatabase: FirebaseDatabase) :
    AchievementsRepository {
    @ExperimentalCoroutinesApi
    override fun getAchievements(): List<Achievement> {
        val achievementsDeferred = CompletableDeferred<List<Achievement>>()
        val db = firebaseDatabase.reference.child("achievements")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                /** do nothing **/
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val achievements = snapshot.getValue(
                    object : GenericTypeIndicator<List<FirebaseAchievement>>() {}
                ) ?: listOf()
                achievementsDeferred.complete(achievements.map {
                    it.toDomainModel().copy(id = achievements.indexOf(it))
                })
            }
        })
        return achievementsDeferred.getCompleted()
    }
}