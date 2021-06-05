package com.minsk.guru.data.repository.places

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.repository.auth.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override fun signIn(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        val task = mAuth.signInWithEmailAndPassword(
            email,
            password
        )
        Tasks.await(task)
    }

    override fun signUp(email: String, password: String, name: String, surname: String) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(
            email,
            password
        )
        createUser(User(email, name, surname))
    }

    private fun createUser(user: User) {
        val database = FirebaseDatabase.getInstance().reference
        val currentUser = FirebaseAuth.getInstance()
        database.child("users").child(currentUser.currentUser.uid).setValue(user)
    }
}