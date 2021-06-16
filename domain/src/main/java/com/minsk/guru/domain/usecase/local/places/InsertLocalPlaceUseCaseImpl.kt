package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.repository.room.PlacesLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertLocalPlaceUseCaseImpl(
    private val placesLocalRepository: PlacesLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertLocalPlaceUseCase.Param, InsertLocalPlaceUseCase.Result>(ioDispatcher),
    InsertLocalPlaceUseCase {

    override suspend fun run(param: InsertLocalPlaceUseCase.Param): InsertLocalPlaceUseCase.Result =
        try {
            placesLocalRepository.insertPlace(param.place)
            InsertLocalPlaceUseCase.Result.Success
        } catch (error: Throwable) {
            InsertLocalPlaceUseCase.Result.Failure(error)
        }
}