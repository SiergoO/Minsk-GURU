package com.minsk.guru.domain.usecase.firebase.auth

import com.minsk.guru.domain.repository.firebase.auth.AuthRepository

class SignUpUseCase(private val authRepository: AuthRepository) {

    suspend fun signUp(email: String, password: String, name: String, surname: String) = authRepository.signUp(email, password, name, surname)
}