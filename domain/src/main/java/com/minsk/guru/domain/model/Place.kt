package com.minsk.guru.domain.model

class Place {
    var address: String = ""
    var category: String = ""
    var id: String = ""
    var is_visited: Boolean = false
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var name: String = ""
    var opening_hours: String = ""
    var phone: String = ""
    var url: String = ""

    constructor() : super()

    constructor(
        address: String,
        category: String,
        id: String,
        is_visited: Boolean,
        latitude: Double,
        longitude: Double,
        name: String,
        opening_hours: String,
        phone: String,
        url: String
    ) {
        this.address = address
        this.category = category
        this.id = id
        this.is_visited = is_visited
        this.latitude = latitude
        this.longitude = longitude
        this.name = name
        this.opening_hours = opening_hours
        this.phone = phone
        this.url = url
    }
}