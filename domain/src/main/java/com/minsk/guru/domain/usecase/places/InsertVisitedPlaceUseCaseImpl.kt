package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertVisitedPlaceUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertVisitedPlaceUseCase.Param, InsertVisitedPlaceUseCase.Result>(ioDispatcher),
    InsertVisitedPlaceUseCase {

    override suspend fun run(param: InsertVisitedPlaceUseCase.Param): InsertVisitedPlaceUseCase.Result =
        try {
            placesRepository.insertVisitedPlace(param.userId, param.place)
            InsertVisitedPlaceUseCase.Result.Success
        } catch (error: Throwable) {
            InsertVisitedPlaceUseCase.Result.Failure(error)
        }
}