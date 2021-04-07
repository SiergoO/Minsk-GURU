package com.minsk.guru.domain.model

class Achievement {
    var category: String = ""
    var name: String = ""
    var description: String = ""
    var done: Int = 0
    var tbd: Int = 0
    var count: Int = 0
    var placesIds: List<String> = listOf()

    constructor() : super()

    constructor(
        category: String,
        name: String,
        description: String,
        done: Int,
        tbd: Int,
        count: Int,
        placesIds: List<String>
    ) {
        this.category = category
        this.name = name
        this.description = description
        this.done = done
        this.tbd = tbd
        this.count = count
        this.placesIds = placesIds
    }
}