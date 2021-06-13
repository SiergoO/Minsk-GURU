package com.minsk.guru.data.model

import com.minsk.guru.domain.model.firebase.FirebaseUser

data class NetFirebaseUser(
    var email: String,
    var name: String,
    var surname: String
)

fun NetFirebaseUser.toDomainModel() = FirebaseUser(email, name, surname)

fun FirebaseUser.toNetModel() = NetFirebaseUser(email, name, surname)