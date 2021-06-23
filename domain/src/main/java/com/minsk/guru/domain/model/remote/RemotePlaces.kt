package com.minsk.guru.domain.model.remote

import com.minsk.guru.domain.model.Place

class RemotePlaces {

     var places: List<Place> = listOf()

     constructor() : super()

     constructor(places: List<Place>) {
         this.places = places
     }
}
