package com.minsk.guru.ui

import android.app.Application
import com.minsk.guru.data.dataModule
import com.minsk.guru.domain.domainModule
import com.minsk.guru.ui.achievements.AchievementsViewModel
import com.minsk.guru.ui.places.PlacesViewModel
import com.minsk.guru.ui.profile.ProfileViewModel
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