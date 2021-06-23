package com.minsk.guru.domain.usecase.firebase.achievements

import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetAchievementsUseCaseImpl(
    private val achievementsRepository: AchievementsRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetAchievementsUseCase.Param, GetAchievementsUseCase.Result>(ioDispatcher),
    GetAchievementsUseCase {

    override suspend fun run(param: GetAchievementsUseCase.Param): GetAchievementsUseCase.Result =
        try {
            val achievements = achievementsRepository.getRemoteAchievements()
            GetAchievementsUseCase.Result.Success(achievements)
        } catch (error: Throwable) {
            GetAchievementsUseCase.Result.Failure(error)
        }
}