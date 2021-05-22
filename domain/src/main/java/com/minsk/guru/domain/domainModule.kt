package com.minsk.guru.domain

import com.minsk.guru.domain.usecase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.auth.SignInUseCase
import com.minsk.guru.domain.usecase.auth.SignUpUseCase
import com.minsk.guru.domain.usecase.places.GetPlacesUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.module

val domainModule = module {
    single { GetPlacesUseCase(get()) }
    single { GetAchievementsUseCase(get()) }
    single { SignInUseCase(get()) }
    single { SignUpUseCase(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}