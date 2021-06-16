package com.minsk.guru.domain.usecase.local.user

import com.minsk.guru.domain.repository.room.UserLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertUserUseCaseImpl(
    private val userLocalRepository: UserLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertUserUseCase.Param, InsertUserUseCase.Result>(ioDispatcher),
    InsertUserUseCase {

    override suspend fun run(param: InsertUserUseCase.Param): InsertUserUseCase.Result =
        try {
            userLocalRepository.insertUser(param.user)
            InsertUserUseCase.Result.Success
        } catch (error: Throwable) {
            InsertUserUseCase.Result.Failure(error)
        }
}