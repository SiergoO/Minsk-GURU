package com.minsk.guru.screen.auth.signin

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.usecase.auth.SignInUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class SignInViewModel(
    private val userIdHolder: UserIdHolder,
    private val signInUseCase: SignInUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ViewModel() {

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _signInResult = MutableLiveData<SignInUseCase.Result>()
    val signInResult: LiveData<SignInUseCase.Result>
        get() = _signInResult

    private val taskSignIn = createSignInTask()

    fun checkLoggedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        userIdHolder.userId = user?.uid?:""
        return user != null
    }

    fun signIn(email: String, password: String) =
        taskSignIn.start(SignInUseCase.Param(email, password))

    fun logEvent(name: String, params: Bundle?) = firebaseAnalytics.logEvent(name, params)

    fun logError(message: String) = firebaseCrashlytics.log(message)

    private fun handleSignInResult(data: SignInUseCase.Result) {
        when (data) {
            is SignInUseCase.Result.Failure -> _error.value = data.error
            is SignInUseCase.Result.Success -> _signInResult.value = data
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun createSignInTask() =
        taskExecutorFactory.createTaskExecutor<SignInUseCase.Param, SignInUseCase.Result>(
            singleResultUseCaseTaskProvider { signInUseCase },
            { data -> handleSignInResult(data) },
            { error -> handleError(error) }
        )
}