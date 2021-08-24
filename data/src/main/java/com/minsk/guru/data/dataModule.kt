package com.minsk.guru.data

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.data.repository.AppDatabase
import com.minsk.guru.data.repository.local.places.PlacesLocalRepositoryImpl
import com.minsk.guru.data.repository.local.user.UserLocalRepositoryImpl
import com.minsk.guru.data.repository.local.userplaces.UserPlacesLocalRepositoryImpl
import com.minsk.guru.data.repository.remote.AchievementsRepositoryImpl
import com.minsk.guru.data.repository.remote.AuthRepositoryImpl
import com.minsk.guru.data.repository.remote.PlacesRepositoryImpl
import com.minsk.guru.domain.domainModule
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.repository.local.PlacesLocalRepository
import com.minsk.guru.domain.repository.local.UserLocalRepository
import com.minsk.guru.domain.repository.local.UserPlacesLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val dataModule = module(override = true) {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }
    single<PlacesRepository> { PlacesRepositoryImpl(FirebaseDatabase.getInstance()) }
    single<AchievementsRepository> { AchievementsRepositoryImpl(FirebaseDatabase.getInstance()) }
    single<AuthRepository> {
        AuthRepositoryImpl(
            FirebaseDatabase.getInstance(),
            FirebaseAuth.getInstance(),
            get()
        )
    }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().placesDao() }
    single { get<AppDatabase>().userPlacesDao() }
    single<UserLocalRepository> { UserLocalRepositoryImpl(get()) }
    single<PlacesLocalRepository> { PlacesLocalRepositoryImpl(get()) }
    single<UserPlacesLocalRepository> { UserPlacesLocalRepositoryImpl(get()) }
}

val startDataKoin = { application: Application ->
    startKoin {
        androidContext(application)
        modules(domainModule, dataModule)
    }
}