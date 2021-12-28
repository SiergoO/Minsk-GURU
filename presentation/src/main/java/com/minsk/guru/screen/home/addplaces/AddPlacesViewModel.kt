package com.minsk.guru.screen.home.addplaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.places.GetAllPlacesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class AddPlacesViewModel(
    private val taskExecutorFactory: TaskExecutorFactory,
    private val getUserPlacesUseCase: GetAllPlacesUseCase
) : ViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>>
        get() = _places

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val taskGetPlaces = createGetAllPlacesTask()

    init {
        taskGetPlaces.start(GetAllPlacesUseCase.Param)
    }

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun handleGetVisitedPlacesResult(data: GetAllPlacesUseCase.Result?) {
        when (data) {
            is GetAllPlacesUseCase.Result.Failure -> _error.value = data.error
            is GetAllPlacesUseCase.Result.Success -> _places.value = data.places
            else -> Unit
        }
    }

    private fun createGetAllPlacesTask() =
        taskExecutorFactory.createTaskExecutor<GetAllPlacesUseCase.Param, GetAllPlacesUseCase.Result>(
            singleResultUseCaseTaskProvider { getUserPlacesUseCase },
            { data -> handleGetVisitedPlacesResult(data) },
            { error -> handleError(error) }
        )

}