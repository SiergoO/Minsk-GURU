package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.repository.local.PlacesLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetVisitedLocalPlacesByCategoryUseCaseImpl(
    private val placesLocalRepository: PlacesLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetVisitedLocalPlacesByCategoryUseCase.Param, GetVisitedLocalPlacesByCategoryUseCase.Result>(ioDispatcher),
    GetVisitedLocalPlacesByCategoryUseCase {

    override suspend fun run(param: GetVisitedLocalPlacesByCategoryUseCase.Param): GetVisitedLocalPlacesByCategoryUseCase.Result =
        try {
            val visitedPlaces = placesLocalRepository.getPlacesByCategory(param.userId, param.categoryName)
            GetVisitedLocalPlacesByCategoryUseCase.Result.Success(visitedPlaces)
        } catch (error: Throwable) {
            GetVisitedLocalPlacesByCategoryUseCase.Result.Failure(error)
        }
}