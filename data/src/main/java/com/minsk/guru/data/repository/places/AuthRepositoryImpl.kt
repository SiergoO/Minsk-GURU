package com.minsk.guru.data.repository.places

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.minsk.guru.domain.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    override suspend fun signIn(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        withContext(Dispatchers.IO){
             mAuth.signInWithEmailAndPassword(email, password)}.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.e("Auth", "success")
            } else {
                Log.e("Auth", "failed")
            }
        }
    }
}