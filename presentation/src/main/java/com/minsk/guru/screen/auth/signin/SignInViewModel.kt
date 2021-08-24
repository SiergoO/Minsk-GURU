package com.minsk.guru.screen.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.usecase.auth.SignInUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<SignInUseCase.Result>()
    private val taskSignIn = createSignInTask()

    fun signIn(email: String, password: String) =
        taskSignIn.start(SignInUseCase.Param(email, password))

    private fun handleSignInResult(data: SignInUseCase.Result) {
        when (data) {
            is SignInUseCase.Result.Failure -> errorLiveData.value = data.error
            is SignInUseCase.Result.Success -> resultLiveData.value = data
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun createSignInTask() =
        taskExecutorFactory.createTaskExecutor<SignInUseCase.Param, SignInUseCase.Result>(
            singleResultUseCaseTaskProvider { signInUseCase },
            { data -> handleSignInResult(data) },
            { error -> handleError(error) }
        )
}