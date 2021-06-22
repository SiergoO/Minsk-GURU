package com.minsk.guru.data.repository.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.model.firebase.FirebaseAchievement
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AchievementsRepositoryImpl(private val firebaseDatabase: FirebaseDatabase) :
    AchievementsRepository {
    @ExperimentalCoroutinesApi
    override fun getAchievements(): List<Achievement> {
        val taskGetAchievements = firebaseDatabase.reference.child("achievements").get()
        Tasks.await(taskGetAchievements)
            .getValue(object : GenericTypeIndicator<List<FirebaseAchievement>>() {}).let { list ->
                return list!!.map { it.toDomainModel().copy(id = list.indexOf(it)) }
            }
    }
}