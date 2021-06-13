package com.minsk.guru.screen.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.usecase.firebase.auth.GetCurrentUserUseCase
import com.minsk.guru.domain.usecase.firebase.auth.SignUpUseCase
import com.minsk.guru.domain.usecase.user.InsertUserUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<SignUpUseCase.Result>()
    private val taskSignUp = createSignUpTask()
    private val taskGetCurrentUser = createGetCurrentUserTask()
    private val taskUpdateUser = createUpdateUserTask()

    fun signUp(email: String, password: String, name: String, surname: String) =
        taskSignUp.start(SignUpUseCase.Param(email, password, name, surname))

    private fun handleSignUpResult(data: SignUpUseCase.Result) {
        when (data) {
            is SignUpUseCase.Result.Failure -> errorLiveData.value = data.error
            is SignUpUseCase.Result.Success -> {
                resultLiveData.value = data
                taskGetCurrentUser.start(GetCurrentUserUseCase.Param)
            }
            else -> Unit
        }
    }

    private fun handleGetCurrentUserResult(data: GetCurrentUserUseCase.Result) {
        when (data) {
            is GetCurrentUserUseCase.Result.Failure -> errorLiveData.value = data.error
            is GetCurrentUserUseCase.Result.Success -> {
                if (data.user != null) {
                    taskUpdateUser.start(InsertUserUseCase.Param(data.user!!))
                }
            }
            else -> Unit
        }
    }

    private fun handleUpdateUserResult(data: InsertUserUseCase.Result) {
        when (data) {
            is InsertUserUseCase.Result.Failure -> errorLiveData.value = data.error
            is InsertUserUseCase.Result.Success -> {
            }
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

    private fun createGetCurrentUserTask() =
        taskExecutorFactory.createTaskExecutor<GetCurrentUserUseCase.Param, GetCurrentUserUseCase.Result>(
            singleResultUseCaseTaskProvider { getCurrentUserUseCase },
            { data -> handleGetCurrentUserResult(data) },
            { error -> handleError(error) }
        )

    private fun createUpdateUserTask() =
        taskExecutorFactory.createTaskExecutor<InsertUserUseCase.Param, InsertUserUseCase.Result>(
            singleResultUseCaseTaskProvider { insertUserUseCase },
            { data -> handleUpdateUserResult(data) },
            { error -> handleError(error) }
        )
}