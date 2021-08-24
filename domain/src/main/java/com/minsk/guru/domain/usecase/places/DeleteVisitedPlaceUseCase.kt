package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface DeleteVisitedPlaceUseCase :
    SingleResultUseCase<DeleteVisitedPlaceUseCase.Param, DeleteVisitedPlaceUseCase.Result> {

    data class Param(val userId: String, val place: Place)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}