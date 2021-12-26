package com.minsk.guru.domain.usecase.profile

import com.minsk.guru.domain.repository.firebase.profile.ProfileRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetProfileInfoUseCaseImpl(
    private val profileRepository: ProfileRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetProfileInfoUseCase.Param, GetProfileInfoUseCase.Result>(
    ioDispatcher
), GetProfileInfoUseCase {

    override suspend fun run(param: GetProfileInfoUseCase.Param): GetProfileInfoUseCase.Result =
        try {
            val profileInfo = profileRepository.getProfileInfo(param.userUid)
            GetProfileInfoUseCase.Result.Success(profileInfo)
        } catch (error: Throwable) {
            GetProfileInfoUseCase.Result.Failure(error)
        }
}