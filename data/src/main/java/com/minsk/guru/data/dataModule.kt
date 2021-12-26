package com.minsk.guru.data

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.minsk.guru.data.repository.AchievementsRepositoryImpl
import com.minsk.guru.data.repository.AuthRepositoryImpl
import com.minsk.guru.data.repository.PlacesRepositoryImpl
import com.minsk.guru.data.repository.ProfileRepositoryImpl
import com.minsk.guru.domain.domainModule
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.repository.firebase.profile.ProfileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val dataModule = module(override = true) {

    single { FirebaseApp.getInstance() }

    single { FirebaseAuth.getInstance(get()) }

    factory { get<FirebaseAuth>().currentUser }

    single { FirebaseAnalytics.getInstance(androidContext().applicationContext) }

    single { FirebaseCrashlytics.getInstance() }

    single { FirebaseDatabase.getInstance(get(), BuildConfig.FIREBASE_DATABASE_BASE_URL) }

    single { FirebaseStorage.getInstance() }

    single<PlacesRepository> { PlacesRepositoryImpl(get()) }
    single<AchievementsRepository> { AchievementsRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
}

val startDataKoin = { application: Application ->
    startKoin {
        androidContext(application)
        FirebaseApp.initializeApp(application.applicationContext)
        modules(domainModule, dataModule)
    }
}