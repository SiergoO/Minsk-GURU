package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteVisitedPlaceUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<DeleteVisitedPlaceUseCase.Param, DeleteVisitedPlaceUseCase.Result>(
    ioDispatcher
),
    DeleteVisitedPlaceUseCase {

    override suspend fun run(param: DeleteVisitedPlaceUseCase.Param): DeleteVisitedPlaceUseCase.Result =
        try {
            placesRepository.deleteVisitedPlace(param.userId, param.place.id)
            DeleteVisitedPlaceUseCase.Result.Success
        } catch (error: Throwable) {
            DeleteVisitedPlaceUseCase.Result.Failure(error)
        }
}