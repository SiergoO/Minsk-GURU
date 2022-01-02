package com.minsk.guru.utils.map

import com.minsk.guru.domain.model.Place
import com.yandex.mapkit.map.PlacemarkMapObject

fun PlacemarkMapObject.getPlace(places: List<Place>?): Place? =
    places?.find { place -> place.latitude == this.geometry.latitude && place.longitude == this.geometry.longitude }