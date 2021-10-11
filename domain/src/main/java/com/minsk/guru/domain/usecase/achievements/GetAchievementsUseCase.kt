package com.minsk.guru.domain.usecase.achievements

import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetAchievementsUseCase:
    SingleResultUseCase<GetAchievementsUseCase.Param, GetAchievementsUseCase.Result> {

    object Param

    sealed class Result {
        data class Success(val achievements: List<Achievement>) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}