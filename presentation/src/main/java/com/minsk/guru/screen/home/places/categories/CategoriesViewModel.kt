package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.usecase.places.GetCategoriesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class CategoriesViewModel(
    private val userIdHolder: UserIdHolder,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    private val _categories = MutableLiveData<List<UserCategory>>()
    val categories: LiveData<List<UserCategory>>
        get() = _categories

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val taskGetCategories = createGetCategoriesTask()

    init {
        getCategories()
    }

    private fun getCategories() {
        taskGetCategories.start(GetCategoriesUseCase.Param(userIdHolder.userId))
    }

    private fun handleGetCategoriesResult(data: GetCategoriesUseCase.Result?) {
        when (data) {
            is GetCategoriesUseCase.Result.Failure -> _error.value = data.error
            is GetCategoriesUseCase.Result.Success -> _categories.value = data.categories
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun createGetCategoriesTask() =
        taskExecutorFactory.createTaskExecutor<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result>(
            singleResultUseCaseTaskProvider { getCategoriesUseCase },
            { data -> handleGetCategoriesResult(data) },
            { error -> handleError(error) }
        )
}