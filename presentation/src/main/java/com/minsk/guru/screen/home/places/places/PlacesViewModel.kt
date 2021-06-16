package com.minsk.guru.screen.home.places.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.firebase.places.GetPlacesUseCase
import com.minsk.guru.domain.usecase.local.places.DeleteLocalPlaceUseCase
import com.minsk.guru.domain.usecase.local.places.InsertLocalPlaceUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class PlacesViewModel(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val insertLocalPlaceUseCase: InsertLocalPlaceUseCase,
    private val deleteLocalPlaceUseCase: DeleteLocalPlaceUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var places: LiveData<List<Place>> = MutableLiveData()
    private val taskInsertPlace = createInsertPlaceTask()
    private val taskDeletePlace = createDeletePlaceTask()

    var errorLiveData = MutableLiveData<Throwable>()
//    var resultLiveData = MutableLiveData<List<Place>>()

    fun getPlacesByCategory(categoryName: String?) {
        places = liveData {
            emit(getPlacesUseCase.getPlacesByCategory(categoryName))
        }
    }

    fun updateLocalPlace(place: Place, isVisited: Boolean) {
        if (isVisited) {
            taskInsertPlace.start(InsertLocalPlaceUseCase.Param(place))
        } else {
            taskDeletePlace.start(DeleteLocalPlaceUseCase.Param(place))
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
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