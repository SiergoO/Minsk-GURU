package com.minsk.guru.domain

import com.minsk.guru.domain.usecase.achievement.InsertAchievementsUseCase
import com.minsk.guru.domain.usecase.achievement.InsertAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.auth.*
import com.minsk.guru.domain.usecase.firebase.places.GetPlacesUseCase
import com.minsk.guru.domain.usecase.user.InsertUserUseCase
import com.minsk.guru.domain.usecase.user.InsertUserUseCaseImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

val domainModule = module {
    factory { GetPlacesUseCase(get()) }
    factory { GetAchievementsUseCaseImpl(get()) }
    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
    factory<SignUpUseCase> { SignUpUseCaseImpl(get()) }
    factory<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
    factory<InsertUserUseCase> { InsertUserUseCaseImpl(get()) }
    factory<GetAchievementsUseCase> { GetAchievementsUseCaseImpl(get()) }
    factory<InsertAchievementsUseCase> { InsertAchievementsUseCaseImpl(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}