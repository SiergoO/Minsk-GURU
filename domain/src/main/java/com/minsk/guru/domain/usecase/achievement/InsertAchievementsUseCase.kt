package com.minsk.guru.domain.usecase.achievement

import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface InsertAchievementsUseCase :
    SingleResultUseCase<InsertAchievementsUseCase.Param, InsertAchievementsUseCase.Result> {

    data class Param(val achievements: List<Achievement>)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}