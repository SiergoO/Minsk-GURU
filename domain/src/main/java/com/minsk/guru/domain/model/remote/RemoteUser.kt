package com.minsk.guru.domain.model.remote

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.User

class RemoteUser {
    var email: String = ""
    var name: String = ""
    var surname: String = ""
    var profilePhotoUrl: String = ""
    var visitedPlaces: Map<String, Place> = mapOf()

    constructor() : super()

    constructor(
        email: String,
        name: String,
        surname: String,
        profilePhotoUrl: String,
        visitedPlaces: Map<String, Place>
    ) {
        this.email = email
        this.name = name
        this.surname = surname
        this.profilePhotoUrl = profilePhotoUrl
        this.visitedPlaces = visitedPlaces
    }

    fun toDomainModel() = User("UNKNOWN", email, name, surname, visitedPlaces)
}