package com.minsk.guru.domain

import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.auth.*
import com.minsk.guru.domain.usecase.firebase.places.GetPlacesUseCase
import com.minsk.guru.domain.usecase.user.InsertUserUseCase
import com.minsk.guru.domain.usecase.user.InsertUserUseCaseImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

val domainModule = module {
    factory { GetPlacesUseCase(get()) }
    factory { GetAchievementsUseCase(get()) }
    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
    factory<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
    factory<SignUpUseCase> { SignUpUseCaseImpl(get()) }
    factory<InsertUserUseCase> { InsertUserUseCaseImpl(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}