package com.minsk.guru.data.rest.model

import com.minsk.guru.domain.model.remote.RemoteUser

data class NetFirebaseUser(
    var email: String,
    var name: String,
    var surname: String
)

fun NetFirebaseUser.toDomainModel() = RemoteUser(email, name, surname)

fun RemoteUser.toNetModel() = NetFirebaseUser(email, name, surname)