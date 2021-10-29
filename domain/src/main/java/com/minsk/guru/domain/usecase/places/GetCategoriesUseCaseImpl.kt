package com.minsk.guru.domain.usecase.places

import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetCategoriesUseCaseImpl(
    private val placesRepository: PlacesRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result>(
    ioDispatcher
), GetCategoriesUseCase {

    override suspend fun run(param: GetCategoriesUseCase.Param): GetCategoriesUseCase.Result =
        try {
            val list: List<UserCategory>
            val categories = placesRepository.getCategories()
            val visitedPlaces = placesRepository.getPlacesVisitedByUser(param.userId)
            list = categories.map { category ->
                UserCategory(
                    category.name,
                    category.placesIds,
                    visitedPlaces.filter { place -> category.placesIds.contains(place.id) })
            }
            GetCategoriesUseCase.Result.Success(list)
        } catch (error: Throwable) {
            GetCategoriesUseCase.Result.Failure(error)
        }
}