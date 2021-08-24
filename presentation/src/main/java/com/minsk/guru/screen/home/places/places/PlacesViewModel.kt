package com.minsk.guru.screen.home.places.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.places.DeleteVisitedPlaceUseCase
import com.minsk.guru.domain.usecase.places.GetPlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.places.GetVisitedPlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.places.InsertVisitedPlaceUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class PlacesViewModel(
    private val userIdHolder: UserIdHolder,
    private val insertVisitedPlaceUseCase: InsertVisitedPlaceUseCase,
    private val deleteVisitedPlaceUseCase: DeleteVisitedPlaceUseCase,
    private val getPlacesByCategoryUseCase: GetPlacesByCategoryUseCase,
    private val getVisitedPlacesByCategoryUseCase: GetVisitedPlacesByCategoryUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var places: LiveData<List<Place>> = MutableLiveData()
    private val taskGetRemotePlacesByCategory = createGetRemotePlacesByCategoryTask()
    private val taskGetVisitedLocalPlacesByCategory = createGetVisitedLocalPlacesByCategoryTask()
    private val taskInsertPlace = createInsertPlaceTask()
    private val taskDeletePlace = createDeletePlaceTask()

    var errorLiveData = MutableLiveData<Throwable>()
    var allPlacesByCategoryLiveData = MutableLiveData<List<Place>>()
    var visitedPlacesByCategoryLiveData = MutableLiveData<List<Place>>()

    fun getPlacesByCategory(categoryName: String?) {
        taskGetRemotePlacesByCategory.start(GetPlacesByCategoryUseCase.Param(categoryName))
        taskGetVisitedLocalPlacesByCategory.start(
            GetVisitedPlacesByCategoryUseCase.Param(
                userIdHolder.userId,
                categoryName
            )
        )
    }

    fun updateLocalPlace(place: Place, isVisited: Boolean) {
        if (isVisited) {
            taskInsertPlace.start(InsertVisitedPlaceUseCase.Param(userIdHolder.userId, place))
        } else {
            taskDeletePlace.start(DeleteVisitedPlaceUseCase.Param(userIdHolder.userId, place))
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun handleGetRemotePlacesByCategory(data: GetPlacesByCategoryUseCase.Result) {
        when (data) {
            is GetPlacesByCategoryUseCase.Result.Success -> allPlacesByCategoryLiveData.value =
                data.places
            is GetPlacesByCategoryUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleGetVisitedLocalPlacesByCategoryResult(data: GetVisitedPlacesByCategoryUseCase.Result) {
        when (data) {
            is GetVisitedPlacesByCategoryUseCase.Result.Success -> visitedPlacesByCategoryLiveData.value =
                data.visitedPlaces
            is GetVisitedPlacesByCategoryUseCase.Result.Failure -> errorLiveData.value =
                data.error
            else -> Unit
        }
    }

    private fun handleInsertVisitedPlaceResult(data: InsertVisitedPlaceUseCase.Result) {
        when (data) {
            is InsertVisitedPlaceUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleDeleteVisitedPlaceResult(data: DeleteVisitedPlaceUseCase.Result) {
        when (data) {
            is DeleteVisitedPlaceUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun createGetRemotePlacesByCategoryTask() =
        taskExecutorFactory.createTaskExecutor<GetPlacesByCategoryUseCase.Param, GetPlacesByCategoryUseCase.Result>(
            singleResultUseCaseTaskProvider { getPlacesByCategoryUseCase },
            { data -> handleGetRemotePlacesByCategory(data) },
            { error -> handleError(error) }
        )

    private fun createGetVisitedLocalPlacesByCategoryTask() =
        taskExecutorFactory.createTaskExecutor<GetVisitedPlacesByCategoryUseCase.Param, GetVisitedPlacesByCategoryUseCase.Result>(
            singleResultUseCaseTaskProvider { getVisitedPlacesByCategoryUseCase },
            { data -> handleGetVisitedLocalPlacesByCategoryResult(data) },
            { error -> handleError(error) }
        )

    private fun createInsertPlaceTask() =
        taskExecutorFactory.createTaskExecutor<InsertVisitedPlaceUseCase.Param, InsertVisitedPlaceUseCase.Result>(
            singleResultUseCaseTaskProvider { insertVisitedPlaceUseCase },
            { data -> handleInsertVisitedPlaceResult(data) },
            { error -> handleError(error) }
        )

    private fun createDeletePlaceTask() =
        taskExecutorFactory.createTaskExecutor<DeleteVisitedPlaceUseCase.Param, DeleteVisitedPlaceUseCase.Result>(
            singleResultUseCaseTaskProvider { deleteVisitedPlaceUseCase },
            { data -> handleDeleteVisitedPlaceResult(data) },
            { error -> handleError(error) }
        )
}