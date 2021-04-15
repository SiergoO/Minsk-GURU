package com.minsk.guru.ui.auth.signin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.minsk.guru.domain.usecase.auth.SignInUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    var exceptionLiveData = MutableLiveData<Throwable?>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        exceptionLiveData.value = throwable
        Log.e("Auth", throwable.message.toString())
    }

    fun signIn(email: String, password: String) = viewModelScope.launch(exceptionHandler) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        signInUseCase.signIn(email, password)
    }
}