package com.minsk.guru.data.repository.places

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.minsk.guru.domain.repository.auth.AuthRepository
import kotlinx.coroutines.*
import java.lang.Exception

class AuthRepositoryImpl : AuthRepository {

    @InternalCoroutinesApi
    override suspend fun signIn(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        val exception = CompletableDeferred<Throwable?>()
        val task = withContext(Dispatchers.IO) {
            mAuth.signInWithEmailAndPassword(
                email,
                password
            )
        }
        task.addOnSuccessListener {
            Log.e("Auth", "success")
            exception.complete(null)
        }.addOnFailureListener {
            Log.e("Auth", it.message.toString())
            exception.complete(task.exception)
        }
        throw exception.await() as Throwable
    }
}