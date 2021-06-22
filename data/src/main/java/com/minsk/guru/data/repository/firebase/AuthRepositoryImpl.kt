package com.minsk.guru.data.repository.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.User
import com.minsk.guru.domain.model.firebase.FirebaseUser
import com.minsk.guru.domain.repository.firebase.auth.AuthRepository

class AuthRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val userIdHolder: UserIdHolder
) : AuthRepository {

    override fun signIn(email: String, password: String) {
        val taskSignIn = firebaseAuth.signInWithEmailAndPassword(
            email,
            password
        )
        Tasks.await(taskSignIn)
            .also { userIdHolder.userId = it?.user?.uid ?: "UNKNOWN_USER" }
    }

    override fun signUp(email: String, password: String, name: String, surname: String) {
        val taskSignUp = firebaseAuth.createUserWithEmailAndPassword(
            email,
            password
        )
        Tasks.await(taskSignUp).user?.uid.let { userId ->
            if (userId != null) {
                createUser(userId, FirebaseUser(email, name, surname))
            }
            userIdHolder.userId = userId ?: "UNKNOWN_USER"
        }
    }

    override fun getCurrentUser(): User {
        val userUId: String = firebaseAuth.currentUser!!.uid
        val taskGetUser =
            firebaseDatabase.reference.child("users").child(userUId).get()
        Tasks.await(taskGetUser).getValue(FirebaseUser::class.java).let { firebaseUser ->
            return firebaseUser?.toDomainModel()!!.copy(id = userUId)
        }
    }

    private fun createUser(id: String, user: FirebaseUser) {
        firebaseDatabase.reference.child("users").child(id).setValue(user)
    }
}