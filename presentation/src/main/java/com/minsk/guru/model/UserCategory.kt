package com.minsk.guru.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

typealias DomainUserCategories = com.minsk.guru.domain.model.UserCategory
typealias UiUserCategories = UserCategory

@Parcelize
data class UserCategory(
    val name: String,
    val categoryPlaces: List<Place>,
    val visitedPlaces: List<Place>
) : Parcelable

fun DomainUserCategories.toUiModel() = UiUserCategories(
    name,
    categoryPlaces.map { it.toUiModel() },
    visitedPlaces.map { it.toUiModel() }
)

fun UiUserCategories.toDomainModel() = DomainUserCategories(
    name,
    categoryPlaces.map { it.toDomainModel() },
    visitedPlaces.map { it.toDomainModel() }
)