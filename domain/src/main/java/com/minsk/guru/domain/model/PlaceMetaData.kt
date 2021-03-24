package com.minsk.guru.domain.model

data class PlaceMetaData(
    val id: String,
    val name: String,
    val address: String,
    val url: String,
    val Phones: List<Phone>,
    val Categories: List<Category>,
    val Hours: Hours
)