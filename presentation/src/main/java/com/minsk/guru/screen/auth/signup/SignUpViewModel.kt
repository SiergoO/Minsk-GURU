package com.minsk.guru.screen.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minsk.guru.domain.usecase.auth.SignUpUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignUpViewModel (private val signUpUseCase: SignUpUseCase) : ViewModel() {

    var exceptionLiveData = MutableLiveData<Throwable?>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        exceptionLiveData.value = throwable
        Log.e("Auth", throwable.message.toString())
    }

    fun signUp(email: String, password: String, name: String, surname: String) = viewModelScope.launch(exceptionHandler) {
        signUpUseCase.signUp(email, password, name, surname)
    }
}