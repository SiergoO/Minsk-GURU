package com.minsk.guru.domain

import com.minsk.guru.domain.usecase.InsertLocalAchievementsUseCase
import com.minsk.guru.domain.usecase.InsertLocalAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCase
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.auth.*
import com.minsk.guru.domain.usecase.firebase.places.GetCategoriesUseCase
import com.minsk.guru.domain.usecase.firebase.places.GetCategoriesUseCaseImpl
import com.minsk.guru.domain.usecase.firebase.places.GetPlacesUseCase
import com.minsk.guru.domain.usecase.local.places.DeleteLocalPlaceUseCase
import com.minsk.guru.domain.usecase.local.places.DeleteLocalPlaceUseCaseImpl
import com.minsk.guru.domain.usecase.local.places.InsertLocalPlaceUseCase
import com.minsk.guru.domain.usecase.local.places.InsertLocalPlaceUseCaseImpl
import com.minsk.guru.domain.usecase.local.user.InsertUserUseCase
import com.minsk.guru.domain.usecase.local.user.InsertUserUseCaseImpl
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
    factory<InsertLocalAchievementsUseCase> { InsertLocalAchievementsUseCaseImpl(get()) }
    factory<GetCategoriesUseCase> { GetCategoriesUseCaseImpl(get()) }
    factory<InsertLocalPlaceUseCase> { InsertLocalPlaceUseCaseImpl(get()) }
    factory<DeleteLocalPlaceUseCase> { DeleteLocalPlaceUseCaseImpl(get()) }
}

val startDomainKoin = {
    startKoin {
        modules(domainModule)
    }
}