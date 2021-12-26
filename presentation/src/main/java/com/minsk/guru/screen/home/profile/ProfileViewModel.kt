package com.minsk.guru.screen.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics

class ProfileViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ViewModel() {

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun logOut() {
        try {
            firebaseAuth.apply {
                signOut()
                currentUser?.reload()
            }
        } catch (error: Throwable) {
            _error.value = error
        }
    }

    fun checkLoggedIn(): Boolean =
        firebaseAuth.currentUser != null

    fun logError(message: String) = firebaseCrashlytics.log(message)
}