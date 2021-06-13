package com.minsk.guru.domain.usecase.achievement

import com.minsk.guru.domain.repository.room.AchievementsLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertAchievementsUseCaseImpl(
    private val achievementsLocalRepository: AchievementsLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertAchievementsUseCase.Param, InsertAchievementsUseCase.Result>(ioDispatcher),
    InsertAchievementsUseCase {

    override suspend fun run(param: InsertAchievementsUseCase.Param): InsertAchievementsUseCase.Result =
        try {
            achievementsLocalRepository.insertAchievements(param.achievements)
            InsertAchievementsUseCase.Result.Success
        } catch (error: Throwable) {
            InsertAchievementsUseCase.Result.Failure(error)
        }
}