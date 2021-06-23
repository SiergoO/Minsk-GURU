package com.minsk.guru.domain.usecase.local.user

import com.minsk.guru.domain.repository.local.UserLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertLocalUserUseCaseImpl(
    private val userLocalRepository: UserLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertLocalUserUseCase.Param, InsertLocalUserUseCase.Result>(ioDispatcher),
    InsertLocalUserUseCase {

    override suspend fun run(param: InsertLocalUserUseCase.Param): InsertLocalUserUseCase.Result =
        try {
            userLocalRepository.insertUser(param.user)
            InsertLocalUserUseCase.Result.Success
        } catch (error: Throwable) {
            InsertLocalUserUseCase.Result.Failure(error)
        }
}