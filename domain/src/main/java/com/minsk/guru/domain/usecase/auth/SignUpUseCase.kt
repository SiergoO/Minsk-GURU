package com.minsk.guru.domain.usecase.auth

import com.minsk.guru.domain.usecase.SingleResultUseCase

interface SignUpUseCase : SingleResultUseCase<SignUpUseCase.Param, SignUpUseCase.Result> {

    data class Param(val email: String, val password: String, val name: String, val surname: String)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}