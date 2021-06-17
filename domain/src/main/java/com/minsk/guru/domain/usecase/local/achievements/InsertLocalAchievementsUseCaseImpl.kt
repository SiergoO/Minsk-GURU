package com.minsk.guru.domain.usecase

import com.minsk.guru.domain.repository.local.AchievementsLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertLocalAchievementsUseCaseImpl(
    private val achievementsLocalRepository: AchievementsLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertLocalAchievementsUseCase.Param, InsertLocalAchievementsUseCase.Result>(ioDispatcher),
    InsertLocalAchievementsUseCase {

    override suspend fun run(param: InsertLocalAchievementsUseCase.Param): InsertLocalAchievementsUseCase.Result =
        try {
            achievementsLocalRepository.insertAchievements(param.achievements)
            InsertLocalAchievementsUseCase.Result.Success
        } catch (error: Throwable) {
            InsertLocalAchievementsUseCase.Result.Failure(error)
        }
}