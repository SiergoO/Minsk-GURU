package com.minsk.guru.domain.usecase.firebase.places

import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetCategoriesUseCase:
    SingleResultUseCase<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result> {

    object Param

    sealed class Result {
        data class Success(val categories: List<Category>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}