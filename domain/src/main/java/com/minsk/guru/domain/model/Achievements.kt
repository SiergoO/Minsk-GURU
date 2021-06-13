package com.minsk.guru.domain.model

import com.minsk.guru.domain.model.firebase.FirebaseAchievement

class Achievements {

    var firebaseAchievements = listOf<FirebaseAchievement>()
    constructor() : super()

    constructor(firebaseAchievements: List<FirebaseAchievement>) {
        this.firebaseAchievements = firebaseAchievements
    }

    override fun toString(): String {
        return firebaseAchievements.joinToString("\n") { "${it.name} - ${it.description}" }
    }
}