package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.remote.RemoteAchievement
import com.minsk.guru.domain.usecase.places.GetCategoriesUseCase
import com.minsk.guru.domain.usecase.places.GetVisitedPlacesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class CategoriesViewModel(
    private val userIdHolder: UserIdHolder,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getVisitedPlaces: GetVisitedPlacesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var places: LiveData<List<RemoteAchievement>> = MutableLiveData()

    var errorLiveData = MutableLiveData<Throwable>()
    var categoriesResultLiveData = MutableLiveData<List<Category>>()
    var visitedPlacesResultLiveData = MutableLiveData<List<Place>>()
    private val taskGetCategories = createGetCategoriesTask()
    private val taskGetVisitedPlaces = createGetVisitedPlacesTask()

    fun getCategories() {
        taskGetCategories.start(GetCategoriesUseCase.Param)
        taskGetVisitedPlaces.start(GetVisitedPlacesUseCase.Param(userIdHolder.userId))
    }

    private fun handleGetCategoriesResult(data: GetCategoriesUseCase.Result) {
        when (data) {
            is GetCategoriesUseCase.Result.Failure -> errorLiveData.value = data.error
            is GetCategoriesUseCase.Result.Success -> categoriesResultLiveData.value =
                data.categories
            else -> Unit
        }
    }

    private fun handleGetVisitedPlacesResult(data: GetVisitedPlacesUseCase.Result) {
        when (data) {
            is GetVisitedPlacesUseCase.Result.Failure -> errorLiveData.value = data.error
            is GetVisitedPlacesUseCase.Result.Success -> visitedPlacesResultLiveData.value =
                data.visitedPlaces
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun createGetCategoriesTask() =
        taskExecutorFactory.createTaskExecutor<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result>(
            singleResultUseCaseTaskProvider { getCategoriesUseCase },
            { data -> handleGetCategoriesResult(data) },
            { error -> handleError(error) }
        )

    private fun createGetVisitedPlacesTask() =
        taskExecutorFactory.createTaskExecutor<GetVisitedPlacesUseCase.Param, GetVisitedPlacesUseCase.Result>(
            singleResultUseCaseTaskProvider { getVisitedPlaces },
            { data -> handleGetVisitedPlacesResult(data) },
            { error -> handleError(error) }
        )
}