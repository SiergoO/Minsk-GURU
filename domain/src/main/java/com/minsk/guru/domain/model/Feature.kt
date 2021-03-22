package com.minsk.guru.domain.model

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)