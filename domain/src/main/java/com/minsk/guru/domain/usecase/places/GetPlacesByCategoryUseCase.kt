package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetPlacesByCategoryUseCase :
    SingleResultUseCase<GetPlacesByCategoryUseCase.Param, GetPlacesByCategoryUseCase.Result> {

    data class Param(val categoryName: String?)

    sealed class Result {
        data class Success(val places: List<Place>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}
