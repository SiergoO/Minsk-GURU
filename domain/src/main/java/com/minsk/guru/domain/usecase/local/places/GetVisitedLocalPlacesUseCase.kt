package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetVisitedLocalPlacesUseCase :
    SingleResultUseCase<GetVisitedLocalPlacesUseCase.Param, GetVisitedLocalPlacesUseCase.Result> {

    object Param

    sealed class Result {
        data class Success(val visitedPlaces: List<Place>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}