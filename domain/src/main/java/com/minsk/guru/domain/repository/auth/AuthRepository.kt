package com.minsk.guru.domain.repository.auth

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Boolean
}