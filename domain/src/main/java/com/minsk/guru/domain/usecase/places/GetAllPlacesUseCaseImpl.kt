package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetAllPlacesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetAllPlacesUseCase.Param, GetAllPlacesUseCase.Result>(
    ioDispatcher
), GetAllPlacesUseCase {

    override suspend fun run(param: GetAllPlacesUseCase.Param): GetAllPlacesUseCase.Result =
        try {
            val places = placesRepository.getAllPlaces()
            GetAllPlacesUseCase.Result.Success(places)
        } catch (error: Throwable) {
            GetAllPlacesUseCase.Result.Failure(error)
        }
}