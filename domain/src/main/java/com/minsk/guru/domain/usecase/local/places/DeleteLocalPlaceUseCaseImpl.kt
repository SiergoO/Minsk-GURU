package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.model.UserPlace
import com.minsk.guru.domain.repository.local.UserPlacesLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteLocalPlaceUseCaseImpl(
    private val userPlacesLocalRepository: UserPlacesLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<DeleteLocalPlaceUseCase.Param, DeleteLocalPlaceUseCase.Result>(
    ioDispatcher
),
    DeleteLocalPlaceUseCase {

    override suspend fun run(param: DeleteLocalPlaceUseCase.Param): DeleteLocalPlaceUseCase.Result =
        try {
            val place = param.place
            userPlacesLocalRepository.deleteUserPlace(UserPlace(param.userId, place.id))
            DeleteLocalPlaceUseCase.Result.Success
        } catch (error: Throwable) {
            DeleteLocalPlaceUseCase.Result.Failure(error)
        }
}