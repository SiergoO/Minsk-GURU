package com.minsk.guru.data.model

import com.minsk.guru.domain.model.User

data class NetUser(
    var email: String,
    var name: String,
    var surname: String
)

fun NetUser.toDomainModel() = User(email, name, surname)

fun User.toNetModel() = NetUser(email, name, surname)