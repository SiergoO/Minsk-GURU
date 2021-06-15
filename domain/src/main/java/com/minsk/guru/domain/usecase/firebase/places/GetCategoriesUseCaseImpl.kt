package com.minsk.guru.domain.usecase.firebase.places

import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetCategoriesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result>(ioDispatcher),
    GetCategoriesUseCase {

    override suspend fun run(param: GetCategoriesUseCase.Param): GetCategoriesUseCase.Result =
        try {
            val categories = placesRepository.getCategories()
            GetCategoriesUseCase.Result.Success(categories)
        } catch (error: Throwable) {
            GetCategoriesUseCase.Result.Failure(error)
        }
}