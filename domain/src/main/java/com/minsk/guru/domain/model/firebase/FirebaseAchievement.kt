package com.minsk.guru.domain.model.firebase

import com.minsk.guru.domain.model.Achievement

class FirebaseAchievement {
    var category: String = ""
    var count: Int = 0
    var description: String = ""
    var name: String = ""
    var placesIds: List<String> = listOf()

    constructor() : super()

    constructor(
        category: String,
        count: Int,
        description: String,
        name: String,
        placesIds: List<String>
    ) {
        this.category = category
        this.count = count
        this.description = description
        this.name = name
        this.placesIds = placesIds
    }

    fun toDomainModel() = Achievement(-1, name, description, count) // TODO
}