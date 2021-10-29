package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetCategoriesUseCase :
    SingleResultUseCase<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result> {

    data class Param (val userId: String)

    sealed class Result {
        data class Success(val categories: List<UserCategory>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}