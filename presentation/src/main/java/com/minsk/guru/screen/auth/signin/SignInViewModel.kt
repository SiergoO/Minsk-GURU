package com.minsk.guru.screen.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.minsk.guru.domain.usecase.firebase.auth.GetCurrentUserUseCase
import com.minsk.guru.domain.usecase.firebase.auth.SignInUseCase
import com.minsk.guru.domain.usecase.local.user.InsertLocalUserUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val insertLocalUserUseCase: InsertLocalUserUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
) : ViewModel() {

    var errorLiveData = MutableLiveData<Throwable>()
    var resultLiveData = MutableLiveData<SignInUseCase.Result>()
    private val taskSignIn = createSignInTask()
    private val taskGetCurrentUser = createGetCurrentUserTask()
    private val taskUpdateUser = createUpdateUserTask()

    fun signIn(email: String, password: String) =
        taskSignIn.start(SignInUseCase.Param(email, password))

    private fun handleSignInResult(data: SignInUseCase.Result) {
        when (data) {
            is SignInUseCase.Result.Failure -> errorLiveData.value = data.error
            is SignInUseCase.Result.Success -> {
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
                    taskUpdateUser.start(InsertLocalUserUseCase.Param(data.user!!))
                }
            }
            else -> Unit
        }
    }

    private fun handleUpdateUserResult(data: InsertLocalUserUseCase.Result) {
        when (data) {
            is InsertLocalUserUseCase.Result.Failure -> errorLiveData.value = data.error
            is InsertLocalUserUseCase.Result.Success -> {
            }
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

    private fun createGetCurrentUserTask() =
        taskExecutorFactory.createTaskExecutor<GetCurrentUserUseCase.Param, GetCurrentUserUseCase.Result>(
            singleResultUseCaseTaskProvider { getCurrentUserUseCase },
            { data -> handleGetCurrentUserResult(data) },
            { error -> handleError(error) }
        )

    private fun createUpdateUserTask() =
        taskExecutorFactory.createTaskExecutor<InsertLocalUserUseCase.Param, InsertLocalUserUseCase.Result>(
            singleResultUseCaseTaskProvider { insertLocalUserUseCase },
            { data -> handleUpdateUserResult(data) },
            { error -> handleError(error) }
        )
}