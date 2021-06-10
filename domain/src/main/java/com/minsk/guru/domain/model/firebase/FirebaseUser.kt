package com.minsk.guru.domain.model

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
}