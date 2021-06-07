package com.minsk.guru.data.repository.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.domain.model.FirebaseUser
import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun signIn(email: String, password: String) {
        val taskSignIn = firebaseAuth.signInWithEmailAndPassword(
            email,
            password
        )
        Tasks.await(taskSignIn)
    }

    override fun signUp(email: String, password: String, name: String, surname: String) {
        val taskSignUp = firebaseAuth.createUserWithEmailAndPassword(
            email,
            password
        )
        Tasks.await(taskSignUp).user?.uid?.let {
            createUser(it, FirebaseUser(email, name, surname))
        }
    }

    override fun getCurrentUser(): User {
        var user: User? = null
        val taskGetUser =
            firebaseDatabase.reference.child("users").child(firebaseAuth.currentUser.uid).get()
        taskGetUser.addOnSuccessListener {
            it.getValue(FirebaseUser::class.java).let { firebaseUser ->
                user = User(
                    it.key ?: "",
                    firebaseUser?.email ?: "",
                    firebaseUser?.name ?: "",
                    firebaseUser?.surname ?: "",
                    null
                )
            }
        }
        Tasks.await(taskGetUser, 15L, TimeUnit.SECONDS)
        return user!!
    }

    private fun createUser(id: String, user: FirebaseUser) {
        firebaseDatabase.reference.child("users").child(id).setValue(user)
    }
}