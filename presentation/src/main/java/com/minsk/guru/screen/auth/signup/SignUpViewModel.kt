package com.minsk.guru.screen.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.usecase.auth.SignUpUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<SignUpUseCase.Result>()
    private val taskSignUp = createSignUpTask()

    fun signUp(email: String, password: String, name: String, surname: String) =
        taskSignUp.start(SignUpUseCase.Param(email, password, name, surname))

    private fun handleSignUpResult(data: SignUpUseCase.Result) {
        when (data) {
            is SignUpUseCase.Result.Failure -> errorLiveData.value = data.error
            is SignUpUseCase.Result.Success -> resultLiveData.value = data
            else -> Unit
        }
    }

    private fun handleError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun createSignUpTask() =
        taskExecutorFactory.createTaskExecutor<SignUpUseCase.Param, SignUpUseCase.Result>(
            singleResultUseCaseTaskProvider { signUpUseCase },
            { data -> handleSignUpResult(data) },
            { error -> handleError(error) }
        )
}