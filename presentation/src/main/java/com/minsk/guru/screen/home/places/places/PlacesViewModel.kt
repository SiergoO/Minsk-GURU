package com.minsk.guru.screen.home.places.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.firebase.places.GetRemotePlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.local.places.DeleteLocalPlaceUseCase
import com.minsk.guru.domain.usecase.local.places.GetVisitedLocalPlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.local.places.InsertLocalPlaceUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class PlacesViewModel(
    private val getRemotePlacesByCategoryUseCase: GetRemotePlacesByCategoryUseCase,
    private val getVisitedLocalPlacesByCategoryUseCase: GetVisitedLocalPlacesByCategoryUseCase,
    private val insertLocalPlaceUseCase: InsertLocalPlaceUseCase,
    private val deleteLocalPlaceUseCase: DeleteLocalPlaceUseCase,
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
        taskGetRemotePlacesByCategory.start(GetRemotePlacesByCategoryUseCase.Param(categoryName))
        taskGetVisitedLocalPlacesByCategory.start(GetVisitedLocalPlacesByCategoryUseCase.Param(categoryName))
    }

    fun updateLocalPlace(place: Place, isVisited: Boolean) {
        if (isVisited) {
            taskInsertPlace.start(InsertLocalPlaceUseCase.Param("orcyLIhh9QZq4XwmHLlqfGhzOY53", place)) //TODO
        } else {
            taskDeletePlace.start(DeleteLocalPlaceUseCase.Param("orcyLIhh9QZq4XwmHLlqfGhzOY53", place))
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun handleGetRemotePlacesByCategory(data: GetRemotePlacesByCategoryUseCase.Result) {
        when (data) {
            is GetRemotePlacesByCategoryUseCase.Result.Success -> allPlacesByCategoryLiveData.value = data.places
            is GetRemotePlacesByCategoryUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleGetVisitedLocalPlacesByCategoryResult(data: GetVisitedLocalPlacesByCategoryUseCase.Result) {
        when (data) {
            is GetVisitedLocalPlacesByCategoryUseCase.Result.Success -> visitedPlacesByCategoryLiveData.value = data.visitedPlaces
            is GetVisitedLocalPlacesByCategoryUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleInsertPlaceResult(data: InsertLocalPlaceUseCase.Result) {
        when (data) {
            is InsertLocalPlaceUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleDeletePlaceResult(data: DeleteLocalPlaceUseCase.Result) {
        when (data) {
            is DeleteLocalPlaceUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun createGetRemotePlacesByCategoryTask() =
        taskExecutorFactory.createTaskExecutor<GetRemotePlacesByCategoryUseCase.Param, GetRemotePlacesByCategoryUseCase.Result>(
            singleResultUseCaseTaskProvider { getRemotePlacesByCategoryUseCase },
            { data -> handleGetRemotePlacesByCategory(data) },
            { error -> handleError(error) }
        )

    private fun createGetVisitedLocalPlacesByCategoryTask() =
        taskExecutorFactory.createTaskExecutor<GetVisitedLocalPlacesByCategoryUseCase.Param, GetVisitedLocalPlacesByCategoryUseCase.Result>(
            singleResultUseCaseTaskProvider { getVisitedLocalPlacesByCategoryUseCase },
            { data -> handleGetVisitedLocalPlacesByCategoryResult(data) },
            { error -> handleError(error) }
        )

    private fun createInsertPlaceTask() =
        taskExecutorFactory.createTaskExecutor<InsertLocalPlaceUseCase.Param, InsertLocalPlaceUseCase.Result>(
            singleResultUseCaseTaskProvider { insertLocalPlaceUseCase },
            { data -> handleInsertPlaceResult(data) },
            { error -> handleError(error) }
        )

    private fun createDeletePlaceTask() =
        taskExecutorFactory.createTaskExecutor<DeleteLocalPlaceUseCase.Param, DeleteLocalPlaceUseCase.Result>(
            singleResultUseCaseTaskProvider { deleteLocalPlaceUseCase },
            { data -> handleDeletePlaceResult(data) },
            { error -> handleError(error) }
        )
}