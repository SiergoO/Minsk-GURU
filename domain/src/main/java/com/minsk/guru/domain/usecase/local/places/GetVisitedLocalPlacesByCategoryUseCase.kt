package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetVisitedLocalPlacesByCategoryUseCase :
    SingleResultUseCase<GetVisitedLocalPlacesByCategoryUseCase.Param, GetVisitedLocalPlacesByCategoryUseCase.Result> {

    data class Param(val categoryName: String?)

    sealed class Result {
        data class Success(val visitedPlaces: List<Place>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}