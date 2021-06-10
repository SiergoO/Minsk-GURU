package com.minsk.guru.screen.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minsk.guru.domain.usecase.firebase.auth.GetCurrentUserUseCase
import com.minsk.guru.domain.usecase.firebase.auth.SignInUseCase
import com.minsk.guru.domain.usecase.firebase.auth.SignUpUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<SignUpUseCase.Result>()
    private val taskSignUp = createSignUpTask()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.value = throwable
        Log.e("Auth", throwable.message.toString())
    }

    fun signUp(email: String, password: String, name: String, surname: String) =
        viewModelScope.launch(exceptionHandler) {
            taskSignUp.start(SignUpUseCase.Param(email, password, name, surname))
        }

    private fun handleSignUpResult(data: SignUpUseCase.Result) {
        when (data) {
            is SignUpUseCase.Result.Failure -> errorLiveData.value = data.error
            is SignUpUseCase.Result.Success -> resultLiveData.value = data
            else -> Unit
        }
    }

    private fun handleSignUpError(error: Throwable) {
        errorLiveData.value = error
    }

    private fun createSignUpTask() =
        taskExecutorFactory.createTaskExecutor<SignUpUseCase.Param, SignUpUseCase.Result>(
            singleResultUseCaseTaskProvider { signUpUseCase },
            { data -> handleSignUpResult(data) },
            { error -> handleSignUpError(error) }
        )
}