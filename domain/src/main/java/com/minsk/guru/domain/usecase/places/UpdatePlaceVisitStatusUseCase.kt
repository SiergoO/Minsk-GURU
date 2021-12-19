package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface UpdatePlaceVisitStatusUseCase :
    SingleResultUseCase<UpdatePlaceVisitStatusUseCase.Param, UpdatePlaceVisitStatusUseCase.Result> {

    data class Param(val userId: String, val place: Place, val categoryName: String, val isVisited: Boolean)

    sealed class Result {
        data class Success(val visitedPlaces: List<Place>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}