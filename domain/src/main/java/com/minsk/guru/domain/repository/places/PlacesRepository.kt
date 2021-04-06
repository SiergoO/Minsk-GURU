package com.minsk.guru.domain.repository.places

import com.minsk.guru.domain.model.Places

interface PlacesRepository {
    suspend fun getAll(): Places
}