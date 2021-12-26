package com.minsk.guru.data.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.minsk.guru.domain.model.ProfileInfo
import com.minsk.guru.domain.model.remote.RemoteUser
import com.minsk.guru.domain.repository.firebase.profile.ProfileRepository

class ProfileRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
) : ProfileRepository {

    override fun getProfileInfo(userUid: String): ProfileInfo {
        val userReference = firebaseDatabase.reference.child("users").child(userUid)
        val taskGetUser = userReference.get()
        Tasks.await(taskGetUser)
            .getValue(RemoteUser::class.java)?.let { user ->
                return ProfileInfo(
                    user.email,
                    user.name,
                    user.surname,
                    user.profilePhotoUrl.let { if (it.isNotEmpty()) it else {
                        val photoUri = generateRandomProfilePhotoUri()
                        userReference.child("profilePhotoUrl").setValue(photoUri)
                        photoUri
                    } }
                )
            }!!
    }

    private fun generateRandomProfilePhotoUri(): String {
        val imagePath = "${(1..8).random()}.png"
        val taskDownloadUrl = firebaseStorage.reference.child(imagePath).downloadUrl
        return Tasks.await(taskDownloadUrl).toString()
    }
}