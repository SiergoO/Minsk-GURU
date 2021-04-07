package com.minsk.guru.domain.model

 class Places {

     var places: MutableList<Place> = mutableListOf()

     constructor() : super()

     constructor(places: MutableList<Place>) {
         this.places = places
     }

    override fun toString(): String {
        return places.joinToString("\n") { it.name + " - " + it.address }
    }
}
