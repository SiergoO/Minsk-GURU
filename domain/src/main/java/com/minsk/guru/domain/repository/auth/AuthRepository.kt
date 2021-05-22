package com.minsk.guru.domain.repository.auth

interface AuthRepository {

    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String, name: String, surname: String)
}