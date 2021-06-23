package com.minsk.guru.domain

import com.minsk.guru.domain.adapter.InMemoryUserIdHolder
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.usecase.InsertLocalAchievementsUseCase
import com.minsk.guru.domain.usecase.InsertLocalAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.achievements.GetRemoteAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetRemoteAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.auth.*
import com.minsk.guru.domain.usecase.firebase.places.GetCategoriesUseCase
import com.minsk.guru.domain.usecase.firebase.places.GetCategoriesUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.places.GetRemotePlacesByCategoryUseCase
import com.minsk.guru.domain.usecase.firebase.places.GetRemotePlacesByCategoryUseCaseImpl
import com.minsk.guru.domain.usecase.local.places.*
import com.minsk.guru.domain.usecase.local.user.InsertLocalUserUseCase
import com.minsk.guru.domain.usecase.local.user.InsertLocalUserUseCaseImpl
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
    factory<InsertLocalUserUseCase> { InsertLocalUserUseCaseImpl(get()) }

    // achievements
    factory { GetRemoteAchievementsUseCaseImpl(get()) }
    factory<GetRemoteAchievementsUseCase> { GetRemoteAchievementsUseCaseImpl(get()) }
    factory<InsertLocalAchievementsUseCase> { InsertLocalAchievementsUseCaseImpl(get()) }

    // categories
    factory<GetCategoriesUseCase> { GetCategoriesUseCaseImpl(get()) }

    // places
    factory<InsertLocalPlaceUseCase> { InsertLocalPlaceUseCaseImpl(get(), get()) }
    factory<DeleteLocalPlaceUseCase> { DeleteLocalPlaceUseCaseImpl(get()) }
    factory<GetRemotePlacesByCategoryUseCase> { GetRemotePlacesByCategoryUseCaseImpl(get()) }
    factory<GetVisitedLocalPlacesByCategoryUseCase> { GetVisitedLocalPlacesByCategoryUseCaseImpl(get()) }
    factory<GetVisitedLocalPlacesUseCase> { GetVisitedLocalPlacesUseCaseImpl(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}