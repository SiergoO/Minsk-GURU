package com.minsk.guru.data.model

import com.minsk.guru.domain.model.User

data class NetUser(
    val email: String,
    val phone: String,
    val password: String
)

fun NetUser.toDomainModel() = User(email, phone, password)

fun User.toNetModel() = NetUser(email, phone, password)