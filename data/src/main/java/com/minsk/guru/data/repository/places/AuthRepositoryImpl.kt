package com.minsk.guru.data.repository.places

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.repository.auth.AuthRepository
import kotlinx.coroutines.*

class AuthRepositoryImpl : AuthRepository {

    @InternalCoroutinesApi
    override suspend fun signIn(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        val exception = CompletableDeferred<Throwable?>()
        val signInTask = withContext(Dispatchers.IO) {
            mAuth.signInWithEmailAndPassword(
                email,
                password
            )
        }
        signInTask.addOnSuccessListener {
            Log.e("Auth", "success")
            exception.complete(null)
        }.addOnFailureListener {
            Log.e("Auth", it.message.toString())
            exception.complete(signInTask.exception)
        }
        throw exception.await() as Throwable
    }

    override suspend fun signUp(email: String, password: String, name: String, surname: String) {
        val mAuth = FirebaseAuth.getInstance()
        val exception = CompletableDeferred<Throwable?>()
        val signUpTask = withContext(Dispatchers.IO) {
            mAuth.createUserWithEmailAndPassword(
                email,
                password
            )
        }
        signUpTask.addOnSuccessListener {
            Log.e("Auth", "success")
            createUser(User(email, name, surname))
            exception.complete(null)
        }.addOnFailureListener {
            Log.e("Auth", it.message.toString())
            exception.complete(signUpTask.exception)
        }
        throw exception.await() as Throwable
    }

    private fun createUser(user: User) {
        val database = FirebaseDatabase.getInstance().reference
        val currentUser = FirebaseAuth.getInstance()
        database.child("users").child(currentUser.currentUser.uid).setValue(user)
    }
}