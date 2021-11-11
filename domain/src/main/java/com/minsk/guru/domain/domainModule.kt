package com.minsk.guru.domain

import com.minsk.guru.domain.adapter.InMemoryUserIdHolder
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.usecase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.achievements.GetAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.auth.*
import com.minsk.guru.domain.usecase.places.*
import org.koin.core.context.startKoin
import org.koin.dsl.module

val domainModule = module {

    single<UserIdHolder> {
        InMemoryUserIdHolder()
    }

    // user
    factory<SignInUseCase> { SignInUseCaseImpl(get()) }
    factory<SignUpUseCase> { SignUpUseCaseImpl(get()) }
    factory<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    // achievements
    factory { GetAchievementsUseCaseImpl(get()) }
    factory<GetAchievementsUseCase> { GetAchievementsUseCaseImpl(get()) }

    // categories
    factory<GetUserCategoriesUseCase> { GetUserCategoriesUseCaseImpl(get()) }

    // places
    factory<GetPlacesByCategoryUseCase> { GetPlacesByCategoryUseCaseImpl(get()) }
    factory<GetVisitedPlacesUseCase> { GetVisitedPlacesUseCaseImpl(get()) }
    factory<GetVisitedPlacesByCategoryUseCase> { GetVisitedPlacesByCategoryUseCaseImpl(get()) }
    factory<UpdatePlaceVisitStatusUseCase> { UpdatePlaceVisitStatusUseCaseImpl(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}