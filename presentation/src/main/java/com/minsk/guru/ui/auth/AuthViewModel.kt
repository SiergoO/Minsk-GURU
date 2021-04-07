package com.minsk.guru.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.usecase.auth.SignInUseCase

class AuthViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    var isSignInSuccessful: LiveData<Boolean> = MutableLiveData()

    fun signIn(email: String, password: String) {
        isSignInSuccessful = liveData { emit(signInUseCase.signIn(email, password)) }
    }

}