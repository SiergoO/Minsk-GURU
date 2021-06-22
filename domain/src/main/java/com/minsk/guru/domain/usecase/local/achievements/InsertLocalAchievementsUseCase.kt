package com.minsk.guru.domain.usecase

import com.minsk.guru.domain.model.Achievement

interface InsertLocalAchievementsUseCase :
    SingleResultUseCase<InsertLocalAchievementsUseCase.Param, InsertLocalAchievementsUseCase.Result> {

    data class Param(val achievements: List<Achievement>)

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Throwable) : Result()
    }
}