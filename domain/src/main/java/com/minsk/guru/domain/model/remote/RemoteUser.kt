package com.minsk.guru.domain.model.remote

import com.minsk.guru.domain.model.User

class RemoteUser {
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