package com.minsk.guru.domain.usecase.auth

import com.minsk.guru.domain.repository.auth.AuthRepository

class SignUpUseCase(private val authRepository: AuthRepository) {

    suspend fun signUp(email: String, password: String, name: String, surname: String) = authRepository.signUp(email, password, name, surname)
}