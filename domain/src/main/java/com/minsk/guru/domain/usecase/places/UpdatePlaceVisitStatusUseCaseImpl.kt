package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UpdatePlaceVisitStatusUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<UpdatePlaceVisitStatusUseCase.Param, UpdatePlaceVisitStatusUseCase.Result>(ioDispatcher),
    UpdatePlaceVisitStatusUseCase {

    override suspend fun run(param: UpdatePlaceVisitStatusUseCase.Param): UpdatePlaceVisitStatusUseCase.Result =
        try {
            placesRepository.updatePlaceVisitStatus(param.userId, param.place, param.isVisited)
            val visitedPlaces = placesRepository.getVisitedPlacesByCategory(param.userId, param.categoryName)
            UpdatePlaceVisitStatusUseCase.Result.Success(visitedPlaces)
        } catch (error: Throwable) {
            UpdatePlaceVisitStatusUseCase.Result.Failure(error)
        }
}