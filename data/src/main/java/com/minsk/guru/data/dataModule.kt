package com.minsk.guru.data

import android.app.Application
import android.content.Context
import com.minsk.guru.data.repository.places.PlacesRepositoryImpl
import com.minsk.guru.domain.domainModule
import com.minsk.guru.domain.repository.places.PlacesRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.websocket.WebSockets
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

    single<PlacesRepository> { PlacesRepositoryImpl() }

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