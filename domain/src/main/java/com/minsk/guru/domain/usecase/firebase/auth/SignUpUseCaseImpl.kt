package com.minsk.guru.domain.usecase.firebase.auth

import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SignUpUseCaseImpl(
    private val authRepository: AuthRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<SignUpUseCase.Param, SignUpUseCase.Result>(ioDispatcher),
    SignUpUseCase {

    override suspend fun run(param: SignUpUseCase.Param): SignUpUseCase.Result =
        try {
            authRepository.signUp(param.email, param.password, param.name, param.surname)
            SignUpUseCase.Result.Success
        } catch (error: Throwable) {
            SignUpUseCase.Result.Failure(error)
        }
}