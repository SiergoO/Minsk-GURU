package com.minsk.guru.domain.model

class Achievement {
    var category: String = ""
    var name: String = ""
    var description: String = ""
    var count: Int = 0
    var placesIds: List<String> = listOf()

    constructor() : super()

    constructor(
        category: String,
        name: String,
        description: String,
        count: Int,
        placesIds: List<String>
    ) {
        this.category = category
        this.name = name
        this.description = description
        this.count = count
        this.placesIds = placesIds
    }
}