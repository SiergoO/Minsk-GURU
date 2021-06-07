package com.minsk.guru.domain.model

class FirebaseUser {
    var surname: String = ""
    var name: String = ""
    var email: String = ""

    constructor() : super()

    constructor(
        surname: String,
        name: String,
        email: String
    ) {
        this.surname = surname
        this.name = name
        this.email = email
    }
}