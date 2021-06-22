package com.minsk.guru.domain.usecase.firebase.auth

import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetCurrentUserUseCase : SingleResultUseCase<GetCurrentUserUseCase.Param, GetCurrentUserUseCase.Result> {

    object Param

    sealed class Result {
        data class Success(val user: User?) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}