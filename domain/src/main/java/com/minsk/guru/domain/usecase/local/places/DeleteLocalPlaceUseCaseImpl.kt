package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.repository.room.PlacesLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteLocalPlaceUseCaseImpl(
    private val placesLocalRepository: PlacesLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<DeleteLocalPlaceUseCase.Param, DeleteLocalPlaceUseCase.Result>(
    ioDispatcher
),
    DeleteLocalPlaceUseCase {

    override suspend fun run(param: DeleteLocalPlaceUseCase.Param): DeleteLocalPlaceUseCase.Result =
        try {
            placesLocalRepository.deletePlace(param.place)
            DeleteLocalPlaceUseCase.Result.Success
        } catch (error: Throwable) {
            DeleteLocalPlaceUseCase.Result.Failure(error)
        }
}