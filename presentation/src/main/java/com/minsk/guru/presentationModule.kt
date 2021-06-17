package com.minsk.guru

import android.app.Application
import com.minsk.guru.data.dataModule
import com.minsk.guru.domain.domainModule
import com.minsk.guru.screen.auth.signin.SignInFragment
import com.minsk.guru.screen.auth.signin.SignInViewModel
import com.minsk.guru.screen.auth.signup.SignUpFragment
import com.minsk.guru.screen.auth.signup.SignUpViewModel
import com.minsk.guru.screen.home.places.categories.CategoriesFragment
import com.minsk.guru.screen.home.places.categories.CategoriesViewModel
import com.minsk.guru.screen.home.places.places.PlacesFragment
import com.minsk.guru.screen.home.places.places.PlacesViewModel
import com.minsk.guru.screen.home.profile.ProfileFragment
import com.minsk.guru.screen.home.profile.ProfileViewModel
import com.minsk.guru.utils.TaskExecutorFactory
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val presentationModule = module {

    single {
        TaskExecutorFactory.create(CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate))
    }

    fragment { SignInFragment() }
    viewModel { SignInViewModel(get(), get(), get(), get()) }

    fragment { SignUpFragment() }
    viewModel { SignUpViewModel(get(), get(), get(), get()) }

    fragment { CategoriesFragment() }
    viewModel { CategoriesViewModel(get(), get(), get(), get(), get()) }

    fragment { PlacesFragment() }
    viewModel { PlacesViewModel(get(), get(), get(),get(), get()) }

    fragment { ProfileFragment() }
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