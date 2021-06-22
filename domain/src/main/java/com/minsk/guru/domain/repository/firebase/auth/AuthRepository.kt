package com.minsk.guru.domain.repository.firebase.auth

import com.minsk.guru.domain.model.User

interface AuthRepository {

    fun signIn(email: String, password: String)
    fun signUp(email: String, password: String, name: String, surname: String)
    fun getCurrentUser(): User
}