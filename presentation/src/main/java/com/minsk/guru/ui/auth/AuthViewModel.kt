package com.minsk.guru.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minsk.guru.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    fun signIn(email: String, password: String) = viewModelScope.launch {
        signInUseCase.signIn(email, password)
    }
}