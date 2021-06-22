package com.minsk.guru.domain.usecase.firebase.auth

import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SignInUseCaseImpl(
    private val authRepository: AuthRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<SignInUseCase.Param, SignInUseCase.Result>(ioDispatcher),
    SignInUseCase {

    override suspend fun run(param: SignInUseCase.Param): SignInUseCase.Result =
        try {
            authRepository.signIn(param.email, param.password)
            SignInUseCase.Result.Success
        } catch (error: Throwable) {
            SignInUseCase.Result.Failure(error)
        }
}