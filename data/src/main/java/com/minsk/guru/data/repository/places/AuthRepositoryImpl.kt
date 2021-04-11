package com.minsk.guru.data.repository.places

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.minsk.guru.domain.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    override suspend fun signIn(email: String, password: String) {

        val mAuth = FirebaseAuth.getInstance()
        val task = withContext(Dispatchers.IO) {
            val s = async {
                mAuth.signInWithEmailAndPassword(
                    email,
                    password
                )
            }
            s.await()
        }
        if (task.isSuccessful) {
            Log.e("Auth", "success")
        } else {
            Log.e("Auth", task.exception?.message.toString())
            throw task.exception ?: Exception()
        }
    }
}