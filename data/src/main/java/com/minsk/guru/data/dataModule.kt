package com.minsk.guru.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.minsk.guru.data.repository.AppDatabase
import com.minsk.guru.data.repository.firebase.AchievementsRepositoryImpl
import com.minsk.guru.data.repository.firebase.AuthRepositoryImpl
import com.minsk.guru.data.repository.firebase.PlacesRepositoryImpl
import com.minsk.guru.data.repository.room.achievements.AchievementsLocalRepositoryImpl
import com.minsk.guru.data.repository.room.user.UserLocalRepositoryImpl
import com.minsk.guru.domain.domainModule
import com.minsk.guru.domain.repository.firebase.achievements.AchievementsRepository
import com.minsk.guru.domain.repository.firebase.auth.AuthRepository
import com.minsk.guru.domain.repository.firebase.places.PlacesRepository
import com.minsk.guru.domain.repository.room.AchievementsLocalRepository
import com.minsk.guru.domain.repository.room.UserLocalRepository
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.websocket.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val dataModule = module(override = true) {

    factory {
        androidApplication().getSharedPreferences(
            "shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    single<PlacesRepository> { PlacesRepositoryImpl(FirebaseDatabase.getInstance()) }
    single<AchievementsRepository> { AchievementsRepositoryImpl(FirebaseDatabase.getInstance()) }
    single<AuthRepository> {
        AuthRepositoryImpl(
            FirebaseDatabase.getInstance(),
            FirebaseAuth.getInstance()
        )
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().achievementsDao() }
    single<UserLocalRepository> { UserLocalRepositoryImpl(get()) }
    single<AchievementsLocalRepository> { AchievementsLocalRepositoryImpl(get()) }

    single {
        HttpClient(OkHttp) {
            install(WebSockets)
        }
    }
}

val startDataKoin = { application: Application ->
    startKoin {
        androidContext(application)
        modules(domainModule, dataModule)
    }
}