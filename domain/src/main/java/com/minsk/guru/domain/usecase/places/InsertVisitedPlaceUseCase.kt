package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface InsertVisitedPlaceUseCase :
    SingleResultUseCase<InsertVisitedPlaceUseCase.Param, InsertVisitedPlaceUseCase.Result> {

    data class Param(val userId: String, val place: Place)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}