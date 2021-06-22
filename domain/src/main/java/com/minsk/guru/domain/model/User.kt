package com.minsk.guru.domain.model

data class User(
    val id: String,
    var email: String,
    var name: String,
    var surname: String,
    var achievements: Achievements?
)