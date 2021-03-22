package com.minsk.guru.domain

import com.minsk.guru.domain.usecase.places.GetPlacesUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.module

val domainModule = module {
    single { GetPlacesUseCase(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}