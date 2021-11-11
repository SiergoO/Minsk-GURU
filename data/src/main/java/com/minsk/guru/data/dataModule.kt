package com.minsk.guru.data

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.data.repository.AchievementsRepositoryImpl
import com.minsk.guru.data.repository.AuthRepositoryImpl
import com.minsk.guru.data.repository.PlacesRepositoryImpl
import com.minsk.guru.domain.domainModule
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val dataModule = module(override = true) {

    single<PlacesRepository> {
        PlacesRepositoryImpl(FirebaseDatabase.getInstance(), Places.createClient(androidContext()))
    }
    single<AchievementsRepository> { AchievementsRepositoryImpl(FirebaseDatabase.getInstance()) }
    single<AuthRepository> {
        AuthRepositoryImpl(
            FirebaseDatabase.getInstance(),
            FirebaseAuth.getInstance(),
            get()
        )
    }
}

val startDataKoin = { application: Application ->
    startKoin {
        androidContext(application)
        modules(domainModule, dataModule)
    }
}