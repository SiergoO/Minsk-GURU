package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.firebase.FirebaseAchievement
import com.minsk.guru.domain.usecase.InsertLocalAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.places.GetCategoriesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class CategoriesViewModel(
    private val getAchievementsUseCase: GetAchievementsUseCaseImpl,
    private val insertLocalAchievementsUseCase: InsertLocalAchievementsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var places: LiveData<List<FirebaseAchievement>> = MutableLiveData()

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<List<Category>>()
    private val taskGetAchievements = createGetAchievementsTask()
    private val taskInsertAchievements = createInsertAchievementsTask()
    private val taskGetCategories = createGetCategoriesTask()

    init {
        taskGetAchievements.start(GetAchievementsUseCase.Param)
    }

    fun getCategories() = taskGetCategories.start(GetCategoriesUseCase.Param)

    private fun handleGetAchievementsResult(data: GetAchievementsUseCase.Result) {
        when (data) {
            is GetAchievementsUseCase.Result.Failure -> errorLiveData.value = data.error
            is GetAchievementsUseCase.Result.Success -> {
                taskInsertAchievements.start(InsertLocalAchievementsUseCase.Param(data.achievements))
            }
            else -> Unit
        }
    }

    private fun handleInsertAchievementsResult(data: InsertLocalAchievementsUseCase.Result) {
        when (data) {
            is InsertLocalAchievementsUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }


    private fun handleGetCategoriesResult(data: GetCategoriesUseCase.Result) {
        when (data) {
            is GetCategoriesUseCase.Result.Failure -> errorLiveData.value = data.error
            is GetCategoriesUseCase.Result.Success -> resultLiveData.value = data.categories
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun createGetAchievementsTask() =
        taskExecutorFactory.createTaskExecutor<GetAchievementsUseCase.Param, GetAchievementsUseCase.Result>(
            singleResultUseCaseTaskProvider { getAchievementsUseCase },
            { data -> handleGetAchievementsResult(data) },
            { error -> handleError(error) }
        )

    private fun createInsertAchievementsTask() =
        taskExecutorFactory.createTaskExecutor<InsertLocalAchievementsUseCase.Param, InsertLocalAchievementsUseCase.Result>(
            singleResultUseCaseTaskProvider { insertLocalAchievementsUseCase },
            { data -> handleInsertAchievementsResult(data) },
            { error -> handleError(error) }
        )

    private fun createGetCategoriesTask() =
        taskExecutorFactory.createTaskExecutor<GetCategoriesUseCase.Param, GetCategoriesUseCase.Result>(
            singleResultUseCaseTaskProvider { getCategoriesUseCase },
            { data -> handleGetCategoriesResult(data) },
            { error -> handleError(error) }
        )
}