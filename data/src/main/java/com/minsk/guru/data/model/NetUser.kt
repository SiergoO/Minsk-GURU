package com.minsk.guru.data.model

import com.minsk.guru.domain.model.User

data class NetUser(
    var email: String,
)

fun NetUser.toDomainModel() = User(email)

fun User.toNetModel() = NetUser(email)