package com.minsk.guru.domain.usecase.firebase.achievements

import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetRemoteAchievementsUseCaseImpl(
    private val achievementsRepository: AchievementsRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetRemoteAchievementsUseCase.Param, GetRemoteAchievementsUseCase.Result>(ioDispatcher),
    GetRemoteAchievementsUseCase {

    override suspend fun run(param: GetRemoteAchievementsUseCase.Param): GetRemoteAchievementsUseCase.Result =
        try {
            val achievements = achievementsRepository.getRemoteAchievements()
            GetRemoteAchievementsUseCase.Result.Success(achievements)
        } catch (error: Throwable) {
            GetRemoteAchievementsUseCase.Result.Failure(error)
        }
}