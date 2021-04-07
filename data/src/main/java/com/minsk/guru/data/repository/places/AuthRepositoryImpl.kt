package com.minsk.guru.data.repository.places

import com.google.firebase.auth.FirebaseAuth
import com.minsk.guru.domain.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl() : AuthRepository {
    override suspend fun signIn(email: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            val mAuth = FirebaseAuth.getInstance()
            return@withContext mAuth.signInWithEmailAndPassword(email, password).isComplete
        }
}