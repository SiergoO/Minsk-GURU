package com.minsk.guru.domain.usecase.profile

import com.minsk.guru.domain.model.ProfileInfo
import com.minsk.guru.domain.usecase.SingleResultUseCase

interface GetProfileInfoUseCase :
    SingleResultUseCase<GetProfileInfoUseCase.Param, GetProfileInfoUseCase.Result> {

    data class Param(val userUid: String)

    sealed class Result {
        data class Success(val profileInfo: ProfileInfo) : Result()
        data class Failure(val error: Throwable) : Result()
    }
}
