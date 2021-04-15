package com.minsk.guru.ui

import android.app.Application
import com.minsk.guru.data.dataModule
import com.minsk.guru.domain.domainModule
import com.minsk.guru.ui.home.achievements.AchievementsViewModel
import com.minsk.guru.ui.auth.signin.SignInViewModel
import com.minsk.guru.ui.home.places.PlacesViewModel
import com.minsk.guru.ui.home.profile.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val presentationModule = module {

    viewModel { SignInViewModel(get()) }
    viewModel { AchievementsViewModel(get()) }
    viewModel { PlacesViewModel(get()) }
    viewModel { ProfileViewModel() }
}

@ExperimentalCoroutinesApi
@FlowPreview
val startPresentationKoin = { application: Application ->
    startKoin {
        androidContext(application)
        modules(dataModule, domainModule, presentationModule)
        logger(AndroidLogger())
    }
}