package com.minsk.guru.domain.usecase.firebase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetRemotePlacesByCategoryUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetRemotePlacesByCategoryUseCase.Param, GetRemotePlacesByCategoryUseCase.Result>(
    ioDispatcher
), GetRemotePlacesByCategoryUseCase {

    override suspend fun run(param: GetRemotePlacesByCategoryUseCase.Param): GetRemotePlacesByCategoryUseCase.Result =
        try {
            val places = placesRepository.getByCategory(param.categoryName)
            GetRemotePlacesByCategoryUseCase.Result.Success(places)
        } catch (error: Throwable) {
            GetRemotePlacesByCategoryUseCase.Result.Failure(error)
        }
}