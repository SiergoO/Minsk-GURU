package com.minsk.guru.screen.home.places.places

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.usecase.places.UpdatePlaceVisitStatusUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class PlacesViewModel(
    private val userIdHolder: UserIdHolder,
    private val updatePlaceVisitStatusUseCase: UpdatePlaceVisitStatusUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ViewModel() {

    private val taskUpdatePlaceVisitStatus = createUpdatePlaceVisitStatusTask()

    private val _visitedPlaces = MutableLiveData<List<Place>>()
    val visitedPlaces: LiveData<List<Place>>
        get() = _visitedPlaces

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun updateLocalPlace(place: Place, categoryName: String, isVisited: Boolean) {
        taskUpdatePlaceVisitStatus.start(
            UpdatePlaceVisitStatusUseCase.Param(userIdHolder.userId, place, categoryName, isVisited)
        )
    }

    fun logEvent(name: String, params: Bundle?) = firebaseAnalytics.logEvent(name, params)

    fun logError(message: String) = firebaseCrashlytics.log(message)

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun handlePlaceVisitStatusUpdated(data: UpdatePlaceVisitStatusUseCase.Result) {
        when (data) {
            is UpdatePlaceVisitStatusUseCase.Result.Success -> _visitedPlaces.value = data.visitedPlaces
            is UpdatePlaceVisitStatusUseCase.Result.Failure -> _error.value = data.error
            else -> Unit
        }
    }

    private fun createUpdatePlaceVisitStatusTask() =
        taskExecutorFactory.createTaskExecutor<UpdatePlaceVisitStatusUseCase.Param, UpdatePlaceVisitStatusUseCase.Result>(
            singleResultUseCaseTaskProvider { updatePlaceVisitStatusUseCase },
            { data -> handlePlaceVisitStatusUpdated(data) },
            { error -> handleError(error) }
        )
}