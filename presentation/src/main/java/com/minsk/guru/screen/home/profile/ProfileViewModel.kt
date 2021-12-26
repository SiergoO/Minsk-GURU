package com.minsk.guru.screen.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.ProfileInfo
import com.minsk.guru.domain.usecase.profile.GetProfileInfoUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class ProfileViewModel(
    private val userIdHolder: UserIdHolder,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ViewModel() {

    private val taskGetProfileInfo = createGetProfileInfoTask()

    private val _profileInfo = MutableLiveData<ProfileInfo>()
    val profileInfo: LiveData<ProfileInfo>
        get() = _profileInfo

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    init {
        taskGetProfileInfo.start(GetProfileInfoUseCase.Param(userIdHolder.userId))
    }

    fun logOut() {
        try {
            firebaseAuth.apply {
                signOut()
                currentUser?.reload()
            }
        } catch (error: Throwable) {
            _error.value = error
        }
    }

    fun checkLoggedIn(): Boolean =
        firebaseAuth.currentUser != null

    fun logError(message: String) = firebaseCrashlytics.log(message)

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun handleGetProfileInfoResult(data: GetProfileInfoUseCase.Result) {
        when (data) {
            is GetProfileInfoUseCase.Result.Success -> _profileInfo.value = data.profileInfo
            is GetProfileInfoUseCase.Result.Failure -> _error.value = data.error
            else -> Unit
        }
    }

    private fun createGetProfileInfoTask() =
        taskExecutorFactory.createTaskExecutor<GetProfileInfoUseCase.Param, GetProfileInfoUseCase.Result>(
            singleResultUseCaseTaskProvider { getProfileInfoUseCase },
            { data -> handleGetProfileInfoResult(data) },
            { error -> handleError(error) }
        )
}