package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetUserCategoriesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetUserCategoriesUseCase.Param, GetUserCategoriesUseCase.Result>(
    ioDispatcher
), GetUserCategoriesUseCase {

    override suspend fun run(param: GetUserCategoriesUseCase.Param): GetUserCategoriesUseCase.Result =
        try {
            val categories = placesRepository.getCategories()
            val visitedPlaces = placesRepository.getPlacesVisitedByUser(param.userId)
            val list = categories.map { category ->
                UserCategory(
                    category.name,
                    category.places,
                    visitedPlaces.filter { place -> category.places.any {it.id == place.id} })
            }
            GetUserCategoriesUseCase.Result.Success(list)
        } catch (error: Throwable) {
            GetUserCategoriesUseCase.Result.Failure(error)
        }
}