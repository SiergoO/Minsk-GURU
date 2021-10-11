package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetPlacesByCategoryUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetPlacesByCategoryUseCase.Param, GetPlacesByCategoryUseCase.Result>(
    ioDispatcher
), GetPlacesByCategoryUseCase {

    override suspend fun run(param: GetPlacesByCategoryUseCase.Param): GetPlacesByCategoryUseCase.Result =
        try {
            val places = placesRepository.getByCategory(param.categoryName)
            GetPlacesByCategoryUseCase.Result.Success(places)
        } catch (error: Throwable) {
            GetPlacesByCategoryUseCase.Result.Failure(error)
        }
}