package com.minsk.guru.domain.model

data class Place(
    val geometry: Geometry,
    val properties: Properties
) {
    override fun toString(): String {
        return "\n" + "\n" + properties.PlaceMetaData?.Categories?.first()?.name +
                "\n" + properties.PlaceMetaData?.name +
                "\n" + properties.PlaceMetaData?.address
    }
}