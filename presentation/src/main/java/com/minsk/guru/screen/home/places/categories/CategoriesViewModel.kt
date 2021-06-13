package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.model.firebase.FirebaseAchievement
import com.minsk.guru.domain.usecase.achievement.InsertAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCaseImpl
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class CategoriesViewModel(
    private val getAchievementsUseCase: GetAchievementsUseCaseImpl,
    private val insertAchievementsUseCase: InsertAchievementsUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var places: LiveData<List<FirebaseAchievement>> = MutableLiveData()

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<List<Achievement>>()
    private val taskInsertAchievements = createInsertAchievementsTask()
    private val taskGetAchievements = createGetAchievementsTask()

    fun getAchievements() = taskGetAchievements.start(GetAchievementsUseCase.Param)

    private fun handleInsertAchievementsResult(data: InsertAchievementsUseCase.Result) {
        when (data) {
            is InsertAchievementsUseCase.Result.Failure -> errorLiveData.value = data.error
            else -> Unit
        }
    }

    private fun handleGetAchievementsResult(data: GetAchievementsUseCase.Result) {
        when (data) {
            is GetAchievementsUseCase.Result.Failure -> errorLiveData.value = data.error
            is GetAchievementsUseCase.Result.Success -> {
                resultLiveData.value = data.achievements
                taskInsertAchievements.start(InsertAchievementsUseCase.Param(data.achievements))
            }
            else -> Unit
        }
    }

    private fun handleInsertAchievementError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun handleGetAchievementsError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun createInsertAchievementsTask() =
        taskExecutorFactory.createTaskExecutor<InsertAchievementsUseCase.Param, InsertAchievementsUseCase.Result>(
            singleResultUseCaseTaskProvider { insertAchievementsUseCase },
            { data -> handleInsertAchievementsResult(data) },
            { error -> handleInsertAchievementError(error) }
        )

    private fun createGetAchievementsTask() =
        taskExecutorFactory.createTaskExecutor<GetAchievementsUseCase.Param, GetAchievementsUseCase.Result>(
            singleResultUseCaseTaskProvider { getAchievementsUseCase },
            { data -> handleGetAchievementsResult(data) },
            { error -> handleGetAchievementsError(error) }
        )
}