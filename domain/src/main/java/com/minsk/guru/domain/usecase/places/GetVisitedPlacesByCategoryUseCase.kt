package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetVisitedPlacesByCategoryUseCase :
    SingleResultUseCase<GetVisitedPlacesByCategoryUseCase.Param, GetVisitedPlacesByCategoryUseCase.Result> {

    data class Param(val userId: String, val categoryName: String?)

    sealed class Result {
        data class Success(val visitedPlaces: List<Place>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}