package com.minsk.guru.domain.repository.auth

interface AuthRepository {

    fun signIn(email: String, password: String)
    fun signUp(email: String, password: String, name: String, surname: String)
}