package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetVisitedPlacesUseCase :
    SingleResultUseCase<GetVisitedPlacesUseCase.Param, GetVisitedPlacesUseCase.Result> {

    data class Param(val userId: String)

    sealed class Result {
        data class Success(val visitedPlaces: List<Place>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}