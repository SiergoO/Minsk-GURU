package com.minsk.guru.domain.usecase.auth

import com.minsk.guru.domain.repository.auth.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {

    suspend fun signIn(email: String, password: String) = authRepository.signIn(email, password)
}