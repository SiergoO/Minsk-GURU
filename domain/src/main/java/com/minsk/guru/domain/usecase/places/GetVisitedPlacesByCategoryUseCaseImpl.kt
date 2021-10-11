package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetVisitedPlacesByCategoryUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetVisitedPlacesByCategoryUseCase.Param, GetVisitedPlacesByCategoryUseCase.Result>(ioDispatcher),
    GetVisitedPlacesByCategoryUseCase {

    override suspend fun run(param: GetVisitedPlacesByCategoryUseCase.Param): GetVisitedPlacesByCategoryUseCase.Result =
        try {
            val visitedPlaces = placesRepository.getVisitedPlacesByCategory(param.userId, param.categoryName)
            GetVisitedPlacesByCategoryUseCase.Result.Success(visitedPlaces)
        } catch (error: Throwable) {
            GetVisitedPlacesByCategoryUseCase.Result.Failure(error)
        }
}