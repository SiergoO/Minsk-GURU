package com.minsk.guru.domain.usecase.firebase.auth

import com.minsk.guru.domain.usecase.SingleResultUseCase

interface SignInUseCase : SingleResultUseCase<SignInUseCase.Param, SignInUseCase.Result> {

    data class Param(val email: String, val password: String)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}