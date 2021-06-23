package com.minsk.guru.domain.model.remote

class RemoteAchievements {

    var firebaseAchievements = listOf<RemoteAchievement>()
    constructor() : super()

    constructor(remoteAchievements: List<RemoteAchievement>) {
        this.firebaseAchievements = remoteAchievements
    }
}