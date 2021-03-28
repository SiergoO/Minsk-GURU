package com.minsk.guru.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.minsk.guru.data.api.ApiHelper
import com.minsk.guru.data.api.PlacesApiImpl
import com.minsk.guru.data.api.YandexPlacesApi
import com.minsk.guru.data.repository.AppDatabase
import com.minsk.guru.data.repository.places.PlacesLocalRepositoryImpl
import com.minsk.guru.data.repository.places.PlacesRepositoryImpl
import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.domainModule
import com.minsk.guru.domain.repository.places.PlacesLocalRepository
import com.minsk.guru.domain.repository.places.PlacesRepository
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.websocket.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module(override = true) {

    factory {
        androidApplication().getSharedPreferences(
            "shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YandexPlacesApi::class.java)
    }

    single<PlacesApi> { PlacesApiImpl(get()) }
    single { ApiHelper(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().placesDao() }
    single<PlacesRepository> { PlacesRepositoryImpl(get(), get()) }
    single<PlacesLocalRepository> { PlacesLocalRepositoryImpl(get()) }

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