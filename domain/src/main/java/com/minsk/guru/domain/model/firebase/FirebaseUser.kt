package com.minsk.guru.domain.model.firebase

import com.minsk.guru.domain.model.User

class FirebaseUser {
    var email: String = ""
    var name: String = ""
    var surname: String = ""

    constructor() : super()

    constructor(
        email: String,
        name: String,
        surname: String
    ) {
        this.email = email
        this.name = name
        this.surname = surname
    }

    fun toDomainModel() = User("UNKNOWN", email, name, surname, null)
}