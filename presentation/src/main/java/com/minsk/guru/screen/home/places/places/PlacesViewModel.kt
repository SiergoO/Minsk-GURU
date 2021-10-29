package com.minsk.guru.screen.home.places.places

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.places.GetPlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.places.GetVisitedPlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.places.UpdatePlaceVisitStatusUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class PlacesViewModel(
    private val userIdHolder: UserIdHolder,
    private val updatePlaceVisitStatusUseCase: UpdatePlaceVisitStatusUseCase,
    private val getPlacesByCategoryUseCase: GetPlacesByCategoryUseCase,
    private val getVisitedPlacesByCategoryUseCase: GetVisitedPlacesByCategoryUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    private val taskGetRemotePlacesByCategory = createGetPlacesByCategoryTask()
    private val taskGetVisitedLocalPlacesByCategory = createGetVisitedPlacesByCategoryTask()
    private val taskUpdatePlaceVisitStatus = createUpdatePlaceVisitStatusTask()

    var errorLiveData = MutableLiveData<Throwable>()
    var placesByCategory = MutableLiveData<List<Place>>()
    var visitedPlacesByCategory = MutableLiveData<List<Place>>()

    fun getPlacesByCategory(categoryName: String) {
        taskGetRemotePlacesByCategory.start(GetPlacesByCategoryUseCase.Param(categoryName))
        taskGetVisitedLocalPlacesByCategory.start(
            GetVisitedPlacesByCategoryUseCase.Param(
                userIdHolder.userId,
                categoryName
            )
        )
    }

    fun updateLocalPlace(place: Place, isVisited: Boolean) {
        taskUpdatePlaceVisitStatus.start(
            UpdatePlaceVisitStatusUseCase.Param(userIdHolder.userId, place, isVisited)
        )
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun handleGetPlacesByCategory(data: GetPlacesByCategoryUseCase.Result) {
        when (data) {
            is GetPlacesByCategoryUseCase.Result.Success -> placesByCategory.value = data.places
            is GetPlacesByCategoryUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleGetVisitedLocalPlacesByCategoryResult(data: GetVisitedPlacesByCategoryUseCase.Result) {
        when (data) {
            is GetVisitedPlacesByCategoryUseCase.Result.Success -> visitedPlacesByCategory.value =
                data.visitedPlaces
            is GetVisitedPlacesByCategoryUseCase.Result.Failure -> errorLiveData.value =
                data.error
            else -> Unit
        }
    }

    private fun handlePlaceVisitStatusUpdated(data: UpdatePlaceVisitStatusUseCase.Result) {
        when (data) {
            is UpdatePlaceVisitStatusUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun createGetPlacesByCategoryTask() =
        taskExecutorFactory.createTaskExecutor<GetPlacesByCategoryUseCase.Param, GetPlacesByCategoryUseCase.Result>(
            singleResultUseCaseTaskProvider { getPlacesByCategoryUseCase },
            { data -> handleGetPlacesByCategory(data) },
            { error -> handleError(error) }
        )

    private fun createGetVisitedPlacesByCategoryTask() =
        taskExecutorFactory.createTaskExecutor<GetVisitedPlacesByCategoryUseCase.Param, GetVisitedPlacesByCategoryUseCase.Result>(
            singleResultUseCaseTaskProvider { getVisitedPlacesByCategoryUseCase },
            { data -> handleGetVisitedLocalPlacesByCategoryResult(data) },
            { error -> handleError(error) }
        )

    private fun createUpdatePlaceVisitStatusTask() =
        taskExecutorFactory.createTaskExecutor<UpdatePlaceVisitStatusUseCase.Param, UpdatePlaceVisitStatusUseCase.Result>(
            singleResultUseCaseTaskProvider { updatePlaceVisitStatusUseCase },
            { data -> handlePlaceVisitStatusUpdated(data) },
            { error -> handleError(error) }
        )
}