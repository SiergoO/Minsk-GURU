package com.minsk.guru.screen.home.addplaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.places.GetAllPlacesUseCase
import com.minsk.guru.domain.usecase.places.GetVisitedPlacesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider
import com.yandex.mapkit.map.PlacemarkMapObject

class AddPlacesViewModel(
    userIdHolder: UserIdHolder,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val getUserPlacesUseCase: GetAllPlacesUseCase,
    private val getVisitedPlacesUseCase: GetVisitedPlacesUseCase
) : ViewModel() {

    var lastCheckedPlaceMark: PlacemarkMapObject? = null

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>>
        get() = _places

    private val _visitedPlaces = MutableLiveData<List<Place>>()
    val visitedPlaces: LiveData<List<Place>>
        get() = _visitedPlaces

    val visitedAndAllPlaces = MediatorLiveData<Pair<List<Place>, List<Place>>>()

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val taskGetPlaces = createGetAllPlacesTask()
    private val taskGetVisitedPlaces = createGetVisitedPlacesTask()

    init {
        taskGetPlaces.start(GetAllPlacesUseCase.Param)
        taskGetVisitedPlaces.start(GetVisitedPlacesUseCase.Param(userIdHolder.userId))
        initializeMediatorLiveData()
    }

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun handleGetAllPlacesResult(data: GetAllPlacesUseCase.Result?) {
        when (data) {
            is GetAllPlacesUseCase.Result.Failure -> _error.value = data.error
            is GetAllPlacesUseCase.Result.Success -> _places.value = data.places
            else -> Unit
        }
    }

    private fun createGetAllPlacesTask() =
        taskExecutorFactory.createTaskExecutor<GetAllPlacesUseCase.Param, GetAllPlacesUseCase.Result>(
            singleResultUseCaseTaskProvider { getUserPlacesUseCase },
            { data -> handleGetAllPlacesResult(data) },
            { error -> handleError(error) }
        )

    private fun handleGetVisitedPlacesResult(data: GetVisitedPlacesUseCase.Result?) {
        when (data) {
            is GetVisitedPlacesUseCase.Result.Failure -> _error.value = data.error
            is GetVisitedPlacesUseCase.Result.Success -> _visitedPlaces.value = data.visitedPlaces
            else -> Unit
        }
    }

    private fun createGetVisitedPlacesTask() =
        taskExecutorFactory.createTaskExecutor<GetVisitedPlacesUseCase.Param, GetVisitedPlacesUseCase.Result>(
            singleResultUseCaseTaskProvider { getVisitedPlacesUseCase },
            { data -> handleGetVisitedPlacesResult(data) },
            { error -> handleError(error) }
        )

    private fun initializeMediatorLiveData() {
        visitedAndAllPlaces.addSource(places) {
            visitedAndAllPlaces.value = Pair(it, visitedPlaces.value ?: listOf())
        }
        visitedAndAllPlaces.addSource(visitedPlaces) {
            visitedAndAllPlaces.value = Pair(places.value ?: listOf(), it)
        }
    }
}