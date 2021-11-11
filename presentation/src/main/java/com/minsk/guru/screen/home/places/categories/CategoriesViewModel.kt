package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.usecase.places.GetUserCategoriesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class CategoriesViewModel(
    private val userIdHolder: UserIdHolder,
    private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    private val _userCategories = MutableLiveData<List<UserCategory>>()
    val userCategories: LiveData<List<UserCategory>>
        get() = _userCategories

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val taskGetCategories = createGetCategoriesTask()

    init {
        getCategories()
    }

    private fun getCategories() {
        taskGetCategories.start(GetUserCategoriesUseCase.Param(userIdHolder.userId))
    }

    private fun handleGetCategoriesResult(data: GetUserCategoriesUseCase.Result?) {
        when (data) {
            is GetUserCategoriesUseCase.Result.Failure -> _error.value = data.error
            is GetUserCategoriesUseCase.Result.Success -> _userCategories.value = data.userCategories
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun createGetCategoriesTask() =
        taskExecutorFactory.createTaskExecutor<GetUserCategoriesUseCase.Param, GetUserCategoriesUseCase.Result>(
            singleResultUseCaseTaskProvider { getUserCategoriesUseCase },
            { data -> handleGetCategoriesResult(data) },
            { error -> handleError(error) }
        )
}