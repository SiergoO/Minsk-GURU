package com.minsk.guru.domain.model

 class Places {

     var places: List<Place> = listOf()

     constructor() : super()

     constructor(places: List<Place>) {
         this.places = places
     }

    override fun toString(): String {
        return places.joinToString("\n") { it.name + " - " + it.address }
    }
}
