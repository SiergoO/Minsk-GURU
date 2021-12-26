package com.minsk.guru.screen.auth.signup

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.minsk.guru.domain.usecase.auth.SignUpUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ViewModel() {

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _signUpResult = MutableLiveData<SignUpUseCase.Result>()
    val signUpResult: LiveData<SignUpUseCase.Result>
        get() = _signUpResult

    private val taskSignUp = createSignUpTask()

    fun signUp(email: String, password: String, name: String, surname: String) =
        taskSignUp.start(SignUpUseCase.Param(email, password, name, surname))

    fun logEvent(name: String, params: Bundle?) = firebaseAnalytics.logEvent(name, params)

    fun logError(message: String) = firebaseCrashlytics.log(message)

    private fun handleSignUpResult(data: SignUpUseCase.Result) {
        when (data) {
            is SignUpUseCase.Result.Failure -> _error.value = data.error
            is SignUpUseCase.Result.Success -> _signUpResult.value = data
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun createSignUpTask() =
        taskExecutorFactory.createTaskExecutor<SignUpUseCase.Param, SignUpUseCase.Result>(
            singleResultUseCaseTaskProvider { signUpUseCase },
            { data -> handleSignUpResult(data) },
            { error -> handleError(error) }
        )
}