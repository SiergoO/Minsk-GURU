package com.minsk.guru.domain.model

import com.minsk.guru.domain.model.remote.RemoteAchievements

data class User(
    val id: String,
    var email: String,
    var name: String,
    var surname: String,
    var remoteAchievements: RemoteAchievements?
)