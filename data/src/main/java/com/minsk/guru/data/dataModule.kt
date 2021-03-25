package com.minsk.guru.data

import android.app.Application
import android.content.Context
import com.minsk.guru.data.repository.api.ApiHelper
import com.minsk.guru.data.repository.api.PlacesApiImpl
import com.minsk.guru.data.repository.api.YandexPlacesApi
import com.minsk.guru.domain.api.PlacesApi
import com.minsk.guru.domain.domainModule
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.websocket.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://search-maps.yandex.ru"

val dataModule = module(override = true) {

    factory {
        androidApplication().getSharedPreferences(
            "shared_preferences",
            Context.MODE_PRIVATE
        )
    }

    single<PlacesApi> { PlacesApiImpl(get()) }
    single { ApiHelper(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // BuildConfig.BASE_URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YandexPlacesApi::class.java)
    }

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