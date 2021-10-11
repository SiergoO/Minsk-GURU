package com.minsk.guru.domain.usecase.auth

import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetCurrentUserUseCaseImpl(
    private val authRepository: AuthRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetCurrentUserUseCase.Param, GetCurrentUserUseCase.Result>(ioDispatcher),
    GetCurrentUserUseCase {

    override suspend fun run(param: GetCurrentUserUseCase.Param): GetCurrentUserUseCase.Result =
        try {
            val user = authRepository.getCurrentRemoteUser()
            GetCurrentUserUseCase.Result.Success(user)
        } catch (error: Throwable) {
            GetCurrentUserUseCase.Result.Failure(error)
        }
}