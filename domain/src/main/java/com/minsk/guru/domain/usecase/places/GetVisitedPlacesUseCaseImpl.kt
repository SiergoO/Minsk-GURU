package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetVisitedPlacesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetVisitedPlacesUseCase.Param, GetVisitedPlacesUseCase.Result>(ioDispatcher),
    GetVisitedPlacesUseCase {

    override suspend fun run(param: GetVisitedPlacesUseCase.Param): GetVisitedPlacesUseCase.Result =
        try {
            val userId = param.userId
            val visitedPlaces = placesRepository.getPlacesVisitedByUser(userId)
            GetVisitedPlacesUseCase.Result.Success(visitedPlaces)
        } catch (error: Throwable) {
            GetVisitedPlacesUseCase.Result.Failure(error)
        }
}