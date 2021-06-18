package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface InsertLocalPlaceUseCase :
    SingleResultUseCase<InsertLocalPlaceUseCase.Param, InsertLocalPlaceUseCase.Result> {

    data class Param(val userId: String, val place: Place)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}