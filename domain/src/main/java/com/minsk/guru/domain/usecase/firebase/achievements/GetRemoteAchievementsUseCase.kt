package com.minsk.guru.domain.usecase.firebase.achievements

import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetRemoteAchievementsUseCase:
    SingleResultUseCase<GetRemoteAchievementsUseCase.Param, GetRemoteAchievementsUseCase.Result> {

    object Param

    sealed class Result {
        data class Success(val achievements: List<Achievement>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}