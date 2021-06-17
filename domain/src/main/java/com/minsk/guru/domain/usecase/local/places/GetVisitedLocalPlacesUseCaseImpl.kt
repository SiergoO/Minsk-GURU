package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.repository.local.PlacesLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetVisitedLocalPlacesUseCaseImpl(
    private val placesLocalRepository: PlacesLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetVisitedLocalPlacesUseCase.Param, GetVisitedLocalPlacesUseCase.Result>(ioDispatcher),
    GetVisitedLocalPlacesUseCase {

    override suspend fun run(param: GetVisitedLocalPlacesUseCase.Param): GetVisitedLocalPlacesUseCase.Result =
        try {
            val visitedPlaces = placesLocalRepository.getPlaces()
            GetVisitedLocalPlacesUseCase.Result.Success(visitedPlaces)
        } catch (error: Throwable) {
            GetVisitedLocalPlacesUseCase.Result.Failure(error)
        }
}