package com.minsk.guru.domain.usecase.local.places

import com.minsk.guru.domain.model.UserPlace
import com.minsk.guru.domain.repository.local.PlacesLocalRepository
import com.minsk.guru.domain.repository.local.UserPlacesLocalRepository
import com.minsk.guru.domain.usecase.CoroutineSingleResultUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class InsertLocalPlaceUseCaseImpl(
    private val placesLocalRepository: PlacesLocalRepository,
    private val userPlacesLocalRepository: UserPlacesLocalRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineSingleResultUseCase<InsertLocalPlaceUseCase.Param, InsertLocalPlaceUseCase.Result>(ioDispatcher),
    InsertLocalPlaceUseCase {

    override suspend fun run(param: InsertLocalPlaceUseCase.Param): InsertLocalPlaceUseCase.Result =
        try {
            val place = param.place
            placesLocalRepository.insertPlace(place)
            userPlacesLocalRepository.insertUserPlace(UserPlace(param.userId, place.id))
            InsertLocalPlaceUseCase.Result.Success
        } catch (error: Throwable) {
            InsertLocalPlaceUseCase.Result.Failure(error)
        }
}