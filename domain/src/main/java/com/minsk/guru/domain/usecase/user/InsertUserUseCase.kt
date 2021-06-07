package com.minsk.guru.domain.usecase.user

import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface InsertUserUseCase: SingleResultUseCase<InsertUserUseCase.Param, InsertUserUseCase.Result> {

    data class Param(val user: User)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}