package com.minsk.guru.domain.repository.firebase.profile

import com.minsk.guru.domain.model.ProfileInfo

interface ProfileRepository {
    fun getProfileInfo(userUid: String): ProfileInfo
}